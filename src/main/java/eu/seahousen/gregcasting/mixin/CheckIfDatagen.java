package eu.seahousen.gregcasting.mixin;

import eu.seahousen.gregcasting.nixim.LoadedReallyEarly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.data.Main.class)
public class CheckIfDatagen {
    @Inject(method = "main", at = @At(value = "HEAD"))
    private static void yesWereInDatagen(String[] p_129669_, CallbackInfo ci) {
        LoadedReallyEarly.inDatagen = true;
    }
}
