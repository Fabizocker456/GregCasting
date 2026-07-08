package eu.seahousen.gregcasting.mixin;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import at.petrak.hexcasting.common.items.pigment.ItemDyePigment;
import at.petrak.hexcasting.common.items.pigment.ItemPridePigment;
import com.gregtechceu.gtceu.api.cosmetics.CapeRegistry;
import eu.seahousen.gregcasting.GCRecipes;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(at.petrak.hexcasting.common.casting.actions.spells.OpColorize.Spell.class)
public class FreeCapes {
    @Shadow(remap = false)
    @Final
    private ItemStack stack;

    @Inject(method = "cast(Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;)V", at=@At(value = "HEAD"), remap = false)
    public void hereYouMightWantThis(CastingEnvironment env, CallbackInfo ci) {
        if(env instanceof PlayerBasedCastEnv pbce && pbce.getCaster() != null) {
            if (this.stack.getItem() instanceof ItemPridePigment ipp && GCRecipes.PRIDE_CAPES.containsKey(ipp.type)) {
                CapeRegistry.unlockCape(pbce.getCaster().getUUID(), GCRecipes.PRIDE_CAPES.get(ipp.type));
            } else if(this.stack.getItem() instanceof ItemDyePigment idp && GCRecipes.COLOR_CAPES.containsKey(idp.getDyeColor())) {
                CapeRegistry.unlockCape(pbce.getCaster().getUUID(), GCRecipes.COLOR_CAPES.get(idp.getDyeColor()));
            }
        }
    }
}
