package eu.seahousen.gregcasting.datagen;

import com.gregtechceu.gtceu.api.registry.registrate.provider.GTBlockstateProvider;
import com.gregtechceu.gtceu.data.model.BlockstateModelLoader;
import com.tterrag.registrate.providers.ProviderType;
import eu.seahousen.gregcasting.GCAddon;
import eu.seahousen.gregcasting.GregCasting;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class GCDatagen {
    public static void main(GatherDataEvent gde) {
        if(gde.includeClient()) {
            GregCasting.LOGGER.info("-- DATAGEN CLIENT --");
            generateClient(gde);
        }
        if(gde.includeServer()) {
            GregCasting.LOGGER.info("-- DATAGEN SERVER --");
        }
        if(gde.includeDev()) {
            GregCasting.LOGGER.info("-- DATAGEN DEV --");
        }
        if(gde.includeReports()) {
            GregCasting.LOGGER.info("-- DATAGEN REPORTS --");
        }
    }

    static void generateClient(GatherDataEvent gde) {
        DataGenerator gen = gde.getGenerator();
        ExistingFileHelper efh = gde.getExistingFileHelper();

        gen.addProvider(true, (DataProvider.Factory<SimpleBlockStateProvider>) out -> new SimpleBlockStateProvider(out, GregCasting.MODID, efh));
    }
}
