package eu.seahousen.gregcasting.recipecondition;

import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.seahousen.gregcasting.GCRecipeTypes;
import eu.seahousen.gregcasting.GregCasting;
import eu.seahousen.gregcasting.machine.MediaDisplacedWEMM;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class MediaDisplacedCondition extends RecipeCondition<MediaDisplacedCondition> {
    public static final Codec<MediaDisplacedCondition> CODEC = RecordCodecBuilder.create(
            instance -> RecipeCondition.isReverse(instance)
                    .and(Codec.INT.fieldOf("maxTime").forGetter(x -> x.maxTime))
                    .apply(instance, MediaDisplacedCondition::new)
    );

    public int maxTime;
    public int getMaxTime() { return this.maxTime; }
    public void setMaxTime(int maxTime) { this.maxTime = maxTime; }

    public MediaDisplacedCondition(boolean isReverse, int maxTime) {
        super(isReverse);
        this.maxTime = maxTime;
    }

    public MediaDisplacedCondition(int maxTime) {
        this(false, maxTime);
    }

    public MediaDisplacedCondition() {
        this(false, 0);
    }

    @Override
    public RecipeConditionType<MediaDisplacedCondition> getType() {
        return GCRecipeTypes.MEDIA_DISPLACED;
    }

    @Override
    public Component getTooltips() {
        return Component.literal("Should be subjected to forcible media removal to operate");
    }

    @Override
    protected boolean testCondition(@NotNull GTRecipe gtRecipe, @NotNull RecipeLogic recipeLogic) {
        if(recipeLogic.getMachine() instanceof MediaDisplacedWEMM wemm) {
            GregCasting.LOGGER.warn("WEMM: lastcast {} and {}", wemm.lastCastTicks, this.maxTime);
            return wemm.lastCastTicks <= this.maxTime;
        }
        return false;
    }

    @Override
    public MediaDisplacedCondition createTemplate() {
        return new MediaDisplacedCondition();
    }
}
