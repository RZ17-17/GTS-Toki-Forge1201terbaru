package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TamedCandyEntity;

public class TamedCandySitandStandProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (entity instanceof TamedCandyEntity _datEntL1 && _datEntL1.getEntityData().get(TamedCandyEntity.DATA_Sleep)) {
				if (entity instanceof TamedCandyEntity _datEntSetL)
					_datEntSetL.getEntityData().set(TamedCandyEntity.DATA_Sleep, false);
				if (entity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
			} else if (!(entity instanceof TamedCandyEntity _datEntL4 && _datEntL4.getEntityData().get(TamedCandyEntity.DATA_Sleep))
					&& (entity instanceof TamedCandyEntity _datEntS ? _datEntS.getEntityData().get(TamedCandyEntity.DATA_AnimationDecision) : "").equals("empty") && world instanceof Level _lvl6 && _lvl6.isDay()) {
				if (entity instanceof TamedCandyEntity _datEntSetL)
					_datEntSetL.getEntityData().set(TamedCandyEntity.DATA_Sleep, true);
				((TamedCandyEntity) entity).getNavigation().stop();
				((TamedCandyEntity) entity).setTarget(null);
				if (entity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.sit");
			} else if (!(entity instanceof TamedCandyEntity _datEntL9 && _datEntL9.getEntityData().get(TamedCandyEntity.DATA_Sleep))
					&& (entity instanceof TamedCandyEntity _datEntS ? _datEntS.getEntityData().get(TamedCandyEntity.DATA_AnimationDecision) : "").equals("empty") && !(world instanceof Level _lvl11 && _lvl11.isDay())) {
				if (entity instanceof TamedCandyEntity _datEntSetL)
					_datEntSetL.getEntityData().set(TamedCandyEntity.DATA_Sleep, true);
				if (entity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.sleep");
			}
		}
	}
}
