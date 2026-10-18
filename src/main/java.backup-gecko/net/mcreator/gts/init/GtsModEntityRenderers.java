
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.gts.client.renderer.TokiRenderer;
import net.mcreator.gts.client.renderer.TokiDroneRenderer;
import net.mcreator.gts.client.renderer.TamedTokiRenderer;
import net.mcreator.gts.client.renderer.TamedCandyRenderer;
import net.mcreator.gts.client.renderer.HeartBulletRenderer;
import net.mcreator.gts.client.renderer.GiantessSpitRenderer;
import net.mcreator.gts.client.renderer.CandyRenderer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GtsModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(GtsModEntities.TOKI.get(), TokiRenderer::new);
		event.registerEntityRenderer(GtsModEntities.TOKI_DRONE.get(), TokiDroneRenderer::new);
		event.registerEntityRenderer(GtsModEntities.GIANTESS_SPIT.get(), GiantessSpitRenderer::new);
		event.registerEntityRenderer(GtsModEntities.CANDY.get(), CandyRenderer::new);
		event.registerEntityRenderer(GtsModEntities.HEART_BULLET.get(), HeartBulletRenderer::new);
		event.registerEntityRenderer(GtsModEntities.TAMED_TOKI.get(), TamedTokiRenderer::new);
		event.registerEntityRenderer(GtsModEntities.TAMED_CANDY.get(), TamedCandyRenderer::new);
	}
}
