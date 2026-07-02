package eu.seahousen.gregcasting;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import eu.seahousen.gregcasting.casting.ExamplePatternGenerator;
import eu.seahousen.gregcasting.casting.OPDebugMachine;
import eu.seahousen.gregcasting.casting.OPDisplaceMedia;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;

public class GCActions {
    public static HashMap<String, ActionRegistryEntry> ACTIONS = new HashMap<>();

    static ActionRegistryEntry DISPLACE_MEDIA = action("displace_media", ExamplePatternGenerator.makeExamplePattern(), OPDisplaceMedia.INSTANCE);
    static ActionRegistryEntry DEBUG_MACHINE = action("debug_machine", ExamplePatternGenerator.makeExamplePattern(), OPDebugMachine.INSTANCE);

    static ActionRegistryEntry action(String name, HexPattern pattern, Action action) {
        GregCasting.LOGGER.info("-- CREATING ACTION {} --", name);
        ActionRegistryEntry entry = new ActionRegistryEntry(pattern, action);
        ACTIONS.put(name, entry);
        return entry;
    }

    public static void init(RegisterEvent event) {
        for(String i : ACTIONS.keySet()) {
            GregCasting.LOGGER.info("-- REGISTERING ACTION {} --", i);
            event.register(HexRegistries.ACTION, GregCasting.id(i), () -> ACTIONS.get(i));
            GregCasting.LOGGER.info(ACTIONS.get(i).toString());
        }
    }
}
