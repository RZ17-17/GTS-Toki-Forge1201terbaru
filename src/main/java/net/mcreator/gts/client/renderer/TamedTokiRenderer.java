
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.gts.entity.model.TamedTokiModel;
import net.mcreator.gts.entity.layer.TamedTokiLayer;
import net.mcreator.gts.entity.TamedTokiEntity;


public class TamedTokiRenderer extends GeoEntityRenderer<TamedTokiEntity> {
	public TamedTokiRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TamedTokiModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new TamedTokiLayer(this));
	}

	@Override
	protected float getDeathMaxRotation(TamedTokiEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
