package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.CandyEntity;

public class PlayerStruggleKeyOnKeyReleasedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide() && !((entity.getVehicle()) == null) && (entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:untamedgiantess"))) && entity.isAlive()
				&& (entity.getVehicle()).isAlive()) {
			if ((entity.getVehicle()).getPersistentData().getDouble(
					"TargetStruggleCount") >= ((entity.getVehicle()) instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity11.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)) {
				if ((entity.getVehicle()) instanceof TokiEntity) {
					PlayerStruggleTokiActionProcedure.execute(world, entity.getVehicle(), entity);
				} else if ((entity.getVehicle()) instanceof CandyEntity) {
					PlayerStruggleCandyActionProcedure.execute(world, entity.getVehicle(), entity);
				}
			} else {
				(entity.getVehicle()).getPersistentData().putDouble("TargetStruggleCount", ((entity.getVehicle()).getPersistentData().getDouble("TargetStruggleCount") + 1));
			}
		} else if (!world.isClientSide() && !((entity.getVehicle()) == null) && (entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:tamedgiantess"))) && (entity.getVehicle()).onGround()) {
			(entity.getVehicle()).setDeltaMovement(
					new Vec3(0, (1 + ((entity.getVehicle()) instanceof LivingEntity _livingEntity30 && _livingEntity30.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity30.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 10), 0));
		}
	}
}
