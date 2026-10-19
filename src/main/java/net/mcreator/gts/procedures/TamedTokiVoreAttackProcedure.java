package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.TokiVariantEntity;
import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.GtsMod;

public class TamedTokiVoreAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		boolean wildTokiTarget = entity instanceof TokiEntity
            || (entity instanceof TokiVariantEntity variant && !variant.isTame());
        boolean hostileMobTarget = entity instanceof Monster
            && !(entity instanceof net.minecraft.world.entity.TamableAnimal tameTarget && tameTarget.isTame())
            && !(entity instanceof TokiEntity) && !(entity instanceof TokiVariantEntity)
            && !(entity instanceof CandyEntity);
		if (!hostileMobTarget && !wildTokiTarget)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER.get(), 145, 1, false, false));
		GtsMod.queueServerWork(15, () -> {
			if (sourceentity.isAlive() && !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
				entity.startRiding(sourceentity);
				{
					Entity _ent = sourceentity;
					_ent.setYRot(sourceentity.getYRot());
					_ent.setXRot(0);
					_ent.setYBodyRot(_ent.getYRot());
					_ent.setYHeadRot(_ent.getYRot());
					_ent.yRotO = _ent.getYRot();
					_ent.xRotO = _ent.getXRot();
					if (_ent instanceof LivingEntity _entity) {
						_entity.yBodyRotO = _entity.getYRot();
						_entity.yHeadRotO = _entity.getYRot();
					}
				}
			}
		});
		GtsMod.queueServerWork(65, () -> {
			if ((entity.getVehicle()) == sourceentity && sourceentity.isAlive()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.lick")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity14.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.lick")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity14.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_SHRINK_EFFECT.get(), 30, 0));
			}
		});
		GtsMod.queueServerWork(90, () -> {
			if ((entity.getVehicle()) == sourceentity && sourceentity.isAlive()) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_SHRINK_EFFECT.get(), 70, 1));
			}
		});
		GtsMod.queueServerWork(145, () -> {
			if ((entity.getVehicle()) == sourceentity && sourceentity.isAlive() && entity.isAlive()) {
				if (!entity.level().isClientSide())
					entity.discard();
				entity.getPersistentData().putBoolean("BeingGrabbed", false);
				// Wild Toki may be eaten, but only hostile non-Toki mobs restore health.
				if (!wildTokiTarget && sourceentity instanceof LivingEntity _entity) {
					float maxHealth = _entity.getMaxHealth();
					_entity.setHealth(Math.min(maxHealth, _entity.getHealth() + maxHealth / 10.0F));
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki_swallow")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity32.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki_swallow")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity32.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof TamedTokiEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
					TamedTokiBelchProcedure.execute(world, x, y, z, sourceentity);
				}
			} else if (sourceentity.isAlive()) {
				entity.getPersistentData().putBoolean("BeingGrabbed", false);
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				if (sourceentity instanceof TamedTokiEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}
