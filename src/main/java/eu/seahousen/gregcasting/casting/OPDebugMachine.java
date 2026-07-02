package eu.seahousen.gregcasting.casting;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import eu.seahousen.gregcasting.machine.MediaDisplacedWEMM;
import eu.seahousen.gregcasting.mixinutil.IGetDebugData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OPDebugMachine implements SpellAction {
    public static final OPDebugMachine INSTANCE = new OPDebugMachine();

    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    public boolean hasCastingSound(@NotNull CastingEnvironment castingEnvironment) {
        return SpellAction.DefaultImpls.hasCastingSound(this, castingEnvironment);
    }

    @Override
    public boolean awardsCastingStat(@NotNull CastingEnvironment castingEnvironment) {
        return SpellAction.DefaultImpls.awardsCastingStat(this, castingEnvironment);
    }

    @Override
    public @NotNull Result execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) throws Mishap {
        Vec3 vec = OperatorUtils.getVec3(list, 0, 1);
        BlockPos pos = BlockPos.containing(vec);
        BlockEntity be = castingEnvironment.getWorld().getBlockEntity(pos);
        if(!(castingEnvironment instanceof PlayerBasedCastEnv pbce)) { throw new DebugMishap("No Player"); }
        if(!pbce.getCaster().hasPermissions(2)) { throw new DebugMishap("Not Admin"); }
        if (be == null) { throw new DebugMishap("No Block Entity"); }
        if(!(be instanceof MediaDisplacedWEMM)) { throw new DebugMishap("Not a MD WEMM"); }

        return new SpellAction.Result(
                new OPDebugMachine.Spell(pos),
                MediaConstants.DUST_UNIT * 10,
                List.of(ParticleSpray.burst(Vec3.atCenterOf(pos), 1.0, 20)),
                1
        );
    }

    @Override
    public @NotNull Result executeWithUserdata(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment, @NotNull CompoundTag compoundTag) throws Mishap {
        return SpellAction.DefaultImpls.executeWithUserdata(this, list, castingEnvironment, compoundTag);
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return SpellAction.DefaultImpls.operate(this, castingEnvironment, castingImage, spellContinuation);
    }

    public static class DebugMishap extends Mishap {
        public String message;

        public DebugMishap(String message) {
            this.message = message;
        }

        @Override
        public @NotNull FrozenPigment accentColor(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
            return FrozenPigment.DEFAULT.get();
        }

        @Override
        public void execute(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context, @NotNull List<Iota> list) { }

        @Override
        protected @Nullable Component errorMessage(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
            return Component.literal(this.message);
        }
    }
    public static class Spell implements RenderedSpell {
        public BlockPos pos;

        public Spell(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public void cast(@NotNull CastingEnvironment castingEnvironment) {
            BlockEntity be = castingEnvironment.getWorld().getBlockEntity(pos);
            if(!(castingEnvironment instanceof PlayerBasedCastEnv pbce)) { return; }
            if(!pbce.getCaster().hasPermissions(2)) { return; }
            if (be == null) { return; }
            if(!(be instanceof MediaDisplacedWEMM mdw)) { return; }
            pbce.getCaster().sendSystemMessage(
                    Component.literal(
                            mdw.gregcasting$getDebugData()
                    )
            );
        }

        @Override
        public @Nullable CastingImage cast(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage) {
            return RenderedSpell.DefaultImpls.cast(this, castingEnvironment, castingImage);
        }
    }
}