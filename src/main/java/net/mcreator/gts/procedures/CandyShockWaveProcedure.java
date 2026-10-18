package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.GtsMod;

import java.util.List;
import java.util.Comparator;

public class CandyShockWaveProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof CandyEntity _datEntSetS)
			_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.shockwave");
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
		if (sourceentity.getXRot() < -35) {
			{
				Entity _ent = sourceentity;
				_ent.setYRot(sourceentity.getYRot());
				_ent.setXRot(-35);
				_ent.setYBodyRot(_ent.getYRot());
				_ent.setYHeadRot(_ent.getYRot());
				_ent.yRotO = _ent.getYRot();
				_ent.xRotO = _ent.getXRot();
				if (_ent instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
		} else if (sourceentity.getXRot() > 35) {
			{
				Entity _ent = sourceentity;
				_ent.setYRot(sourceentity.getYRot());
				_ent.setXRot(35);
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
		GtsMod.queueServerWork(20, () -> {
			if (sourceentity.isAlive()) {
				GtsWaveEffectProcedure
						.execute(world,
								sourceentity.getX() + sourceentity.getLookAngle().x
										* (sourceentity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity17.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.5,
								sourceentity.getY(),
								sourceentity.getZ() + sourceentity.getLookAngle().z
										* (sourceentity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity21.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.5,
								sourceentity);
				{
					final Vec3 _center = new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()));
					List<Entity> _entfound = world
							.getEntitiesOfClass(Entity.class,
									new AABB(_center, _center).inflate(
											(sourceentity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity25.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2d),
									e -> true)
							.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
									(float) (4.5 * (sourceentity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
						}
					}
				}
				{
					final Vec3 _center = new Vec3(
							(sourceentity.getX() + sourceentity.getLookAngle().x
									* (sourceentity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity33.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.5),
							(sourceentity.getY()), (sourceentity.getZ() + sourceentity.getLookAngle().z
									* (sourceentity instanceof LivingEntity _livingEntity37 && _livingEntity37.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity37.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.5));
					List<Entity> _entfound = world
							.getEntitiesOfClass(Entity.class,
									new AABB(_center, _center).inflate(
											(sourceentity instanceof LivingEntity _livingEntity38 && _livingEntity38.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity38.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2d),
									e -> true)
							.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
									(float) (4.5 * (sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
						}
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL,
								(float) (sourceentity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity47.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL,
								(float) (sourceentity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity47.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
			}
		});
		GtsMod.queueServerWork(40, () -> {
			if (sourceentity.isAlive()) {
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (sourceentity instanceof CandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}
