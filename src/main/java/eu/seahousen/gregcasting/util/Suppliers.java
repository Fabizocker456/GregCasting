package eu.seahousen.gregcasting.util;

import appeng.core.definitions.ItemDefinition;
import com.gregtechceu.gtceu.utils.memoization.GTMemoizer;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class Suppliers {
    public static Supplier<Item> ae2(ItemDefinition<? extends Item> def) {
        return GTMemoizer.memoize(def::asItem);
    }
}
