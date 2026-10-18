package net.mcreator.gts.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.gts.init.GtsModMobEffects;

public class TokiDroneBatteryCountDownProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.TOKI_DRONE_BATTERY, 1200, 1, false, false));
	}
}
