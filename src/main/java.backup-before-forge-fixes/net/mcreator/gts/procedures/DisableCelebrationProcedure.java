package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCelebrationProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableShowOff) {
			GtsModVariables.MapVariables.get(world).DisableShowOff = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableShowOff = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
