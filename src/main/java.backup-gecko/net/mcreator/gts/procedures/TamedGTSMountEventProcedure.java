package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.ICancellableEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TamedCandyEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class TamedGTSMountEventProcedure {
	@SubscribeEvent
	public static void onEventTriggered(EntityMountEvent event) {
		execute(event, event.getEntityBeingMounted(), event.getEntityMounting(), event.isMounting());
	}

	public static void execute(Entity entity, Entity sourceentity, boolean MOUNTING) {
		execute(null, entity, sourceentity, MOUNTING);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity, boolean MOUNTING) {
		if (entity == null || sourceentity == null)
			return;
		if (MOUNTING && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:tamedgiantess"))) && sourceentity instanceof Player) {
			if (entity instanceof TamedTokiEntity && entity instanceof TamedTokiEntity _datEntL3 && _datEntL3.getEntityData().get(TamedTokiEntity.DATA_Sleep)
					|| entity instanceof TamedCandyEntity && entity instanceof TamedCandyEntity _datEntL5 && _datEntL5.getEntityData().get(TamedCandyEntity.DATA_Sleep)) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			} else if ((entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity6.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) < 3 && !sourceentity.isShiftKeyDown()) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("RidingRejectNotification").getString())), false);
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
		}
	}
}
