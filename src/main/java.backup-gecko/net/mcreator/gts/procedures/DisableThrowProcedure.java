package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableThrowProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableThrow) {
			GtsModVariables.MapVariables.get(world).DisableThrow = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableThrow = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
