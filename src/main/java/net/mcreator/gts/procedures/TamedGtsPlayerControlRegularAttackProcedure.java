package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TamedCandyEntity;

public class TamedGtsPlayerControlRegularAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double MeleeChoice = 0;
		Entity TargetedTamedGTS = null;
		if (!world.isClientSide() && !((entity.getVehicle()) == null) && (entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:tamedgiantess"))) && (entity.getVehicle()).onGround()) {
			if ((entity.getVehicle()) instanceof TamedCandyEntity && !(entity.getVehicle()).getPersistentData().getBoolean("AttackCheck")) {
				TargetedTamedGTS = entity.getVehicle();
				MeleeChoice = Mth.nextInt(RandomSource.create(), 1, 3);
				if (MeleeChoice == 1 && !GtsModVariables.MapVariables.get(world).DisableStep) {
					TamedCandySteppingProcedure.execute(world, TargetedTamedGTS);
				} else if (MeleeChoice == 2 && !GtsModVariables.MapVariables.get(world).DisableKick) {
					TamedCandyKickProcedure.execute(world, x, y, z, TargetedTamedGTS);
				} else if (MeleeChoice == 3 && !GtsModVariables.MapVariables.get(world).DisableSit) {
					TamedCandyShockWaveProcedure.execute(world, TargetedTamedGTS);
				} else if (!GtsModVariables.MapVariables.get(world).DisableStep) {
					TamedCandySteppingProcedure.execute(world, TargetedTamedGTS);
				} else if (!GtsModVariables.MapVariables.get(world).DisableKick) {
					TamedCandyKickProcedure.execute(world, x, y, z, TargetedTamedGTS);
				} else if (!GtsModVariables.MapVariables.get(world).DisableSit) {
					TamedCandyShockWaveProcedure.execute(world, TargetedTamedGTS);
				}
			} else if ((entity.getVehicle()) instanceof TamedTokiEntity && !(entity.getVehicle()).getPersistentData().getBoolean("AttackCheck")) {
				TargetedTamedGTS = entity.getVehicle();
				MeleeChoice = Mth.nextInt(RandomSource.create(), 1, 3);
				if (MeleeChoice == 1 && !GtsModVariables.MapVariables.get(world).DisableStep) {
					TamedTokiSteppingProcedure.execute(world, TargetedTamedGTS);
				} else if (MeleeChoice == 2 && !GtsModVariables.MapVariables.get(world).DisableKick) {
					TamedTokiKickProcedure.execute(world, TargetedTamedGTS);
				} else if (MeleeChoice == 3 && !GtsModVariables.MapVariables.get(world).DisableSit) {
					TamedTokiSittingProcedure.execute(world, TargetedTamedGTS);
				} else if (!GtsModVariables.MapVariables.get(world).DisableStep) {
					TamedTokiSteppingProcedure.execute(world, TargetedTamedGTS);
				} else if (!GtsModVariables.MapVariables.get(world).DisableKick) {
					TamedTokiKickProcedure.execute(world, TargetedTamedGTS);
				} else if (!GtsModVariables.MapVariables.get(world).DisableSit) {
					TamedTokiSittingProcedure.execute(world, TargetedTamedGTS);
				}
			}
		}
	}
}
