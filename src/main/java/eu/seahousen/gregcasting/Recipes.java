package eu.seahousen.gregcasting;

import at.petrak.hexcasting.common.lib.HexItems;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeCategories;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class Recipes {
    static void initGreg(RecipeOutput provider) {
        for(DyeColor color : DyeColor.values()) {
            String name = color.getName();
            GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder("gregcasting/blank_to_%s".formatted(name))
                    .inputItems(new ItemStack(HexItems.DEFAULT_PIGMENT.asItem()))
                    .inputFluids(GTMaterials.CHEMICAL_DYES[color.ordinal()].getFluid(100))
                    .outputItems(new ItemStack(HexItems.DYE_PIGMENTS.get(color).asItem()))
                    .category(GTRecipeCategories.CHEM_DYES)
                    .save(provider);
        }

        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder("gregcasting/uuid")
                .inputItems(new ItemStack(HexItems.DEFAULT_PIGMENT.asItem()))
                .inputFluids(GTMaterials.BacterialSludge.getFluid(GTValues.L))
                .outputItems(new ItemStack(HexItems.UUID_PIGMENT.asItem()))
                .category(GTRecipeCategories.CHEM_DYES)
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting/silicon_tetrafluoride")
                .inputItems(TagPrefix.dust, GTMaterials.Silicon, 1)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(Materials.SiliconTetrafluoride.getFluid(1000))
                .outputFluids(GTMaterials.Hydrogen.getFluid(4000))
                .EUt(GTValues.VA[GTValues.HV])
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting/silicon_tetrafluoride_dioxide")
                .inputItems(TagPrefix.dust, GTMaterials.SiliconDioxide, 3)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(Materials.SiliconTetrafluoride.getFluid(1000))
                .outputFluids(GTMaterials.Water.getFluid(2000))
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting/silicon_tetrafluoride_amethyst")
                .inputItems(TagPrefix.dust, GTMaterials.Amethyst, 3)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(Materials.SiliconTetrafluorideMedia.getFluid(1000))
                .outputFluids(GTMaterials.Water.getFluid(2000))
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting/silicon_tetrafluoride_certus")
                .inputItems(TagPrefix.dust, GTMaterials.CertusQuartz, 3)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(Materials.SiliconTetrafluorideXenomediaThin.getFluid(1000))
                .outputFluids(GTMaterials.Water.getFluid(2000))
                .save(provider);
    }
    static void deleteGreg(Consumer<ResourceLocation> consumer) {
        // no free media
        consumer.accept(GregCasting.idGreg("implode_dust_amethyst_tnt"));
        consumer.accept(GregCasting.idGreg("implode_dust_amethyst_itnt"));
        consumer.accept(GregCasting.idGreg("implode_dust_amethyst_dynamite"));
        consumer.accept(GregCasting.idGreg("implode_dust_amethyst_powderbarrel"));

        // for now use greg's infrastructure
        deleteOther(consumer);
    }

    static void deleteOther(Consumer<ResourceLocation> consumer) {
        for(DyeColor color : DyeColor.values()) {
            consumer.accept(GregCasting.idHex("dye_colorizer_%s".formatted(color.getName())));
        }
        consumer.accept(GregCasting.idHex("uuid_colorizer"));
    }
}
