package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.GtsMod;

public class TamedTokiTrappedBreachProcedure {
	public static void execute(LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (false && !GtsModVariables.MapVariables.get(world).DisableTrappedBreach && !GtsModVariables.MapVariables.get(world).DisableTamedGiantessBreach) {
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", true);
			if (sourceentity instanceof TamedTokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.TrappedBreach");
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			GtsMod.queueServerWork(35, () -> {
				if (sourceentity.isAlive()) {
					TokiTrappedBreachExplosionProcedure.execute(world, sourceentity);
				}
			});
			GtsMod.queueServerWork(70, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof TamedTokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", false);
					sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				}
			});
		}
	}
}
