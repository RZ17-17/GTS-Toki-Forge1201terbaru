package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.commands.arguments.EntityAnchorArgument;



public class TokiRangeAndBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		// Never start a terrain-affecting breach during the kick or another active attack.
		if (sourceentity.getPersistentData().getBoolean("AttackCheck")) return;
		if (sourceentity instanceof net.mcreator.gts.entity.TokiEntity wildToki
				&& "Toki.kick".equals(wildToki.getEntityData().get(net.mcreator.gts.entity.TokiEntity.DATA_AnimationDecision))) return;
		double DistanceToTarget = 0;
		double TokiSpeed = 0;
		double TargetHeight = 0;
		double Scale = 0;
		boolean TokiInWater = false;
		TokiInWater = sourceentity.isInWater();
		TokiSpeed = Math.pow(sourceentity.getDeltaMovement().x(), 2) + Math.pow(sourceentity.getDeltaMovement().z(), 2);
		// Ranged attacks must progress even when Toki or its target stands still.
		sourceentity.getPersistentData().putDouble("RangeAttack", sourceentity.getPersistentData().getDouble("RangeAttack") + 1);
		if (TokiSpeed >= (TokiInWater ? 0.000784 : 0.01)) {
			sourceentity.getPersistentData().putDouble("BreachAttack", 0);
		} else {
			sourceentity.getPersistentData().putDouble("BreachAttack", sourceentity.getPersistentData().getDouble("BreachAttack") + 1);
		}
		entity.getPersistentData().putDouble("TargetRangeAttack", entity.getPersistentData().getDouble("TargetRangeAttack") + 1);
		if (sourceentity.getPersistentData().getDouble("RangeAttack") >= 15) {
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
		}
		if (entity.getPersistentData().getDouble("TargetRangeAttack") >= 15) {
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
		if (sourceentity.getPersistentData().getDouble("RangeAttack") >= 6 && entity.getPersistentData().getDouble("TargetRangeAttack") >= 6) {
			if (Mth.nextInt(RandomSource.create(), 1, (int) (TokiInWater ? 10 : 4)) == 1) {
				DistanceToTarget = Math.pow(sourceentity.getX() - entity.getX(), 2) + Math.pow(sourceentity.getZ() - entity.getZ(), 2);
				TargetHeight = entity.getY() - sourceentity.getY();
				Scale = sourceentity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity32.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0;
				sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY() + 1), (entity.getZ())));
				if (DistanceToTarget >= Scale * Scale * 100 && (sourceentity instanceof LivingEntity _liveEnt && entity != null ? (_liveEnt.hasLineOfSight(entity) || entity.isUnderWater() || sourceentity.isUnderWater()) : false)) {
					TokiSniperAttackProcedure.execute(world, entity, sourceentity);
				} else if (DistanceToTarget <= Scale * Scale * 16 && DistanceToTarget > (Scale + 0.5) * (Scale + 0.5) && Math.abs(TargetHeight) <= Scale * 2) {
					TokiSpitAttackProcedure.execute(world, entity, sourceentity);
				} else if (Mth.nextInt(RandomSource.create(), 1, 3) == 1 && DistanceToTarget > Scale * Scale * 16 && DistanceToTarget < Scale * Scale * 100) {
					TokiDroneSummonProcedure.execute(world, entity, sourceentity);
				}
			}
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
		if (!sourceentity.getPersistentData().getBoolean("AttackCheck") && sourceentity.getPersistentData().getDouble("BreachAttack") >= 20) {
			Scale = sourceentity instanceof LivingEntity _livingEntity42 && _livingEntity42.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity42.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0;
			DistanceToTarget = Math.pow(sourceentity.getX() - entity.getX(), 2) + Math.pow(sourceentity.getZ() - entity.getZ(), 2);
			TargetHeight = entity.getY() - sourceentity.getY();
			if (DistanceToTarget >= Scale * Scale * 4) {
				TokiTrappedBreachProcedure.execute(world, entity, sourceentity);
			} else if (TargetHeight <= Scale) {
				TokiLowerBreachProcedure.execute(world, entity, sourceentity);
			} else if (TargetHeight > Scale && TargetHeight < Scale * 3) {
				TokiMiddleBreachProcedure.execute(world, entity, sourceentity);
			} else if (TargetHeight >= Scale * 3 && TargetHeight <= Scale * 4) {
				TokiUpperBreachProcedure.execute(world, entity, sourceentity);
			}
			sourceentity.getPersistentData().putDouble("BreachAttack", 0);
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
	}
}
