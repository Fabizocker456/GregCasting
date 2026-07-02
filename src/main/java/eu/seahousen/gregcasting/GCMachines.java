package eu.seahousen.gregcasting;

import at.petrak.hexcasting.common.lib.HexBlocks;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.multiblock.Predicates;
import com.gregtechceu.gtceu.api.multiblock.pattern.MultiblockPatternBuilder;
import eu.seahousen.gregcasting.machine.MediaDisplacedWEMM;
import net.minecraft.world.level.block.Blocks;

import static com.gregtechceu.gtceu.api.multiblock.Predicates.blocks;
import static com.gregtechceu.gtceu.api.multiblock.Predicates.controller;


public class GCMachines {
    public final static MultiblockMachineDefinition GRADIENT_PUMP = GregCasting.REGISTRATE
            .multiblock("gradient_pump", MediaDisplacedWEMM::new)
            .rotationState(RotationState.ALL)
            .recipeType(GCRecipeTypes.GRADIENT_PUMP)
            .appearanceBlock(() -> GCBlocks.MEDIA_CASING.get())
            .pattern(definition -> MultiblockPatternBuilder.start()
                    .slice("ZYZ", "YSY", "ZYZ")
                    .slice("XXX", "KAK", "XXX")
                    .slice("XXX", "KAK", "XXX")
                    .slice("XXX", "KAK", "XXX")
                    .slice("XXX", "KAK", "XXX")
                    .slice("XXX", "KAK", "XXX")
                    .slice("ZYZ", "YYY", "ZYZ")
                    .where('S', controller(blocks(definition.get())))
                    .where('X', blocks(GCBlocks.MEDIA_CASING.get()))
                    .where('Y', blocks(GCBlocks.MEDIA_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where('Z', blocks(HexBlocks.SLATE_BLOCK))
                    .where('K', blocks(Blocks.TINTED_GLASS))
                    .where('A', Predicates.air())
                    .build())
            .workableCasingModel(GregCasting.id("block/machine_casing_media_proof"),
                    GregCasting.id("block/multiblock/gradient_pump"))
            .langValue("Ethereal Energy Gradient Pump")
            .register();

    public static void init() {
        GregCasting.LOGGER.info("-- MACHINES REGISTERED --");
    }
}
