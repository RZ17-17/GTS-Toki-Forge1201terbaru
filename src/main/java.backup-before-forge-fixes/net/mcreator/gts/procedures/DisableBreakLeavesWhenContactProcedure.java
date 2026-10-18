package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableBreakLeavesWhenContactProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableLeavesBreakWalk) {
			GtsModVariables.MapVariables.get(world).DisableLeavesBreakWalk = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableLeavesBreakWalk = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
