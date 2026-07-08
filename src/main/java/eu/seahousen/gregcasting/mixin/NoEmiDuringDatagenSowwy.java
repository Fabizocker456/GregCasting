package eu.seahousen.gregcasting.mixin;

import com.gregtechceu.gtceu.GTCEu;
import eu.seahousen.gregcasting.nixim.LoadedReallyEarly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GTCEu.Mods.class)
public class NoEmiDuringDatagenSowwy {
    /**
     * @author f.sch
     * @reason see class name
     */
    @Overwrite(remap = false)
    public static boolean isEMILoaded() {
        if(LoadedReallyEarly.inDatagen) {
            return false;
        }
        return GTCEu.isModLoaded("emi") && (!GTCEu.isClientSide() || dev.emi.emi.config.EmiConfig.enabled);
    }
}
