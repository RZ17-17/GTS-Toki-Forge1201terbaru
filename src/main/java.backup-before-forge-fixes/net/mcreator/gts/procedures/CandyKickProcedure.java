package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.GtsMod;

import java.util.List;
import java.util.Comparator;

public class CandyKickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof CandyEntity _datEntSetS)
			_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.kick");
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
		GtsMod.queueServerWork(15, () -> {
			if (sourceentity.isAlive()) {
				{
					final Vec3 _center = new Vec3(
							(sourceentity.getX() + sourceentity.getLookAngle().x
									* (sourceentity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity17.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
							(sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity19.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 0.4),
							(sourceentity.getZ() + sourceentity.getLookAngle().z
									* (sourceentity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity22.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class,
							new AABB(_center, _center).inflate(
									((sourceentity instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity23.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.5) / 2d),
							e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
							if (entity instanceof LivingEntity _livEnt25 && _livEnt25.isBlocking()) {
								if (entityiterator instanceof LivingEntity _entity) {
									DamageSource _dmgsource = new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK));
									_entity.hurt(new DamageSource(_dmgsource.typeHolder(), _dmgsource.getEntity(), _dmgsource.getDirectEntity()) {
										@Override
										public Component getLocalizedDeathMessage(LivingEntity _msgEntity) {
											String _translatekey = (entity.getDisplayName().getString() + " tried and failed to block Candy's kick, and died from embarrassment");
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
									}, (float) (2 * (sourceentity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity26.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
								}
								entityiterator.setDeltaMovement(new Vec3(
										(sourceentity.getLookAngle().x
												* (sourceentity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity31.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
										0.5, (sourceentity.getLookAngle().z
												* (sourceentity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity33.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
							} else {
								entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
										(float) (3 * (sourceentity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity35.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
								entityiterator.setDeltaMovement(new Vec3(
										(sourceentity.getLookAngle().x
												* (sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
										0.5, (sourceentity.getLookAngle().z
												* (sourceentity instanceof LivingEntity _livingEntity41 && _livingEntity41.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity41.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
							}
						}
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.goat.ram_impact")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity44 && _livingEntity44.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity44.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.goat.ram_impact")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity44 && _livingEntity44.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity44.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
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
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (sourceentity instanceof CandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}
