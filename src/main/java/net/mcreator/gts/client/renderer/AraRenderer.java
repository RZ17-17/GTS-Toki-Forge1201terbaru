package net.mcreator.gts.client.renderer;

import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.entity.model.AraModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AraRenderer extends GeoEntityRenderer<AraEntity> {
    public AraRenderer(EntityRendererProvider.Context context) {
        super(context, new AraModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public RenderType getRenderType(AraEntity entity, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(entity));
    }
}
