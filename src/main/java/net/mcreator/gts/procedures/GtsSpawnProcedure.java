package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.CandyEntity;

public class GtsSpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double WaveCount = 0;
		double TokiSpawnScale = 0;
		if (!world.isClientSide()) {
			if (world instanceof ServerLevel _level1 && _level1.isRaided(BlockPos.containing(x, y, z)) && entity == entity) {
				if (entity instanceof TokiEntity) {
					Entity GtsEntity = (TokiEntity) entity;
					ServerLevel level = (ServerLevel) GtsEntity.level();
					Raid nearbyRaid = level.getRaidAt(GtsEntity.blockPosition());
					if (nearbyRaid != null) {
						WaveCount = nearbyRaid.getGroupsSpawned() + 1;
						TokiSpawnScale = Math.floor(0.5 * WaveCount + 1.5);
					}
				} else if (entity instanceof CandyEntity) {
					Entity GtsEntity = (CandyEntity) entity;
					ServerLevel level = (ServerLevel) GtsEntity.level();
					Raid nearbyRaid = level.getRaidAt(GtsEntity.blockPosition());
					if (nearbyRaid != null) {
						WaveCount = nearbyRaid.getGroupsSpawned() + 1;
						TokiSpawnScale = Math.floor(0.5 * WaveCount + 1.5);
					}
				}
			}
			if (TokiSpawnScale >= 3 && TokiSpawnScale <= 5 && !GtsModVariables.MapVariables.get(world).DisableRaidWaveSizeChange) {
				if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()))
					_livingEntity5.getAttribute(GtsModAttributes.SCALE.get()).setBaseValue(TokiSpawnScale);
				if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
					_livingEntity6.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((0.25 + 0.05 * TokiSpawnScale));
				if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
					_livingEntity9.getAttribute(Attributes.MAX_HEALTH)
							.setBaseValue(((entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity8.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity10.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
			} else {
				if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()))
					_livingEntity12.getAttribute(GtsModAttributes.SCALE.get()).setBaseValue(GtsModVariables.MapVariables.get(world).GtsNaturalSpawnHeight);
				if (entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
					_livingEntity14.getAttribute(Attributes.MAX_HEALTH)
							.setBaseValue(((entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity13.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
				if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
					_livingEntity18.getAttribute(Attributes.MOVEMENT_SPEED)
							.setBaseValue((0.25 + 0.05 * (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity17.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity19.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
			}
			entity.getPersistentData().putBoolean("CanBeFriend", true);
		}
	}
}
