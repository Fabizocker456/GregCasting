package eu.seahousen.gregcasting;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.forge.cap.ForgeCapabilityHandler;
import at.petrak.hexcasting.forge.cap.HexCapabilities;
import at.petrak.hexcasting.forge.cap.adimpl.CapStaticMediaHolder;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.materials.GTFoods;
import com.gregtechceu.gtceu.common.item.behavior.AntidoteBehavior;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class GCItems {
    static ItemEntry<ComponentItem> MEDIA_PILL = GregCasting.REGISTRATE.item("media_pill", ComponentItem::create)
            .lang("Media Substitution Pill")
            .properties(p -> p.food(GTFoods.ANTIDOTE))
            .onRegister(i -> { i.attachComponents(new AntidoteBehavior(50, GCMedicalConditions.XENOMEDIA)); })
            .register();

    static ItemEntry<Item> REALLY_DENSE_DUST = GregCasting.REGISTRATE.item("dense_dust", Item::new)
            .lang("Incredibly Dense Media Dust")
            .register();

    public static void capabilities(AttachCapabilitiesEvent<ItemStack> ace) {
        ItemStack is = ace.getObject();
        if(is.getItem() == REALLY_DENSE_DUST.asItem()) {
            ace.addCapability(
                    ForgeCapabilityHandler.MEDIA_STATIC_CAP,
                    ForgeCapabilityHandler.provide(is, HexCapabilities.MEDIA,
                            () -> new CapStaticMediaHolder(
                                    () -> 256 * MediaConstants.DUST_UNIT,
                                    700,
                                    is
                            ))
            );
        }
    }

    public static void init() {
        GregCasting.LOGGER.info("-- ITEMS INITIALIZED --");
    }
}
