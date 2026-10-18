package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.GtsMod;

public class TokiSniperAttackProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!GtsModVariables.MapVariables.get(world).DisableSniper) {
			if (sourceentity instanceof TokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.RangeAttack");
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
			GtsMod.queueServerWork(60, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY() + 1), (entity.getZ())));
				}
			});
			GtsMod.queueServerWork(70, () -> {
				if (sourceentity.isAlive()) {
					TokiSniperAttackDamageProcedure.execute(world, sourceentity);
				}
			});
			GtsMod.queueServerWork(90, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					sourceentity.getPersistentData().putBoolean("AttackCheck", false);
					if (sourceentity instanceof TokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "empty");
				}
			});
		}
	}
}
