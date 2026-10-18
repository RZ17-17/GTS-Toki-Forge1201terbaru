package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableDroneProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableDrone) {
			GtsModVariables.MapVariables.get(world).DisableDrone = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableDrone = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
