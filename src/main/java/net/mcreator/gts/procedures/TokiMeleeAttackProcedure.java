package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class TokiMeleeAttackProcedure {
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
		if (sourceentity instanceof TokiEntity) {
			if (immediatesourceentity instanceof GiantessSpitEntity) {
				if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }
			} else if (!sourceentity.getPersistentData().getBoolean("AttackCheck")) {
				if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }
				sourceentity.getPersistentData().putDouble("RangeAttack", 0);
				sourceentity.getPersistentData().putDouble("BreachAttack", 0);
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
				TargetHeight = entity.getY() - sourceentity.getY();
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.5
						&& (sourceentity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity10.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& !GtsModVariables.MapVariables.get(world).DisableVore
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 4
								* (sourceentity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity12.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)
						&& Mth.nextInt(RandomSource.create(), 0, (int) Math.round(((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) * 10)) == 0
						&& !(sourceentity instanceof LivingEntity _livEnt16 && _livEnt16.hasEffect(GtsModMobEffects.GTS_TIMER)) && !entity.getPersistentData().getBoolean("BeingGrabbed") && true) {
					if ((sourceentity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity18.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.LowerVoreAttack");
					} else if ((sourceentity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity20.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity21.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.MiddleVoreAttack");
					} else if ((sourceentity instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity23.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.UpperVoreAttack");
					}
					TokiVoreAttackProcedure.execute(world, x, y, z, entity, sourceentity);
				} else if (entity.isShiftKeyDown() && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > 0.5
						&& !(world instanceof ServerLevel _level28 && _level28.isRaided(BlockPos.containing(x, y, z))) && !GtsModVariables.MapVariables.get(world).DisableSubmission
						&& (sourceentity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity29.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& !(sourceentity instanceof LivingEntity _livEnt30 && _livEnt30.hasEffect(GtsModMobEffects.GTS_TIMER)) && !entity.getPersistentData().getBoolean("BeingGrabbed")
						&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("minecraft:flowers")))
								|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("minecraft:flowers"))))
						&& sourceentity.getPersistentData().getBoolean("CanBeFriend") && Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
					if ((sourceentity instanceof LivingEntity _livingEntity38 && _livingEntity38.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity38.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.LowerKiss");
						TokiKissProcedure.execute(world, x, y, z, entity, sourceentity, 85);
					} else if ((sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity41 && _livingEntity41.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity41.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.MiddleKiss");
						TokiKissProcedure.execute(world, x, y, z, entity, sourceentity, 75);
					} else if ((sourceentity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity43.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.UpperKiss");
						TokiKissProcedure.execute(world, x, y, z, entity, sourceentity, 85);
					}
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity46 && _livingEntity46.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity46.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableStep) {
							TokiSteppingProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice == 3 && !(entity instanceof IronGolem) && !GtsModVariables.MapVariables.get(world).DisableSit) {
							TokiSittingProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableKick) {
							TokiKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableStep) {
							TokiSteppingProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableKick) {
							TokiKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableSit) {
							TokiSittingProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity48 && _livingEntity48.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity48.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity49.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (MeleeAttackChoice <= 2 && !GtsModVariables.MapVariables.get(world).DisableGunSlap) {
							TokiGunMeleeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (MeleeAttackChoice > 2 && MeleeAttackChoice <= 4
								&& (sourceentity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity50.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt52 && _livEnt52.hasEffect(GtsModMobEffects.GTS_TIMER))
								&& !GtsModVariables.MapVariables.get(world).DisableThrow) {
							TokiThrowAttackProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TokiGunSmashProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableGunSlap) {
							TokiGunMeleeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TokiGunSmashProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if ((sourceentity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity53.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt55 && _livEnt55.hasEffect(GtsModMobEffects.GTS_TIMER))
								&& !GtsModVariables.MapVariables.get(world).DisableThrow) {
							TokiThrowAttackProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity56 && _livingEntity56.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity56.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TokiGunSmashProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					}
				}
			}
		}
	}
}
