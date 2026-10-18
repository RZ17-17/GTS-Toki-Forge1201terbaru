package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableTrappedBreachProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableTrappedBreach) {
			GtsModVariables.MapVariables.get(world).DisableTrappedBreach = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableTrappedBreach = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
