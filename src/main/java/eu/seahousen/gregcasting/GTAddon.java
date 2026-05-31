package eu.seahousen.gregcasting;

import at.petrak.hexcasting.common.lib.HexItems;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

@com.gregtechceu.gtceu.api.addon.GTAddon(GregCasting.MODID)
public class GTAddon implements IGTAddon {
    public static GTRegistrate REGISTRATE = GTRegistrate.create(GregCasting.MODID);

    @Override
    public GTRegistrate getRegistrate() { return GTAddon.REGISTRATE; }

    @Override
    public void gtInitComplete() { GregCasting.LOGGER.info("-- GT INIT COMPLETE --"); }

    @Override
    public void addRecipes(RecipeOutput provider) { Recipes.initGreg(provider); }

    @Override
    public void removeRecipes(Consumer<ResourceLocation> consumer) { Recipes.deleteGreg(consumer); }
}
