package net.mcreator.gts.client.renderer;

import net.mcreator.gts.entity.FireflyEntity;
import net.mcreator.gts.entity.model.FireflyModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireflyRenderer extends GeoEntityRenderer<FireflyEntity> {
    public FireflyRenderer(EntityRendererProvider.Context context) {
        super(context, new FireflyModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public RenderType getRenderType(FireflyEntity entity, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(entity));
    }
}
