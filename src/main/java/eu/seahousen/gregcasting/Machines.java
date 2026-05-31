package eu.seahousen.gregcasting;

import at.petrak.hexcasting.common.lib.HexBlocks;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.SIFTER_RECIPES;

public class Machines {
    public static MultiblockMachineDefinition GRADIENT_PUMP;

    public static void init(GTRegistrate registrate) {
        GRADIENT_PUMP = registrate.multiblock("gradient_pump", WorkableElectricMultiblockMachine::new)
                .rotationState(RotationState.ALL)
                .recipeType(SIFTER_RECIPES)
                .recipeModifiers()
                .appearanceBlock(() -> HexBlocks.SLATE_BLOCK)
                .pattern(
                        def ->
                        FactoryBlockPattern.start()
                                .aisle("AAA", "AAA", "AAA")
                                .aisle("AAA", "AAA", "AAC")
                                .aisle("AAA", "AAA", "AAA")
                                .where("C", Predicates.controller(Predicates.blocks(def.get())))
                                .where("A", Predicates.blocks(HexBlocks.SLATE_BLOCK))
                                .build()
                )
                .workableCasingModel(GregCasting.idHex("slate_block"), GregCasting.idMC("amethyst_block"))
                .register();
    }
}
