package eu.seahousen.gregcasting;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import at.petrak.hexcasting.common.lib.HexItems;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

public class GCMaterials {
    public static Material Fluix;

    public static Material SiliconTetrafluoride;

    public static Material SiliconTetrafluorideMedia;
    public static Material SiliconTetrafluorideMediaThick;

    public static Material SiliconTetrafluorideXenomediaThin;;
    public static Material SiliconTetrafluorideXenomedia;


    public static void init() {
        Fluix = new Material.Builder(GregCasting.id("fluix"))
                .gem()
                .color(0xb955fc).secondaryColor(0x963c94).iconSet(MaterialIconSet.CERTUS)
                .langValue("Fluix")
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(GTMaterials.CertusQuartz, 1, GTMaterials.NetherQuartz, 1, GTMaterials.Redstone, 1)
                .buildAndRegister();

        SiliconTetrafluoride = new Material.Builder(GregCasting.id("silicon_tetrafluoride"))
                .gas()
                .color(0x72797f)
                .langValue("SIlicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideMedia = new Material.Builder(GregCasting.id("silicon_tetrafluoride_media"))
                .gas()
                .color(0x976ebf)
                .langValue("Media-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideMediaThick = new Material.Builder(GregCasting.id("silicon_tetrafluoride_media_plus"))
                .gas()
                .color(0x68438e)
                .langValue("Rich Media-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideXenomediaThin = new Material.Builder(GregCasting.id("silicon_tetrafluoride_xmedia"))
                .gas()
                .color(0x7992a8)
                .langValue("Xenomedia-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideXenomedia = new Material.Builder(GregCasting.id("silicon_tetrafluoride_xmedia_minus"))
                .gas()
                .color(0x6e99bf)
                .langValue("Sparse Xenomedia-Silicon Tetrafluoride")
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        TagPrefix.gem.setIgnored(Fluix, () -> AEItems.FLUIX_CRYSTAL.asItem());
        TagPrefix.dust.setIgnored(Fluix, () -> AEItems.FLUIX_DUST.asItem());
        TagPrefix.block.setIgnored(Fluix, () -> AEBlocks.FLUIX_BLOCK.asItem());

        TagPrefix.block.modifyMaterialAmount(Fluix, 4.0F);
    }

    public static void fixExisting() {
        GTMaterials.Amethyst.setComponents(new MaterialStack(GTMaterials.SiliconDioxide, 1));
        GTMaterials.Amethyst.setFormula("SiO2", true);
        TagPrefix.dust.setIgnored(GTMaterials.Amethyst, () -> HexItems.AMETHYST_DUST);

        TagPrefix.gem.setIgnored(GTMaterials.CertusQuartz, () -> AEItems.CERTUS_QUARTZ_CRYSTAL.asItem());
        TagPrefix.dust.setIgnored(GTMaterials.CertusQuartz, () -> AEItems.CERTUS_QUARTZ_DUST.asItem());
        TagPrefix.block.setIgnoredBlock(GTMaterials.CertusQuartz, AEBlocks.QUARTZ_BLOCK.block());

        TagPrefix.dust.setIgnored(GTMaterials.EnderPearl, () -> AEItems.ENDER_DUST.asItem());
    }
}