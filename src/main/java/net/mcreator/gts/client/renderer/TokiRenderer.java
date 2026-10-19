
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.gts.entity.model.TokiModel;
import net.mcreator.gts.entity.layer.TokiLayer;
import net.mcreator.gts.entity.TokiEntity;


public class TokiRenderer extends GeoEntityRenderer<TokiEntity> {
	public TokiRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TokiModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new TokiLayer(this));
	}

	@Override
	protected float getDeathMaxRotation(TokiEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
