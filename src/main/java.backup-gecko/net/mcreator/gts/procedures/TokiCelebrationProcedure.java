package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.GtsMod;

public class TokiCelebrationProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity == (sourceentity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) && Mth.nextInt(RandomSource.create(), 1, 5) == 1 && !GtsModVariables.MapVariables.get(world).DisableShowOff && !world.isClientSide()) {
			GtsMod.queueServerWork(20, () -> {
				if (sourceentity.isAlive()) {
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(true);
					}
					if (sourceentity instanceof TokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.showOff");
					{
						Entity _ent = sourceentity;
						_ent.setYRot(sourceentity.getYRot());
						_ent.setXRot(0);
						_ent.setYBodyRot(_ent.getYRot());
						_ent.setYHeadRot(_ent.getYRot());
						_ent.yRotO = _ent.getYRot();
						_ent.xRotO = _ent.getXRot();
						if (_ent instanceof LivingEntity _entity) {
							_entity.yBodyRotO = _entity.getYRot();
							_entity.yHeadRotO = _entity.getYRot();
						}
					}
				}
			});
			GtsMod.queueServerWork(95, () -> {
				if (sourceentity.isAlive()) {
					if (sourceentity instanceof TokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "empty");
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
				}
			});
		}
	}
}
