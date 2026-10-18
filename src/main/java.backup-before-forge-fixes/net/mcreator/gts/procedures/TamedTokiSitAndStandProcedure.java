package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TamedTokiEntity;

public class TamedTokiSitAndStandProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (entity instanceof TamedTokiEntity _datEntL1 && _datEntL1.getEntityData().get(TamedTokiEntity.DATA_Sleep)) {
				if (entity instanceof TamedTokiEntity _datEntSetL)
					_datEntSetL.getEntityData().set(TamedTokiEntity.DATA_Sleep, false);
				if (entity instanceof TamedTokiEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
			} else if (!(entity instanceof TamedTokiEntity _datEntL4 && _datEntL4.getEntityData().get(TamedTokiEntity.DATA_Sleep))
					&& (entity instanceof TamedTokiEntity _datEntS ? _datEntS.getEntityData().get(TamedTokiEntity.DATA_AnimationDecision) : "").equals("empty") && !(world instanceof Level _lvl6 && _lvl6.isDay())) {
				if (entity instanceof TamedTokiEntity _datEntSetL)
					_datEntSetL.getEntityData().set(TamedTokiEntity.DATA_Sleep, true);
				if (entity instanceof TamedTokiEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.sleep");
			} else if (!(entity instanceof TamedTokiEntity _datEntL9 && _datEntL9.getEntityData().get(TamedTokiEntity.DATA_Sleep))
					&& (entity instanceof TamedTokiEntity _datEntS ? _datEntS.getEntityData().get(TamedTokiEntity.DATA_AnimationDecision) : "").equals("empty") && world instanceof Level _lvl11 && _lvl11.isDay()) {
				if (entity instanceof TamedTokiEntity _datEntSetL)
					_datEntSetL.getEntityData().set(TamedTokiEntity.DATA_Sleep, true);
				if (entity instanceof TamedTokiEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.sit");
			}
		}
	}
}
