package eu.seahousen.gregcasting;

import appeng.core.definitions.AEItems;
import at.petrak.hexcasting.common.items.pigment.ItemPridePigment;
import at.petrak.hexcasting.common.lib.HexItems;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeCategories;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import eu.seahousen.gregcasting.recipecondition.MediaDisplacedCondition;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import eu.seahousen.gregcasting.util.Suppliers;

import java.util.Random;
import java.util.function.Consumer;

public class GCRecipes {
    static void initGreg(Consumer<FinishedRecipe> provider) {
        final Material[] PRIDE_CHEMICALS = {
                GTMaterials.Fluorine,
                GTMaterials.HydrochloricAcid,
                GTMaterials.BisphenolA,
                GTMaterials.AminoPhenol,
                GTMaterials.VinylAcetate,
                GTMaterials.AmmoniumFormate,
                GTMaterials.Chlorobenzene,
                GTMaterials.Chloromethane,
                GTMaterials.AquaRegia,
                GTMaterials.Ammonia,
                GTMaterials.UraniumHexafluoride,
                GTMaterials.DiethylenetriaminePentaacetonitrile,
                GTMaterials.DinitrogenTetroxide,
                GTMaterials.GlycerylTrinitrate,
                GTMaterials.NitrationMixture,
                GTMaterials.Butyraldehyde
        };

        long prideShuffleSeed = 24062026; // update occasionally
        Random chemicalShuffleRandom = new Random(prideShuffleSeed);
        for(int i = PRIDE_CHEMICALS.length - 1; i > 0; i--) {
            int j = chemicalShuffleRandom.nextInt(i + 1);
            Material tmp = PRIDE_CHEMICALS[i];
            PRIDE_CHEMICALS[i] = PRIDE_CHEMICALS[j];
            PRIDE_CHEMICALS[j] = tmp;
        }

        for(DyeColor color : DyeColor.values()) {
            String name = color.getName();
            GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder("gregcasting:color_%s".formatted(name))
                    .inputItems(new ItemStack(HexItems.DEFAULT_PIGMENT.asItem()))
                    .inputFluids(GTMaterials.CHEMICAL_DYES[color.ordinal()].getFluid(GTValues.L))
                    .outputItems(new ItemStack(HexItems.DYE_PIGMENTS.get(color).asItem()))
                    .category(GTRecipeCategories.CHEM_DYES)
                    .save(provider);
        }

        // This should not change.
        ItemPridePigment.Type[] types = ItemPridePigment.Type.values();

        if(types.length > PRIDE_CHEMICALS.length) {
            throw new RuntimeException("Too few chemicals for pride pigments: %d < %d".formatted(PRIDE_CHEMICALS.length, types.length));
        }
        GregCasting.LOGGER.info("Registering {} pride pigment recipes with {} chemicals and seed {}", types.length, PRIDE_CHEMICALS.length, prideShuffleSeed);

        for(int i = 0; i < types.length; i++) {
            ItemPridePigment.Type type = types[i];
            Material mat = PRIDE_CHEMICALS[i];
            // noooo they're putting chemicals in the pigments to turn the wisps gay
            GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder("gregcasting:pigment_%s_%s".formatted(mat.getName(), type.getName()))
                    .inputItems(new ItemStack(HexItems.DEFAULT_PIGMENT.asItem()))
                    .inputFluids(mat.getFluid(500))
                    .outputItems(new ItemStack(HexItems.PRIDE_PIGMENTS.get(type)))
                    .category(GTRecipeCategories.CHEM_DYES)
                    .save(provider);
        }

        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder("gregcasting:color_uuid")
                .inputItems(() -> HexItems.DEFAULT_PIGMENT)
                .inputFluids(GTMaterials.BacterialSludge.getFluid(500))
                .outputItems(() -> HexItems.UUID_PIGMENT)
                .category(GTRecipeCategories.CHEM_DYES)
                .save(provider);

        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder("gregcasting:color_ancient")
                .inputItems(() -> HexItems.DEFAULT_PIGMENT)
                .inputFluids(GTMaterials.Copper.getFluid(GTValues.L))
                .outputItems(() -> HexItems.ANCIENT_PIGMENT)
                .category(GTRecipeCategories.CHEM_DYES)
                .save(provider);

        GTRecipeTypes.CANNER_RECIPES.recipeBuilder("gregcasting:color_base")
                .inputItems(TagPrefix.dust, GTMaterials.Amethyst, 1)
                .notConsumable(GTItems.SHAPE_MOLD_PILL)
                .outputItems(() -> HexItems.DEFAULT_PIGMENT)
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting:sif_si_hf")
                .inputItems(TagPrefix.dust, GTMaterials.Silicon, 1)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(GCMaterials.SiliconTetrafluoride.getFluid(1000))
                .outputFluids(GTMaterials.Hydrogen.getFluid(4000))
                .EUt(GTValues.VA[GTValues.MV])
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting:sif_si_f")
                .inputItems(TagPrefix.dust, GTMaterials.Silicon, 1)
                .inputFluids(GTMaterials.Fluorine.getFluid(4000))
                .outputFluids(GCMaterials.SiliconTetrafluoride.getFluid(1000))
                .EUt(GTValues.VA[GTValues.HV])
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting:sif_sio")
                .inputItems(TagPrefix.dust, GTMaterials.SiliconDioxide, 3)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(GCMaterials.SiliconTetrafluoride.getFluid(1000))
                .outputFluids(GTMaterials.Water.getFluid(2000))
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting:sif_m_amethyst")
                .inputItems(TagPrefix.dust, GTMaterials.Amethyst, 3)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(GCMaterials.SiliconTetrafluorideMedia.getFluid(1000))
                .outputFluids(GTMaterials.Water.getFluid(2000))
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting:sif_xmm_certus")
                .inputItems(TagPrefix.dust, GTMaterials.CertusQuartz, 3)
                .inputFluids(GTMaterials.HydrofluoricAcid.getFluid(4000))
                .outputFluids(GCMaterials.SiliconTetrafluorideXenomediaMinus.getFluid(1000))
                .outputFluids(GTMaterials.Water.getFluid(2000))
                .save(provider);

        GCRecipeTypes.GRADIENT_PUMP.recipeBuilder("gregcasting:pumping_m")
                .inputFluids(GCMaterials.SiliconTetrafluorideMedia.getFluid(1000))
                .outputFluids(GCMaterials.SiliconTetrafluorideMediaPlus.getFluid(125))
                .outputFluids(GCMaterials.SiliconTetrafluoride.getFluid(875))
                .addCondition(new MediaDisplacedCondition(20))
                .save(provider);

        GCRecipeTypes.GRADIENT_PUMP.recipeBuilder("gregcasting:pumping_xmm")
                .inputFluids(GCMaterials.SiliconTetrafluorideXenomediaMinus.getFluid(1000))
                .outputFluids(GCMaterials.SiliconTetrafluorideXenomedia.getFluid(250))
                .outputFluids(GCMaterials.SiliconTetrafluoride.getFluid(750))
                .addCondition(new MediaDisplacedCondition(20))
                .save(provider);

        GCRecipeTypes.GRADIENT_PUMP.recipeBuilder("gregcasting:pumping_media_crystal_nether_quartz")
                .inputFluids(GCMaterials.SiliconTetrafluorideMedia.getFluid(5000))
                .outputFluids(GCMaterials.SiliconTetrafluoride.getFluid(5000))
                .inputItems(TagPrefix.gem, GTMaterials.NetherQuartz, 3)
                .outputItems(TagPrefix.gem, GTMaterials.Amethyst, 3)
                // .addCondition(new MediaDisplacedCondition(20)) [nether quartz affinity for media is high enough]
                .save(provider);

        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("gregcasting:sif_mp_degrading")
                .inputFluids(GCMaterials.SiliconTetrafluorideMediaPlus.getFluid(125))
                .inputFluids(GCMaterials.SiliconTetrafluoride.getFluid(875))
                .outputFluids(GCMaterials.SiliconTetrafluorideMedia.getFluid(1000))
                .save(provider);

        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("gregcasting:sif_xm_degrading")
                .inputFluids(GCMaterials.SiliconTetrafluorideXenomedia.getFluid(250))
                .inputFluids(GCMaterials.SiliconTetrafluoride.getFluid(750))
                .outputFluids(GCMaterials.SiliconTetrafluorideXenomediaMinus.getFluid(1000))
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("gregcasting:nasio")
                .inputItems(TagPrefix.dust, GTMaterials.Sodium, 2)
                .inputItems(TagPrefix.dust, GTMaterials.SiliconDioxide, 3)
                .inputFluids(GTMaterials.Oxygen.getFluid(1000))
                .outputItems(TagPrefix.dust, GCMaterials.SodiumSilicate, 6)
                .save(provider);

        VanillaRecipeHelper.addShapedRecipe(provider, true, "gradient_pump", GCMachines.GRADIENT_PUMP.asStack(),
                "P P", " C ", "P P",
                'P', new MaterialEntry(TagPrefix.plate, GTMaterials.ReinforcedEpoxyResin),
                'C', GCBlocks.MEDIA_CASING.get().asItem());

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("gregcasting:mpc")
                .inputItems(TagPrefix.foil, GTMaterials.Lead, 32)
                .inputFluids(GTMaterials.Polyethylene.getFluid(GTValues.L))
                .outputItems(GCBlocks.MEDIA_CASING.get().asItem(), 2)
                .save(provider);

        GTRecipeTypes.CUTTER_RECIPES.recipeBuilder("gregcasting:ae2_sili_from_sili")
                .inputItems(GTItems.SILICON_WAFER)
                .outputItems(Suppliers.ae2(AEItems.SILICON), 4)
                .save(provider);

        GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder("gregcasting:ae2_logic")
                .inputItems(Suppliers.ae2(AEItems.SILICON_PRINT))
                .inputItems(Suppliers.ae2(AEItems.LOGIC_PROCESSOR_PRINT))
                .inputItems(TagPrefix.plate, GTMaterials.Redstone, 1)
                .inputItems(CustomTags.ULV_CIRCUITS)
                .outputItems(Suppliers.ae2(AEItems.LOGIC_PROCESSOR))
                .save(provider);

        GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder("gregcasting:ae2_calculation")
                .inputItems(Suppliers.ae2(AEItems.SILICON_PRINT))
                .inputItems(Suppliers.ae2(AEItems.CALCULATION_PROCESSOR_PRINT))
                .inputItems(TagPrefix.plate, GTMaterials.Redstone, 1)
                .inputItems(CustomTags.LV_CIRCUITS)
                .outputItems(Suppliers.ae2(AEItems.CALCULATION_PROCESSOR))
                .save(provider);

        GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder("gregcasting:ae2_engineering")
                .inputItems(Suppliers.ae2(AEItems.SILICON_PRINT))
                .inputItems(Suppliers.ae2(AEItems.ENGINEERING_PROCESSOR_PRINT))
                .inputItems(TagPrefix.plate, GTMaterials.Redstone, 1)
                .inputItems(CustomTags.MV_CIRCUITS)
                .outputItems(Suppliers.ae2(AEItems.ENGINEERING_PROCESSOR))
                .save(provider);

        VanillaRecipeHelper.addShapedRecipe(provider, true, "gregcasting:charger", GCMachines.GRADIENT_PUMP.asStack(),
                "ABA", "A  ", "ACA",
                'A', new MaterialEntry(TagPrefix.ingot, GTMaterials.Iron),
                'B', new MaterialEntry(TagPrefix.ingot, GTMaterials.Copper),
                'C', GTItems.VOLTAGE_COIL_MV);

        VanillaRecipeHelper.addShapedRecipe(provider, true, "gregcasting:inscriber", GCMachines.GRADIENT_PUMP.asStack(),
                "AEA", "C A", "ADA",
                'A', new MaterialEntry(TagPrefix.ingot, GTMaterials.Iron),
                'C', new MaterialEntry(TagPrefix.ingot, GTMaterials.Copper),
                'D', GTItems.ELECTRIC_PISTON_HV,
                'E', GTItems.EMITTER_HV);
    }
    static void deleteGreg(Consumer<ResourceLocation> consumer) {
        // no free media
        consumer.accept(GregCasting.idGreg("implosion_compressor/implode_dust_amethyst_tnt"));
        consumer.accept(GregCasting.idGreg("implosion_compressor/implode_dust_amethyst_itnt"));
        consumer.accept(GregCasting.idGreg("implosion_compressor/implode_dust_amethyst_dynamite"));
        consumer.accept(GregCasting.idGreg("implosion_compressor/implode_dust_amethyst_powderbarrel"));

        // for now use greggy infrastructure
        deleteOther(consumer);
    }

