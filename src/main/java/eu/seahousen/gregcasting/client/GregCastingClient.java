package eu.seahousen.gregcasting.client;

import eu.seahousen.gregcasting.GregCasting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = GregCasting.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GregCastingClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        GregCasting.LOGGER.info("-- CLIENT SETUP EVENT --");
        GregCasting.LOGGER.info("name {}", Minecraft.getInstance().getUser().getName());
    }
}
