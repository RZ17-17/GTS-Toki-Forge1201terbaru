package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TamedCandyEntity;

public class TamedGtsPlayerBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		// Do not run terrain-damaging player-controlled breach logic on the authoritative server.
		if (!world.isClientSide()) return;
		double MeleeChoice = 0;
		Entity TargetedTamedGTS = null;
		if (!world.isClientSide() && !((entity.getVehicle()) == null) && (entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:tamedgiantess"))) && (entity.getVehicle()).onGround()) {
			if ((entity.getVehicle()) instanceof TamedCandyEntity && !(entity.getVehicle()).getPersistentData().getBoolean("AttackCheck")) {
				TamedCandyBreachProcedure.execute(world, entity.getVehicle());
			} else if ((entity.getVehicle()) instanceof TamedTokiEntity && !(entity.getVehicle()).getPersistentData().getBoolean("AttackCheck")) {
				TamedTokiTrappedBreachProcedure.execute(world, entity.getVehicle());
			}
		}
	}
}
