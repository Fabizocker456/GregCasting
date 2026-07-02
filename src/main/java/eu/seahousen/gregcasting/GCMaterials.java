package eu.seahousen.gregcasting;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import at.petrak.hexcasting.common.lib.HexItems;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.HazardProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import eu.seahousen.gregcasting.util.Suppliers;

public class GCMaterials {
    public static Material Fluix;
    public static Material MediaProofAlloy;
    public static Material SodiumSilicate;

    public static Material SiliconTetrafluoride;
    public static Material SiliconTetrafluorideMedia;
    public static Material SiliconTetrafluorideMediaPlus;
    public static Material SiliconTetrafluorideXenomedia;
    public static Material SiliconTetrafluorideXenomediaMinus;


    public static void init() {
        Fluix = new Material.Builder(GregCasting.id("fluix"))
                .gem()
                .color(0xb955fc).secondaryColor(0x963c94).iconSet(MaterialIconSet.CERTUS)
                .langValue("Fluix")
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(GTMaterials.CertusQuartz, 1, GTMaterials.NetherQuartz, 1, GTMaterials.Redstone, 1)
                .buildAndRegister();

        SodiumSilicate = new Material.Builder(GregCasting.id("sodium_silicate"))
                .dust()
                .color(0xc7c7c9).secondaryColor(0xa09fa3)
                .langValue("Sodium Silicate")
                .components(GTMaterials.Sodium, 2, GTMaterials.Silicon, 1, GTMaterials.Oxygen, 3)
                .buildAndRegister();

        SiliconTetrafluoride = new Material.Builder(GregCasting.id("silicon_tetrafluoride"))
                .gas()
                .color(0x72797f)
                .langValue("Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideMedia = new Material.Builder(GregCasting.id("silicon_tetrafluoride_media"))
                .gas()
                .color(0x976ebf)
                .langValue("Media-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideMediaPlus = new Material.Builder(GregCasting.id("silicon_tetrafluoride_media_plus"))
                .gas()
                .color(0x68438e)
                .langValue("Rich Media-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideXenomediaMinus = new Material.Builder(GregCasting.id("silicon_tetrafluoride_xmedia"))
                .gas()
                .color(0x7992a8)
                .langValue("Sparse Xenomedia-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .hazard(HazardProperty.HazardTrigger.INHALATION, GCMedicalConditions.XENOMEDIA)
                .buildAndRegister();

        SiliconTetrafluorideXenomedia = new Material.Builder(GregCasting.id("silicon_tetrafluoride_xmedia_minus"))
                .gas()
                .color(0x6e99bf)
                .langValue("Xenomedia-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .hazard(HazardProperty.HazardTrigger.INHALATION, GCMedicalConditions.XENOMEDIA, 4.0F)
                .buildAndRegister();

        TagPrefix.gem.setIgnored(Fluix, () -> AEItems.FLUIX_CRYSTAL.asItem());
        TagPrefix.dust.setIgnored(Fluix, () -> AEItems.FLUIX_DUST.asItem());
        TagPrefix.block.setIgnoredBlock(Fluix, AEBlocks.FLUIX_BLOCK.block());

        TagPrefix.block.modifyMaterialAmount(Fluix, 4.0F);
    }

    public static void fixExisting() {
        GTMaterials.Amethyst.setComponents(new MaterialStack(GTMaterials.SiliconDioxide, 1));
        GTMaterials.Amethyst.setFormula("SiO2", true);
        TagPrefix.dust.setIgnored(GTMaterials.Amethyst, () -> HexItems.AMETHYST_DUST);
        TagPrefix.gemFlawless.setIgnored(GTMaterials.Amethyst, () -> HexItems.CHARGED_AMETHYST);

        TagPrefix.gem.setIgnored(GTMaterials.CertusQuartz, Suppliers.ae2(AEItems.CERTUS_QUARTZ_CRYSTAL));
        TagPrefix.dust.setIgnored(GTMaterials.CertusQuartz, Suppliers.ae2(AEItems.CERTUS_QUARTZ_DUST));;
        TagPrefix.block.setIgnoredBlock(GTMaterials.CertusQuartz, AEBlocks.QUARTZ_BLOCK.block());

        TagPrefix.dust.setIgnored(GTMaterials.EnderPearl, Suppliers.ae2(AEItems.ENDER_DUST));

        TagPrefix.block.modifyMaterialAmount(GTMaterials.Quartzite, 4.0F);

        GTMaterials.Glass.addFlags(MaterialFlags.GENERATE_FOIL, MaterialFlags.GENERATE_FINE_WIRE);
    }
}