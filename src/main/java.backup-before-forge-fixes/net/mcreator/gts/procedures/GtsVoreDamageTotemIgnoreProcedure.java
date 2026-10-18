package net.mcreator.gts.procedures;

import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.ICancellableEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.CandyEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class GtsVoreDamageTotemIgnoreProcedure {
	@SubscribeEvent
	public static void whenEntityUsesTotem(LivingUseTotemEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity());
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (!((entity.getVehicle()) == null) && (((entity.getVehicle()) instanceof TokiEntity _datEntS ? _datEntS.getEntityData().get(TokiEntity.DATA_AnimationDecision) : "").contains("Vore")
				|| ((entity.getVehicle()) instanceof CandyEntity _datEntS ? _datEntS.getEntityData().get(CandyEntity.DATA_AnimationDecision) : "").contains("Vore"))) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
		}
	}
}
