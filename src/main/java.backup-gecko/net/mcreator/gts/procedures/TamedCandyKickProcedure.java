package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.GtsMod;

import java.util.List;
import java.util.Comparator;

public class TamedCandyKickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof TamedCandyEntity _datEntSetS)
			_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.kick");
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
							(sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 0.4),
							(sourceentity.getZ() + sourceentity.getLookAngle().z
									* (sourceentity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity18.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class,
							new AABB(_center, _center).inflate(
									((sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity19.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.5) / 2d),
							e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (GtsModVariables.MapVariables.get(world).DisableTamedGiantessFriendlyFire) {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))
									&& !(entityiterator == (sourceentity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null))) {
								if (entityiterator instanceof LivingEntity _livEnt23 && _livEnt23.isBlocking()) {
									if (entityiterator instanceof LivingEntity _entity) {
										DamageSource _dmgsource = new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK));
										_entity.hurt(new DamageSource(_dmgsource.typeHolder(), _dmgsource.getEntity(), _dmgsource.getDirectEntity()) {
											@Override
											public Component getLocalizedDeathMessage(LivingEntity _msgEntity) {
												String _translatekey = (entityiterator.getDisplayName().getString() + " tried and failed to block Candy's kick, and died from embarrassment");
												if (this.getEntity() == null && this.getDirectEntity() == null) {
													return _msgEntity.getKillCredit() != null
															? Component.translatable(_translatekey + ".player", _msgEntity.getDisplayName(), _msgEntity.getKillCredit().getDisplayName())
															: Component.translatable(_translatekey, _msgEntity.getDisplayName());
												} else {
													Component _component = this.getEntity() == null ? this.getDirectEntity().getDisplayName() : this.getEntity().getDisplayName();
													ItemStack _itemstack = ItemStack.EMPTY;
													if (this.getEntity() instanceof LivingEntity _livingentity)
														_itemstack = _livingentity.getMainHandItem();
													return !_itemstack.isEmpty() && _itemstack.hasCustomHoverName()
															? Component.translatable(_translatekey + ".item", _msgEntity.getDisplayName(), _component, _itemstack.getDisplayName())
															: Component.translatable(_translatekey, _msgEntity.getDisplayName(), _component);
												}
											}
										}, (float) (2 * (sourceentity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity24.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
									}
									entityiterator.setDeltaMovement(new Vec3(
											(sourceentity.getLookAngle().x
													* (sourceentity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity29.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
											0.5, (sourceentity.getLookAngle().z
													* (sourceentity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity31.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
								} else {
									entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
											(float) (3 * (sourceentity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity33.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
									entityiterator.setDeltaMovement(new Vec3(
											(sourceentity.getLookAngle().x
													* (sourceentity instanceof LivingEntity _livingEntity37 && _livingEntity37.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity37.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
											0.5, (sourceentity.getLookAngle().z
													* (sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
								}
							}
						} else {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
								if (entityiterator instanceof LivingEntity _livEnt42 && _livEnt42.isBlocking()) {
									if (entityiterator instanceof LivingEntity _entity) {
										DamageSource _dmgsource = new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK));
										_entity.hurt(new DamageSource(_dmgsource.typeHolder(), _dmgsource.getEntity(), _dmgsource.getDirectEntity()) {
											@Override
											public Component getLocalizedDeathMessage(LivingEntity _msgEntity) {
												String _translatekey = (entityiterator.getDisplayName().getString() + " tried and failed to block Candy's kick, and died from embarrassment");
												if (this.getEntity() == null && this.getDirectEntity() == null) {
													return _msgEntity.getKillCredit() != null
															? Component.translatable(_translatekey + ".player", _msgEntity.getDisplayName(), _msgEntity.getKillCredit().getDisplayName())
															: Component.translatable(_translatekey, _msgEntity.getDisplayName());
												} else {
													Component _component = this.getEntity() == null ? this.getDirectEntity().getDisplayName() : this.getEntity().getDisplayName();
													ItemStack _itemstack = ItemStack.EMPTY;
													if (this.getEntity() instanceof LivingEntity _livingentity)
														_itemstack = _livingentity.getMainHandItem();
													return !_itemstack.isEmpty() && _itemstack.hasCustomHoverName()
															? Component.translatable(_translatekey + ".item", _msgEntity.getDisplayName(), _component, _itemstack.getDisplayName())
															: Component.translatable(_translatekey, _msgEntity.getDisplayName(), _component);
												}
											}
										}, (float) (2 * (sourceentity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity43.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
									}
									entityiterator.setDeltaMovement(new Vec3(
											(sourceentity.getLookAngle().x
													* (sourceentity instanceof LivingEntity _livingEntity48 && _livingEntity48.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity48.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
											0.5, (sourceentity.getLookAngle().z
													* (sourceentity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity50.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
								} else {
									entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
											(float) (3 * (sourceentity instanceof LivingEntity _livingEntity52 && _livingEntity52.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity52.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
									entityiterator.setDeltaMovement(new Vec3(
											(sourceentity.getLookAngle().x
													* (sourceentity instanceof LivingEntity _livingEntity56 && _livingEntity56.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity56.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
											0.5, (sourceentity.getLookAngle().z
													* (sourceentity instanceof LivingEntity _livingEntity58 && _livingEntity58.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity58.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
								}
							}
						}
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.goat.ram_impact")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity61 && _livingEntity61.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity61.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.goat.ram_impact")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity61 && _livingEntity61.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity61.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
			}
		});
		GtsMod.queueServerWork(25, () -> {
			if (sourceentity.isAlive()) {
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				if (sourceentity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
			}
		});
	}
}
