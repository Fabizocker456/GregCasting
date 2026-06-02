package eu.seahousen.gregcasting.casting;

import appeng.core.definitions.AEBlocks;
import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.common.lib.HexBlocks;
import eu.seahousen.gregcasting.GregCasting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class OPDisplaceMedia implements SpellAction {
    public static final OPDisplaceMedia INSTANCE = new OPDisplaceMedia();

    final static int NUMBER_OF_TICKS = 200;

    static final BiConsumer<ServerLevel, BlockPos> OP_RANDOM_TICK = (world, pos) -> {
        BlockState state = world.getBlockState(pos);
        for(int i = 0; i < NUMBER_OF_TICKS; i++) {
            state.randomTick(world, pos, world.getRandom());
        }
    };
    static final BiConsumer<ServerLevel, BlockPos> OP_NOOP = (_1, _2) -> {};


    public static final List<DecayChain> DECAY_CHAINS = List.of(
            new DecayChain(OP_RANDOM_TICK,
                    Blocks.BUDDING_AMETHYST,
                    Blocks.AMETHYST_BLOCK
            ),
            new DecayChain(OP_RANDOM_TICK,
                    AEBlocks.FLAWLESS_BUDDING_QUARTZ.block(),
                    AEBlocks.FLAWED_BUDDING_QUARTZ.block(),
                    AEBlocks.CHIPPED_BUDDING_QUARTZ.block(),
                    AEBlocks.DAMAGED_BUDDING_QUARTZ.block(),
                    AEBlocks.QUARTZ_BLOCK.block()
            ),
            new DecayChain(OP_NOOP,
                    Blocks.AMETHYST_BLOCK,
                    Blocks.QUARTZ_BLOCK
            ),
            new DecayChain(OP_NOOP,
                    AEBlocks.QUARTZ_BLOCK.block(),
                    Blocks.QUARTZ_BLOCK
            )
    );

    static @Nullable DecayChain canDisplace(BlockState block) {
        for(DecayChain i : DECAY_CHAINS) {
            if(i.canDecay(block.getBlock())) return i;
        }
        return null;
    }

    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    public @NotNull Result execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) throws Mishap {
        Vec3 vec = OperatorUtils.getVec3(list, 0, 1);
        BlockPos pos = BlockPos.containing(vec);
        castingEnvironment.assertPosInRangeForEditing(pos);

        return new SpellAction.Result(
                new Spell(pos),
                MediaConstants.DUST_UNIT * 10,
                List.of(ParticleSpray.burst(Vec3.atCenterOf(pos), 1.0, 20)),
                1
        );
    }

    public static class DecayChain {
        BiConsumer<ServerLevel, BlockPos> op;
        List<Block> blocks;

        public DecayChain(BiConsumer<ServerLevel, BlockPos> op, Block... blocks) {
            this.op = op;
            this.blocks = List.of(blocks);
        }

        public boolean canDecay(Block block) {
            if(!blocks.contains(block)) return false;
            if(blocks.get(blocks.size() - 1) == block) return false;
            return true;
        }

        public Block doDecay(Block prev) {
            return blocks.get(blocks.indexOf(prev) + 1);
        }
    }

    static class Spell implements RenderedSpell {
        @NotNull BlockPos where;

        Spell(@NotNull BlockPos where) {
            this.where = where;
        }

        @Override
        public void cast(@NotNull CastingEnvironment castingEnvironment) {
            ServerLevel world = castingEnvironment.getWorld();
            BlockState state = world.getBlockState(this.where);
            @Nullable DecayChain chain = canDisplace(state);

            if(chain == null) {

            } else {
                chain.op.accept(world, this.where);
                world.setBlockAndUpdate(
                        this.where,
                        chain.doDecay(state.getBlock()).defaultBlockState()
                );
            }
        }

        @Override
        public @Nullable CastingImage cast(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage) {
            return RenderedSpell.DefaultImpls.cast(this, castingEnvironment, castingImage);
        }
    }

    // defaults

    @Override
    public boolean hasCastingSound(@NotNull CastingEnvironment castingEnvironment) {
        return SpellAction.DefaultImpls.hasCastingSound(this, castingEnvironment);
    }

    @Override
    public boolean awardsCastingStat(@NotNull CastingEnvironment castingEnvironment) {
        return SpellAction.DefaultImpls.awardsCastingStat(this, castingEnvironment);
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) throws Mishap {
        return SpellAction.DefaultImpls.operate(this, castingEnvironment, castingImage, spellContinuation);
    }

    @Override
    public @NotNull Result executeWithUserdata(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment, @NotNull CompoundTag compoundTag) throws Mishap {
        return SpellAction.DefaultImpls.executeWithUserdata(this, list, castingEnvironment, compoundTag);
    }
}
