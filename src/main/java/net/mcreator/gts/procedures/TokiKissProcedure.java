package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.GtsMod;

public class TokiKissProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double WaitTime) {
		if (entity == null || sourceentity == null)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		sourceentity.getPersistentData().putDouble("TargetStruggleCount", 0);
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER, (int) WaitTime, 1, false, false));
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
				entity.setShiftKeyDown(false);
				entity.startRiding(sourceentity);
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("minecraft:flowers")))) {
					if (entity instanceof Player _player) {
						ItemStack _stktoremove = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
						_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
					}
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("minecraft:flowers")))) {
					if (entity instanceof Player _player) {
						ItemStack _stktoremove = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY);
						_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
					}
				}
			}
		});
		GtsMod.queueServerWork((int) (WaitTime - 40), () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
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
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.kiss")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity33.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.kiss")), SoundSource.HOSTILE,
								(float) (sourceentity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity33.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.HEART, (sourceentity.getX()),
							(sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity37 && _livingEntity37.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity37.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2),
							(sourceentity.getZ()),
							(int) ((sourceentity instanceof LivingEntity _livingEntity42 && _livingEntity42.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity42.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 10),
							((sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2),
							((sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2),
							((sourceentity instanceof LivingEntity _livingEntity41 && _livingEntity41.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity41.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2), 0);
			}
		});
		GtsMod.queueServerWork((int) (WaitTime - 10), () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				sourceentity.getPersistentData().putBoolean("ThrowingTarget", true);
				entity.stopRiding();
				{
					Entity _ent = entity;
					_ent.teleportTo((entity.getX()), (sourceentity.getY()), (entity.getZ()));
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport((entity.getX()), (sourceentity.getY()), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
				}
			}
		});
		GtsMod.queueServerWork((int) WaitTime, () -> {
			if (sourceentity.isAlive() && sourceentity.getPersistentData()
					.getDouble("TargetStruggleCount") < (sourceentity instanceof LivingEntity _livingEntity57 && _livingEntity57.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity57.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)) {
				entity.getPersistentData().putBoolean("BeingGrabbed", false);
				TamedTokiSpawnProcedure.execute(world, sourceentity.getX(), sourceentity.getY(), sourceentity.getZ(), entity, sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1,
						sourceentity instanceof LivingEntity _livingEntity62 && _livingEntity62.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity62.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0,
						sourceentity instanceof LivingEntity _livingEntity68 && _livingEntity68.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity68.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() : 0,
						sourceentity.getXRot(),
						0,
						sourceentity instanceof LivingEntity _livingEntity63 && _livingEntity63.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity63.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0, sourceentity.getYRot());
				if (!sourceentity.level().isClientSide())
					sourceentity.discard();
			}
		});
	}
}
