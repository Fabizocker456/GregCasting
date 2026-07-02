package eu.seahousen.gregcasting;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.materials.GTFoods;
import com.gregtechceu.gtceu.common.item.behavior.AntidoteBehavior;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class GCItems {
    static ItemEntry<ComponentItem> MEDIA_PILL = GregCasting.REGISTRATE.item("media_pill", ComponentItem::create)
            .lang("Media Substitution Pill")
            .properties(p -> p.food(GTFoods.ANTIDOTE))
            .onRegister(i -> { i.attachComponents(new AntidoteBehavior(50, GCMedicalConditions.XENOMEDIA)); })
            .register();

    public static void init() {
        GregCasting.LOGGER.info("-- ITEMS INITIALIZED --");
    }
}
