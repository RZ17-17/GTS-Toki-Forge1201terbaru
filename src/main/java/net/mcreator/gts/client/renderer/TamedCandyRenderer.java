
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.gts.entity.model.TamedCandyModel;
import net.mcreator.gts.entity.layer.TamedCandyLayer;
import net.mcreator.gts.entity.TamedCandyEntity;


public class TamedCandyRenderer extends GeoEntityRenderer<TamedCandyEntity> {
	public TamedCandyRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TamedCandyModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new TamedCandyLayer(this));
	}

	@Override
	protected float getDeathMaxRotation(TamedCandyEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
