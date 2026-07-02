package eu.seahousen.gregcasting.mixin;

import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import eu.seahousen.gregcasting.mixinutil.IGetDebugData;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;

@Mixin(RecipeLogic.class)
public abstract class LetMeDebugInPeace implements IGetDebugData {
    @Shadow(remap = false)
    public abstract RecipeLogic.Status getStatus();

    @Shadow(remap = false)
    @Nullable
    protected TickableSubscription subscription;

    @Shadow(remap = false)
    public abstract @Nullable Component getWaitingReason();

    @Shadow(remap = false)
    public abstract Map<GTRecipe, Component> getFailureReasonMap();

    @Shadow(remap = false)
    private @Nullable Component waitingReason;

    @Override
    public String gregcasting$getDebugData() {
        StringBuilder waitingreasons = new StringBuilder();
        if(this.getWaitingReason() != null) {
            waitingreasons.append("waitingreason: ");
            waitingreasons.append(this.getWaitingReason().getString());
            waitingreasons.append(". ");
        }
        waitingreasons.append("failure: (");
        for(Map.Entry<GTRecipe, Component> i : this.getFailureReasonMap().entrySet()) {
            waitingreasons.append(i.getKey().id);
            waitingreasons.append(": ");
            waitingreasons.append(i.getValue().getString());
            waitingreasons.append(". ");
        }
        waitingreasons.append(")");

        return "status: %s. ticking: %s. wait: (%s). ".formatted(this.getStatus(), this.subscription != null, waitingreasons);
    }
}
