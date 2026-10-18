package net.mcreator.gts.procedures;

import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.GtsMod;

import javax.annotation.Nullable;

@EventBusSubscriber
public class TamedGTSDismountEventProcedure {
	@SubscribeEvent
	public static void onEventTriggered(EntityMountEvent event) {
		execute(event, event.getLevel(), event.getEntityBeingMounted(), event.getEntityMounting(), event.isDismounting());
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, boolean DISMOUNTING) {
		execute(null, world, entity, sourceentity, DISMOUNTING);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity, boolean DISMOUNTING) {
		if (entity == null || sourceentity == null)
			return;
		if (DISMOUNTING && !world.isClientSide() && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:tamedgiantess"))) && sourceentity instanceof Player) {
			GtsMod.queueServerWork(5, () -> {
				{
					Entity _ent = sourceentity;
					_ent.teleportTo((sourceentity.getX()), (entity.getY() + 1), (sourceentity.getZ()));
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport((sourceentity.getX()), (entity.getY() + 1), (sourceentity.getZ()), _ent.getYRot(), _ent.getXRot());
				}
			});
		}
	}
}
