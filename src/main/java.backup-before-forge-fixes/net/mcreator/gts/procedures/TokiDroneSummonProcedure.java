package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.GtsMod;

public class TokiDroneSummonProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!GtsModVariables.MapVariables.get(world).DisableDrone) {
			if (sourceentity instanceof TokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.DroneRelease");
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
			GtsMod.queueServerWork(25, () -> {
				if (sourceentity.isAlive()) {
					TokiDroneSpawnProcedure.execute(world, entity, sourceentity);
				}
			});
			GtsMod.queueServerWork(35, () -> {
				if (sourceentity.isAlive()) {
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					if (sourceentity instanceof TokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "empty");
				}
			});
		}
	}
}
