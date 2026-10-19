package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Comparator;

import com.ibm.icu.number.Scale;

public class TokiSniperAttackDamageProcedure {
	public static void execute(LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double X = 0;
		double Y = 0;
		double Z = 0;
		double Scale = 0;
		if (!world.isClientSide()) {
			Scale = sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity1.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0;
			X = sourceentity.getX() + sourceentity.getLookAngle().x * 2 * Scale;
			Z = sourceentity.getZ() + sourceentity.getLookAngle().z * 2 * Scale;
			Y = sourceentity.getY() + 2.3 * Scale + sourceentity.getLookAngle().y * 2 * Scale;
			for (int index0 = 0; index0 < 50; index0++) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(X, Y, Z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.warden.sonic_boom")), SoundSource.HOSTILE, (float) Scale, 1);
					} else {
						_level.playLocalSound(X, Y, Z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.warden.sonic_boom")), SoundSource.HOSTILE, (float) Scale, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.SONIC_BOOM, X, Y, Z, 1, 0, 0, 0, 0);
				{
					final Vec3 _center = new Vec3(X, Y, Z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_PROJECTILE), sourceentity), (float) (15 * Scale));
						}
					}
				}
				X = X + sourceentity.getLookAngle().x * 3;
				Z = Z + sourceentity.getLookAngle().z * 3;
				Y = Y + sourceentity.getLookAngle().y * 3;
			}
		}
	}
}
