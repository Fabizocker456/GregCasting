package eu.seahousen.gregcasting;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import at.petrak.hexcasting.common.lib.HexItems;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.neoforged.neoforge.registries.RegisterEvent;

public class Materials {
    public static Material Fluix;

    public static Material SiliconTetrafluoride;

    public static Material SiliconTetrafluorideMedia;
    public static Material SiliconTetrafluorideMediaThick;

    public static Material SiliconTetrafluorideXenomediaThin;;
    public static Material SiliconTetrafluorideXenomedia;


    public static void init() {
        // we are using the gregtech namespace for consistency reasons (and since using MODID errors)

        Fluix = new Material.Builder(GregCasting.idGreg("fluix"))
                .gem()
                .color(0xb955fc).secondaryColor(0x963c94).iconSet(MaterialIconSet.CERTUS)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(GTMaterials.CertusQuartz, 1, GTMaterials.NetherQuartz, 1, GTMaterials.Redstone, 1)
                .buildAndRegister();

        SiliconTetrafluoride = new Material.Builder(GregCasting.idGreg("silicon_tetrafluoride"))
                .gas()
                .color(0x72797f)
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideMedia = new Material.Builder(GregCasting.idGreg("mediated_silicon_tetrafluoride"))
                .gas()
                .color(0x976ebf)
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideMediaThick = new Material.Builder(GregCasting.idGreg("thick_mediated_silicon_tetrafluoride"))
                .gas()
                .color(0x68438e)
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideXenomediaThin = new Material.Builder(GregCasting.idGreg("thin_xenomediated_silicon_tetrafluoride"))
                .gas()
                .color(0x7992a8)
                .components(GTMaterials.Silicon, 1, GTMaterials.Fluorine, 4)
                .buildAndRegister();

        SiliconTetrafluorideXenomedia = new Material.Builder(GregCasting.idGreg("xenomediated_silicon_tetrafluoride"))
                .gas()
                .color(0x6e99bf)
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
        TagPrefix.block.setIgnored(GTMaterials.CertusQuartz, () -> AEBlocks.QUARTZ_BLOCK.asItem());

        TagPrefix.dust.setIgnored(GTMaterials.EnderPearl, () -> AEItems.ENDER_DUST.asItem());
    }
}
