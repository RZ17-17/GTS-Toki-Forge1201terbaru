package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyBreachProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandyBreach) {
			GtsModVariables.MapVariables.get(world).DisableCandyBreach = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandyBreach = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
