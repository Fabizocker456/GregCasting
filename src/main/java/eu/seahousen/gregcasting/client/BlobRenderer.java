package eu.seahousen.gregcasting.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class BlobRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public static void something(RenderPlayerEvent.Pre rpe) {
        PlayerRenderer pr = rpe.getRenderer();
        pr.addLayer(new BlobRenderer(pr));
    }

    public boolean shouldRenderBlob(AbstractClientPlayer acp) {
        if(acp.isInvisible()) { return false; }
        return true;
    }

    public BlobRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, AbstractClientPlayer player, float a, float b, float c, float d, float e, float f) {
        if(!shouldRenderBlob(player)) return;
    }
}
