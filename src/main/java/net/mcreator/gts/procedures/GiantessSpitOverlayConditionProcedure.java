package net.mcreator.gts.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.init.GtsModMobEffects;

public class GiantessSpitOverlayConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(GtsModMobEffects.GIANTESS_SPIT_OVERLAY_EFFECT.get());
	}
}
