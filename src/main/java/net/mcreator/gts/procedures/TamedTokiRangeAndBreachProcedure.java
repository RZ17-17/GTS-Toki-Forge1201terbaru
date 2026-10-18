package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;

import com.ibm.icu.number.Scale;

public class TamedTokiRangeAndBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double DistanceToTarget = 0;
		double TokiSpeed = 0;
		double TargetHeight = 0;
		double TargetSpeed = 0;
		double Scale = 0;
		boolean TokiInWater = false;
		Entity SpeedEntity = null;
		TokiInWater = sourceentity.isInWater();
		SpeedEntity = entity.getRootVehicle();
		TokiSpeed = Math.pow(sourceentity.getDeltaMovement().x(), 2) + Math.pow(sourceentity.getDeltaMovement().z(), 2);
		TargetSpeed = Math.pow(SpeedEntity.getDeltaMovement().x(), 2) + Math.pow(SpeedEntity.getDeltaMovement().z(), 2);
		if (TokiSpeed >= (TokiInWater ? 0.000784 : 0.01)) {
			sourceentity.getPersistentData().putDouble("RangeAttack", (sourceentity.getPersistentData().getDouble("RangeAttack") + 1));
			sourceentity.getPersistentData().putDouble("BreachAttack", 0);
		} else if (TokiSpeed < (TokiInWater ? 0.000784 : 0.01)) {
			sourceentity.getPersistentData().putDouble("BreachAttack", (sourceentity.getPersistentData().getDouble("BreachAttack") + 1));
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
		}
		if (!(entity instanceof Player)) {
			if (TargetSpeed > (TokiInWater ? 0.000784 : 0.01)) {
				entity.getPersistentData().putDouble("TargetRangeAttack", (entity.getPersistentData().getDouble("TargetRangeAttack") + 1));
			} else {
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
			}
		} else {
			if (GtsModVariables.getPlayerVariables(entity).Player_Speed > (TokiInWater ? 2 : 5)) {
				entity.getPersistentData().putDouble("TargetRangeAttack", (entity.getPersistentData().getDouble("TargetRangeAttack") + 1));
			} else {
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
			}
		}
		if (sourceentity.getPersistentData().getDouble("RangeAttack") >= 15) {
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
		}
		if (entity.getPersistentData().getDouble("TargetRangeAttack") >= 15) {
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
		if (sourceentity.getPersistentData().getDouble("RangeAttack") >= 6 && entity.getPersistentData().getDouble("TargetRangeAttack") >= 6) {
			if (Mth.nextInt(RandomSource.create(), 1, (int) (TokiInWater ? 12 : 6)) == 1) {
				Scale = sourceentity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity26.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0;
				DistanceToTarget = Math.pow(sourceentity.getX() - entity.getX(), 2) + Math.pow(sourceentity.getZ() - entity.getZ(), 2);
				TargetHeight = entity.getY() - sourceentity.getY();
				sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY() + 1), (entity.getZ())));
				if (DistanceToTarget >= Scale * Scale * 16 && (sourceentity instanceof LivingEntity _liveEnt && entity != null ? _liveEnt.hasLineOfSight(entity) : false)) {
					TamedTokiSniperAttackProcedure.execute(world, entity, sourceentity);
				} else if (DistanceToTarget <= Scale * Scale * 16 && DistanceToTarget > (Scale + 0.5) * (Scale + 0.5) && Math.abs(TargetHeight) <= Scale * Scale * 4) {
					TamedTokiSpitAttackProcedure.execute(world, entity, sourceentity);
				}
			}
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
		if (sourceentity.getPersistentData().getDouble("BreachAttack") >= 15) {
			DistanceToTarget = Math.pow(sourceentity.getX() - entity.getX(), 2) + Math.pow(sourceentity.getZ() - entity.getZ(), 2);
			TargetHeight = entity.getY() - sourceentity.getY();
			Scale = sourceentity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity47.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0;
			if (DistanceToTarget >= Scale * Scale * 4) {
				sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
				TamedTokiTrappedBreachProcedure.execute(world, sourceentity);
			} else if (TargetHeight <= Scale) {
				TamedTokiLowerBreachProcedure.execute(world, entity, sourceentity);
			} else if (TargetHeight > Scale && TargetHeight < Scale * 3) {
				TamedTokiMiddleBreachProcedure.execute(world, entity, sourceentity);
			} else if (TargetHeight >= Scale * 3 && TargetHeight <= Scale * 4) {
				TamedTokiUpperBreachProcedure.execute(world, entity, sourceentity);
			}
			sourceentity.getPersistentData().putDouble("BreachAttack", 0);
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
	}
}
