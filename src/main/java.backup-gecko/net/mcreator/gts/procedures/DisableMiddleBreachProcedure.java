package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableMiddleBreachProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableMiddleBreach) {
			GtsModVariables.MapVariables.get(world).DisableMiddleBreach = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableMiddleBreach = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
