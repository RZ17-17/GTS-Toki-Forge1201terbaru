package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableGroundSlamProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableSit) {
			GtsModVariables.MapVariables.get(world).DisableSit = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableSit = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
