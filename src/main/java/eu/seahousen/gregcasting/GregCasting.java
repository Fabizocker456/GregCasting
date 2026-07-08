package eu.seahousen.gregcasting;

import appeng.core.AppEng;
import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.common.lib.HexRegistries;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.data.medicalcondition.MedicalCondition;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.providers.ProviderType;
import eu.seahousen.gregcasting.datagen.GCDatagen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Mod(GregCasting.MODID)
public class GregCasting {

    public static final String MODID = "gregcasting";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static MinecraftServer server = null;
    public static String buildMeta;
    public static Map<?, ?> buildMetaParsed;

    public static GregCasting INSTANCE;

    static {
        LOGGER.info("-- GREGCASTING: CLINIT --");
    }

    // Bite the bullet. Use a Registrate.
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GregCasting.MODID);

    public GregCasting(FMLJavaModLoadingContext jmlc) {
        INSTANCE = this;

        try {
            buildMeta = new String(
                    GregCasting.class.getClassLoader().getResourceAsStream("/build-meta.json").readAllBytes(),
                    StandardCharsets.UTF_8
            );
            buildMetaParsed = new Gson().fromJson(buildMeta, Map.class);
        } catch (IOException | NullPointerException e) {
            buildMeta = null;
            buildMetaParsed = Map.of();
            LOGGER.error("Could not load build metadata: {}", e.toString());
        } catch (JsonSyntaxException e) {
            buildMeta = null;
            buildMetaParsed = Map.of();
            LOGGER.error("Could not parse build metadata JSON: {}", e.toString());
        }

        IEventBus modEventBus = jmlc.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        jmlc.registerConfig(ModConfig.Type.COMMON, GCConfig.SPEC);

        modEventBus.addListener(this::materials);
        modEventBus.addListener(this::afterMaterials);
        modEventBus.addListener(this::register);
        modEventBus.addListener(this::datagen);
        modEventBus.addGenericListener(MachineDefinition.class, this::registerMachines);
        modEventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        modEventBus.addGenericListener(MedicalCondition.class, this::registerMedicalConditions);
        modEventBus.addGenericListener(RecipeConditionType.class, this::registerRecipeConditions);
        modEventBus.addGenericListener(ItemStack.class, this::registerItemStackCapabilities);
        GCBlocks.init();
        GCItems.init();
        REGISTRATE.addDataGenerator(ProviderType.LANG, GCLanguage::init);
    }

    public static ResourceLocation id(String name) { return ResourceLocation.fromNamespaceAndPath(MODID, name); }
    public static ResourceLocation idGreg(String name) { return GTCEu.id(name); }
    public static ResourceLocation idHex(String name) { return HexAPI.modLoc(name); }
    public static ResourceLocation idMC(String name) { return ResourceLocation.fromNamespaceAndPath("minecraft", name); }
    public static ResourceLocation idAE(String name) { return AppEng.makeId(name); }

    private void register(RegisterEvent re) {
        ResourceKey<?> key = re.getRegistryKey();
        LOGGER.info("-- REGISTER {} --", key);
        if(HexRegistries.ACTION.equals(key)) {
            GCActions.init(re);
        }
    }

    private void materials(MaterialEvent me) {
        GCMaterials.init();
    }

    private void afterMaterials(PostMaterialEvent pme) {
        GCMaterials.fixExisting();
    }

    private void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> re) {
        GCMachines.init();
    }

    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> re) {
        GCRecipeTypes.init();
    }

    private void registerMedicalConditions(GTCEuAPI.RegisterEvent<ResourceLocation, MedicalCondition> re) {
        GCMedicalConditions.init();
    }

    private void registerRecipeConditions(GTCEuAPI.RegisterEvent<String, RecipeConditionType<?>> re) {
        GCRecipeTypes.initConditions();
    }

    private void datagen(GatherDataEvent gde) {
        LOGGER.info("-- DATAGEN --");
        GCDatagen.main(gde);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("-- COMMON SETUP --");
    }

    private void registerItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> ace) {
        GCItems.capabilities(ace);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        LOGGER.info("-- BUILD CREATIVE MODE TAB CONTENTS --");
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
    }
}
