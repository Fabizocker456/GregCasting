package eu.seahousen.gregcasting.machine;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import eu.seahousen.gregcasting.GregCasting;
import eu.seahousen.gregcasting.casting.IDisplaceable;
import eu.seahousen.gregcasting.mixinutil.IGetDebugData;

public class MediaDisplacedWEMM extends WorkableElectricMultiblockMachine implements IDisplaceable, IGetDebugData {
    static final int LAST_TICK_MAX = 20 * 60 * 60 * 24; // should be enough...

    @SaveField
    public int lastCastTicks = LAST_TICK_MAX;
    TickableSubscription increment = null;

    public MediaDisplacedWEMM(BlockEntityCreationInfo info, RecipeLogic recipeLogic) {
        super(info, recipeLogic);
    }

    public MediaDisplacedWEMM(BlockEntityCreationInfo info) {
        super(info);
    }

    @Override
    public void displaceMedia() {
        this.lastCastTicks = 0;
        this.recipeLogic.markLastRecipeDirty();
        this.setDirty(true);
    }

    @Override
    public void onUnload() {
        unsubscribe(increment);
        increment = null;
    }

    void incrementCounter() {
        if(this.lastCastTicks < LAST_TICK_MAX) {
            this.lastCastTicks += 1;
            this.setDirty(true);
        }
    }

    @Override
    public String gregcasting$getDebugData() {
        return "lastcast: %d. recipelogic: (%s). ".formatted(
                this.lastCastTicks,
                ((IGetDebugData) this.recipeLogic).gregcasting$getDebugData()
        );
    }
}
