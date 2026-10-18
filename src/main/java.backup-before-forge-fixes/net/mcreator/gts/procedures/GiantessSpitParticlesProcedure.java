package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class GiantessSpitParticlesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SPIT, x, y, z,
						(int) (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity4.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0),
						((entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity1.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 3),
						((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity2.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 3),
						((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity3.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 3), 0);
		}
	}
}
