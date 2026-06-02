package eu.seahousen.gregcasting;

import at.petrak.hexcasting.common.lib.HexBlocks;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import net.minecraft.world.level.block.Blocks;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.api.pattern.Predicates.controller;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.SIFTER_RECIPES;

public class GCMachines {
    public final static MultiblockMachineDefinition LARGE_SIFTING_FUNNEL = GregCasting.REGISTRATE
            .multiblock("gradient_pump", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(SIFTER_RECIPES)
            .appearanceBlock(() -> GCBlocks.MEDIA_CASING.get())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ZYZ", "YYY", "ZYZ")
                    .aisle("XXX", "KAK", "XXX")
                    .aisle("XXX", "KAK", "XXX")
                    .aisle("XXX", "KAK", "XXX")
                    .aisle("XXX", "KAK", "XXX")
                    .aisle("XXX", "KAK", "XXX")
                    .aisle("ZYZ", "YSY", "ZYZ")
                    .where('S', controller(blocks(definition.get())))
                    .where('X', blocks(GCBlocks.MEDIA_CASING.get()))
                    .where('Y', blocks(GCBlocks.MEDIA_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where('Z', blocks(HexBlocks.SLATE_BLOCK))
                    .where('K', blocks(Blocks.TINTED_GLASS))
                    .where('A', Predicates.air())
                    .build())
            .workableCasingModel(GregCasting.id("block/machine_casing_media_proof"),
                    GregCasting.idMC("block/cobblestone"))
            .register();

    public static void init() {
        GregCasting.LOGGER.info("-- MACHINES REGISTERED --");
    }
}
