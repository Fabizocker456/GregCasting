package eu.seahousen.gregcasting.client;

import eu.seahousen.gregcasting.GregCasting;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = GregCasting.MODID, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        GregCasting.LOGGER.info("onClientSetup");
        GregCasting.LOGGER.info("name {}", Minecraft.getInstance().getUser().getName());
    }
}
