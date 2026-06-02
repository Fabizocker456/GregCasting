package eu.seahousen.gregcasting;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static eu.seahousen.gregcasting.GregCasting.REGISTRATE;

@GTAddon
public class GCAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        GregCasting.LOGGER.info("-- INITIALIZE ADDON --");
    }

    @Override
    public String addonModId() {
        return GregCasting.MODID;
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        GCRecipes.initGreg(provider);
    }

    @Override
    public void removeRecipes(Consumer<ResourceLocation> consumer) {
        GCRecipes.deleteGreg(consumer);
    }
}
