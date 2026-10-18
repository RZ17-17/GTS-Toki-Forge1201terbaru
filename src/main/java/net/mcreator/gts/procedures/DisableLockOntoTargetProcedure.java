package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableLockOntoTargetProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableLockOnTarget) {
			GtsModVariables.MapVariables.get(world).DisableLockOnTarget = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableLockOnTarget = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
