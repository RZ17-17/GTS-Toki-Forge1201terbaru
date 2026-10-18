package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableBelchProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableBelch) {
			GtsModVariables.MapVariables.get(world).DisableBelch = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableBelch = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
