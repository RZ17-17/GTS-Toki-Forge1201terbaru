package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraftforge.event.entity.living.LivingIncomingDamageEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.ICancellableEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.HeartBulletEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;
import net.mcreator.gts.entity.CandyEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class CandyMeleeAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, immediatesourceentity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		double MeleeAttackChoice = 0;
		double VoreChance = 0;
		double TargetHeight = 0;
		if (sourceentity instanceof CandyEntity) {
			if (immediatesourceentity instanceof GiantessSpitEntity || immediatesourceentity instanceof HeartBulletEntity) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			} else if (!sourceentity.getPersistentData().getBoolean("AttackCheck")) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				sourceentity.getPersistentData().putDouble("RangeAttack", 0);
				sourceentity.getPersistentData().putDouble("BreachAttack", 0);
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
				TargetHeight = entity.getY() - sourceentity.getY();
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.5
						&& (sourceentity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity11.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& !GtsModVariables.MapVariables.get(world).DisableCandyVore
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 4
								* (sourceentity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity13.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)
						&& Mth.nextInt(RandomSource.create(), 0, (int) Math.round(((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) * 10)) == 0
						&& !(sourceentity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(GtsModMobEffects.GTS_TIMER)) && !entity.getPersistentData().getBoolean("BeingGrabbed") && true) {
					if ((sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity19.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.lowerVore");
					} else if ((sourceentity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity21.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity22.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.middleVore");
					} else if ((sourceentity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity24.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.upperVore");
					}
					CandyVoreAttackProcedure.execute(world, x, y, z, entity, sourceentity);
				} else if (Mth.nextInt(RandomSource.create(), 1, 3) == 1 && !(world instanceof ServerLevel _level27 && _level27.isRaided(BlockPos.containing(x, y, z))) && !GtsModVariables.MapVariables.get(world).DisableSubmission
						&& (sourceentity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity28.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > 0.5 && !entity.getPersistentData().getBoolean("BeingGrabbed")
						&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.CAKE
								|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Items.CAKE)
						&& sourceentity.getPersistentData().getBoolean("CanBeFriend")) {
					if ((sourceentity instanceof LivingEntity _livingEntity37 && _livingEntity37.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity37.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.lowerTame");
					} else if ((sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.middleTame");
					} else if ((sourceentity instanceof LivingEntity _livingEntity42 && _livingEntity42.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity42.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.upperTame");
					}
					CandyTamingProcedure.execute(world, x, y, z, entity, sourceentity);
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity45 && _livingEntity45.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity45.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableCandyStep) {
							CandySteppingProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice == 3 && !(entity instanceof IronGolem) && !GtsModVariables.MapVariables.get(world).DisableCandyShockWave) {
							CandyShockWaveProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableCandyKick) {
							CandyKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyStep) {
							CandySteppingProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyKick) {
							CandyKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyShockWave) {
							CandyShockWaveProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity47.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity48 && _livingEntity48.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity48.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (MeleeAttackChoice <= 3 && !GtsModVariables.MapVariables.get(world).DisableCandySwipe) {
							CandySwipeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (MeleeAttackChoice > 3 && !GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							CandySmashProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandySwipe) {
							CandySwipeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							CandySmashProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity49.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							CandySmashProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					}
				}
			}
		}
	}
}
