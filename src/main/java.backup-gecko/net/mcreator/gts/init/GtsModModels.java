
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.gts.client.model.Modelllamaspit;
import net.mcreator.gts.client.model.ModelGts;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class GtsModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelGts.LAYER_LOCATION, ModelGts::createBodyLayer);
		event.registerLayerDefinition(Modelllamaspit.LAYER_LOCATION, Modelllamaspit::createBodyLayer);
	}
}
