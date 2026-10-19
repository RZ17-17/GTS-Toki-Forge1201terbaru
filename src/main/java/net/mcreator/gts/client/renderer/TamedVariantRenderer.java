package net.mcreator.gts.client.renderer;

import net.mcreator.gts.entity.TokiVariantEntity;
import net.mcreator.gts.entity.model.TamedVariantModel;
import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TamedVariantRenderer extends GeoEntityRenderer<TokiVariantEntity> {
    public TamedVariantRenderer(EntityRendererProvider.Context context) {
        super(context, new TamedVariantModel());
        this.shadowRadius = 0.5F;
    }
    @Override public RenderType getRenderType(TokiVariantEntity entity, ResourceLocation texture, MultiBufferSource buffers, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(entity));
    }
    @Override public void preRender(PoseStack poseStack, TokiVariantEntity entity, BakedGeoModel model, MultiBufferSource buffers, VertexConsumer buffer, boolean reRender, float partialTick, int light, int overlay, float red, float green, float blue, float alpha) {
        float scale = (float)Math.max(0.0625D, Math.min(64.0D, entity.getAttributeValue(GtsModAttributes.SCALE.get())));
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, buffers, buffer, reRender, partialTick, light, overlay, red, green, blue, alpha);
    }
}
