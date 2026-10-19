package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.GtsMod;

public class TamedTokiLowerBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!GtsModVariables.MapVariables.get(world).DisableLowerBreach && !GtsModVariables.MapVariables.get(world).DisableTamedGiantessBreach) {
			if (sourceentity instanceof TamedTokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.LowerBreach");
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
					if (sourceentity instanceof TamedTokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
				}
			});
		}
	}
}
