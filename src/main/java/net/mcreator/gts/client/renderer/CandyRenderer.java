
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.gts.entity.model.CandyModel;
import net.mcreator.gts.entity.layer.CandyLayer;
import net.mcreator.gts.entity.CandyEntity;


public class CandyRenderer extends GeoEntityRenderer<CandyEntity> {
	public CandyRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new CandyModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new CandyLayer(this));
	}

	@Override
	public RenderType getRenderType(CandyEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, CandyEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(CandyEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
