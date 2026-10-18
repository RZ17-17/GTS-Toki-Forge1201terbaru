package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
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
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.GtsMod;

public class TamedCandyVoreAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER, 100, 1, false, false));
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
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_SHRINK_EFFECT, 90, 0));
			}
		});
		GtsMod.queueServerWork(50, () -> {
			if ((entity.getVehicle()) == sourceentity && sourceentity.isAlive()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.lick")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.lick")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_SHRINK_EFFECT, 55, 1));
			}
		});
		GtsMod.queueServerWork(100, () -> {
			if ((entity.getVehicle()) == sourceentity && sourceentity.isAlive() && entity.isAlive()) {
				if (!entity.level().isClientSide())
					entity.discard();
				entity.getPersistentData().putBoolean("BeingGrabbed", false);
				if (sourceentity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 10));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:candy.swallow")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity28.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:candy.swallow")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity28.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
					TamedCandyBelchProcedure.execute(world, x, y, z, sourceentity);
				}
			} else if (sourceentity.isAlive()) {
				entity.getPersistentData().putBoolean("BeingGrabbed", false);
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				if (sourceentity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}
