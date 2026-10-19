
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.gts.entity.model.TokiDroneModel;
import net.mcreator.gts.entity.layer.TokiDroneLayer;
import net.mcreator.gts.entity.TokiDroneEntity;


public class TokiDroneRenderer extends GeoEntityRenderer<TokiDroneEntity> {
	public TokiDroneRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TokiDroneModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new TokiDroneLayer(this));
	}

}
