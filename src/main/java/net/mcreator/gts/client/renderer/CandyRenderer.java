
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

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
	protected float getDeathMaxRotation(CandyEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
