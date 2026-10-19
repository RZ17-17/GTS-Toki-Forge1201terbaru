
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.gts.entity.model.TokiModel;
import net.mcreator.gts.entity.layer.TokiLayer;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.init.GtsModAttributes;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class TokiRenderer extends GeoEntityRenderer<TokiEntity> {
	public TokiRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TokiModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new TokiLayer(this));
	}

	@Override
	public RenderType getRenderType(TokiEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, TokiEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float scale = (float) Math.max(0.0625D, Math.min(64.0D, entity.getAttributeValue(GtsModAttributes.SCALE.get())));
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(TokiEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
