package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
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

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.GtsMod;

import java.util.List;
import java.util.Comparator;

public class TamedCandySmashProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof TamedCandyEntity _datEntSetS)
			_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.smash");
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
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
		GtsMod.queueServerWork(15, () -> {
			if (sourceentity.isAlive()) {
				{
					final Vec3 _center = new Vec3(
							(sourceentity.getX() + sourceentity.getLookAngle().x
									* (sourceentity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity13.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
							(sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.5),
							(sourceentity.getZ() + sourceentity.getLookAngle().z
									* (sourceentity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity18.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					List<Entity> _entfound = world
							.getEntitiesOfClass(Entity.class,
									new AABB(_center, _center).inflate(
											(sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity19.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2d),
									e -> true)
							.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (GtsModVariables.MapVariables.get(world).DisableTamedGiantessFriendlyFire) {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))
									&& !(entityiterator == (sourceentity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null))) {
								entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
										(float) (3.5 * (sourceentity instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity23.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
							}
						} else {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
								entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
										(float) (3.5 * (sourceentity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
							}
						}
					}
				}
				{
					final Vec3 _center = new Vec3(
							(sourceentity.getX() + sourceentity.getLookAngle().x
									* (sourceentity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity33.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
							(sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity35.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2.5),
							(sourceentity.getZ() + sourceentity.getLookAngle().z
									* (sourceentity instanceof LivingEntity _livingEntity38 && _livingEntity38.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity38.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					List<Entity> _entfound = world
							.getEntitiesOfClass(Entity.class,
									new AABB(_center, _center).inflate(
											(sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2d),
									e -> true)
							.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (GtsModVariables.MapVariables.get(world).DisableTamedGiantessFriendlyFire) {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))
									&& !(entityiterator == (sourceentity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null))) {
								entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
										(float) (3.5 * (sourceentity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity43.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
							}
						} else {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
								entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
										(float) (3.5 * (sourceentity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity47.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
							}
						}
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("item.mace.smash_ground_heavy")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity51 && _livingEntity51.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity51.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("item.mace.smash_ground_heavy")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity51 && _livingEntity51.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity51.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
			}
		});
		GtsMod.queueServerWork(30, () -> {
			if (sourceentity.isAlive()) {
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (sourceentity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}
