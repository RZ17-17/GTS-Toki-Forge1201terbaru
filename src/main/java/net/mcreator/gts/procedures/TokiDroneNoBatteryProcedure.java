package net.mcreator.gts.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TokiDroneEntity;

public class TokiDroneNoBatteryProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof TokiDroneEntity) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
