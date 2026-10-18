package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.GtsMod;

public class TokiLowerBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!GtsModVariables.MapVariables.get(world).DisableLowerBreach) {
			if (sourceentity instanceof TokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.LowerBreach");
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", true);
			sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
			GtsMod.queueServerWork(20, () -> {
				if (sourceentity.isAlive()) {
					TokiLowerBreachExplosionProcedure.execute(world, sourceentity);
					if (Mth.nextInt(RandomSource.create(), 1, 3) == 3 && !GtsModVariables.MapVariables.get(world).DisableDrone && world.getDifficulty() == Difficulty.HARD) {
						TokiDroneSpawnProcedure.execute(world, entity, sourceentity);
					}
				}
			});
			GtsMod.queueServerWork(33, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", false);
					sourceentity.getPersistentData().putBoolean("AttackCheck", false);
					if (sourceentity instanceof TokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "empty");
				}
			});
		}
	}
}
