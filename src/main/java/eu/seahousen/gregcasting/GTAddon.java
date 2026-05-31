package eu.seahousen.gregcasting;

import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

@com.gregtechceu.gtceu.api.addon.GTAddon
public class GTAddon implements IGTAddon {
    public GTRegistrate REGISTRATE = GTRegistrate.create(GregCasting.MODID);

    @Override
    public GTRegistrate getRegistrate() {
        return REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        GregCasting.LOGGER.info("-- INITIALIZE ADDON --");
    }

    @Override
    public String addonModId() {
        return GregCasting.MODID;
    }
}
