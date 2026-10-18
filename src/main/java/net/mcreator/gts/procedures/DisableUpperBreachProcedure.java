package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableUpperBreachProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableUpperBreach) {
			GtsModVariables.MapVariables.get(world).DisableUpperBreach = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableUpperBreach = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
