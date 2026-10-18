package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class GiantessTurnSpeedIncreaseProcedure {
	public static void execute(LevelAccessor world) {
		GtsModVariables.MapVariables.get(world).Toki_TurnSpeed = GtsModVariables.MapVariables.get(world).Toki_TurnSpeed + 1;
		GtsModVariables.MapVariables.get(world).syncData(world);
	}
}
