package net.mcreator.gts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.gts.entity.FireflyEntity;
import net.mcreator.gts.entity.model.FireflyModel;
import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
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

    @Override
    public void preRender(PoseStack poseStack, FireflyEntity entity, BakedGeoModel model,
            MultiBufferSource buffers, VertexConsumer buffer, boolean reRender, float partialTick,
            int light, int overlay, float red, float green, float blue, float alpha) {
        // Firefly uses a large GeckoLib model. Guard the optional scale attribute
        // and apply the same bounded visual scaling as its tamed variant so a
        // missing/uninitialized attribute cannot break entity rendering.
        double rawScale = entity.getAttributes().hasAttribute(GtsModAttributes.SCALE.get())
                ? entity.getAttributeValue(GtsModAttributes.SCALE.get()) : 1.0D;
        float scale = (float) Math.max(0.0625D, Math.min(64.0D, rawScale));
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, buffers, buffer, reRender, partialTick,
                light, overlay, red, green, blue, alpha);
    }
}
