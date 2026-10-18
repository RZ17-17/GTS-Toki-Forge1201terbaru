package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableLowerBreachProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableLowerBreach) {
			GtsModVariables.MapVariables.get(world).DisableLowerBreach = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableLowerBreach = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
