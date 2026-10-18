package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class GiantessTurnSpeedDisplayProcedure {
	public static String execute(LevelAccessor world) {
		return "" + GtsModVariables.MapVariables.get(world).Toki_TurnSpeed;
	}
}
