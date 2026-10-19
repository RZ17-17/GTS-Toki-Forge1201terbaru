package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TokiEntity;

import java.util.List;
import java.util.Comparator;

public class GiantessSpitHitsEntityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity, Entity sourceentity) {
		if (immediatesourceentity == null || sourceentity == null)
			return;
		if (!world.isClientSide()) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SPIT, x, y, z,
						(int) (33 * (sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity3.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
						(sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity1.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1,
						(sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity2.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 0);
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class,
						new AABB(_center, _center).inflate(
								(1 + 2 * (sourceentity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity5.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)) / 2d),
						e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator != sourceentity && entityiterator != immediatesourceentity.getOwner() && !(entityiterator instanceof TokiEntity)) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
					}
					if (entityiterator instanceof Player) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GIANTESS_SPIT_OVERLAY_EFFECT.get(), 60, 1, false, false));
					}
				}
			}
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		}
	}
}
