package eu.seahousen.gregcasting;

import brachy.modularui.drawable.GuiTextures;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.mui.GTGuiTextures;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import eu.seahousen.gregcasting.recipecondition.MediaDisplacedCondition;

public class GCRecipeTypes {
    public static GTRecipeType GRADIENT_PUMP;

    static void init() {
        GregCasting.LOGGER.info("-- INIT RECIPE TYPES --");
        GRADIENT_PUMP = GTRecipeTypes.register("gregcasting:gradient_pumping", GTRecipeTypes.MULTIBLOCK)
                .setEUIO(IO.IN)
                .setMaxIOSize(2, 2, 2, 2)
                .UI(builder -> builder
                        .setProgressBar(GTGuiTextures.PROGRESS_PACKER)
                )
                .setSound(GTSoundEntries.BOILER);
    }

    public static RecipeConditionType<MediaDisplacedCondition> MEDIA_DISPLACED = new RecipeConditionType<>(MediaDisplacedCondition::new, MediaDisplacedCondition.CODEC);

    static void initConditions() {
        GTRegistries.RECIPE_CONDITIONS.register(GregCasting.id("media_displaced"), MEDIA_DISPLACED);
    }
}
