package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class TamedTokiMeleeAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
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
		if (sourceentity instanceof TamedTokiEntity) {
			if (immediatesourceentity instanceof GiantessSpitEntity) {
                // Cancel only a projectile that collides with its own shooter; enemy hits must deal damage.
                if (entity == sourceentity && event != null && event.isCancelable()) event.setCanceled(true);
            } else if (!sourceentity.getPersistentData().getBoolean("AttackCheck")) {
				if (event != null && event.isCancelable()) {
					event.setCanceled(true);
				}
				sourceentity.getPersistentData().putDouble("RangeAttack", 0);
				sourceentity.getPersistentData().putDouble("BreachAttack", 0);
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
				TargetHeight = entity.getY() - sourceentity.getY();
				sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
				if (!(sourceentity instanceof LivingEntity _livEnt12 && _livEnt12.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed") && !GtsModVariables.MapVariables.get(world).DisableVore
						&& (sourceentity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity14.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > 2 && !(entity instanceof Player)
						&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.8
						&& ((entity instanceof net.minecraft.world.entity.monster.Monster
							&& !(entity instanceof net.minecraft.world.entity.TamableAnimal tameTarget && tameTarget.isTame())
							&& !(entity instanceof net.mcreator.gts.entity.TokiEntity)
							&& !(entity instanceof net.mcreator.gts.entity.TokiVariantEntity)
							&& !(entity instanceof net.mcreator.gts.entity.CandyEntity))
							|| entity instanceof net.mcreator.gts.entity.TokiEntity
							|| (entity instanceof net.mcreator.gts.entity.TokiVariantEntity variantTarget && !variantTarget.isTame()))) {
					if ((sourceentity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity24.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof TamedTokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.LowerVoreAttack");
					} else if ((sourceentity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity26.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof TamedTokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.MiddleVoreAttack");
					} else if ((sourceentity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity29.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof TamedTokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.UpperVoreAttack");
					}
					TamedTokiVoreAttackProcedure.execute(world, x, y, z, entity, sourceentity);
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity32.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableStep) {
							TamedTokiSteppingProcedure.execute(world, sourceentity);
						} else if (MeleeAttackChoice == 3 && !(entity instanceof IronGolem) && !GtsModVariables.MapVariables.get(world).DisableSit) {
							TamedTokiSittingProcedure.execute(world, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableKick) {
							TamedTokiKickProcedure.execute(world, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableStep) {
							TamedTokiSteppingProcedure.execute(world, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableKick) {
							TamedTokiKickProcedure.execute(world, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableSit) {
							TamedTokiSittingProcedure.execute(world, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity34.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity35.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (MeleeAttackChoice <= 2 && !GtsModVariables.MapVariables.get(world).DisableGunSlap) {
							TamedTokiGunMeleeProcedure.execute(world, x, y, z, sourceentity);
						} else if (MeleeAttackChoice > 2 && MeleeAttackChoice <= 4
								&& (sourceentity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity36.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt38 && _livEnt38.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& !GtsModVariables.MapVariables.get(world).DisableThrow) {
							TamedTokiThrowProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TamedTokiGunSmashProcedure.execute(world, x, y, z, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableGunSlap) {
							TamedTokiGunMeleeProcedure.execute(world, x, y, z, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TamedTokiGunSmashProcedure.execute(world, x, y, z, sourceentity);
						} else if ((sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt41 && _livEnt41.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& !GtsModVariables.MapVariables.get(world).DisableThrow) {
							TamedTokiThrowProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity42 && _livingEntity42.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity42.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TamedTokiGunSmashProcedure.execute(world, x, y, z, sourceentity);
						}
					}
				}
			}
		}
	}
}