    static void deleteOther(Consumer<ResourceLocation> consumer) {
        for(DyeColor color : DyeColor.values()) {
            consumer.accept(GregCasting.idHex("dye_colorizer_%s".formatted(color.getName())));
        }
        for(ItemPridePigment.Type type : ItemPridePigment.Type.values()) {
            consumer.accept(GregCasting.idHex("pride_colorizer_%s".formatted(type.getName())));
        }
        consumer.accept(GregCasting.idHex("uuid_colorizer"));
        consumer.accept(GregCasting.idHex("ancient_colorizer"));

        consumer.accept(GregCasting.idAE("inscriber/logic_processor"));
        consumer.accept(GregCasting.idAE("inscriber/calculation_processor"));
        consumer.accept(GregCasting.idAE("inscriber/engineering_processor"));
        consumer.accept(GregCasting.idAE("smelting/silicon_from_certus_quartz_dust"));
        consumer.accept(GregCasting.idAE("blasting/silicon_from_certus_quartz_dust"));

        consumer.accept(GregCasting.idAE("network/blocks/crystal_processing_charger"));
        consumer.accept(GregCasting.idAE("network/blocks/inscribers"));
        consumer.accept(GregCasting.idHex("default_colorizer"));
        consumer.accept(GregCasting.idHex("lens"));
    }
}