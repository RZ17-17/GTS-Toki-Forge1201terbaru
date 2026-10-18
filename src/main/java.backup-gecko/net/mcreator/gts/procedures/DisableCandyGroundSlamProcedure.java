package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyGroundSlamProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandyShockWave) {
			GtsModVariables.MapVariables.get(world).DisableCandyShockWave = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandyShockWave = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
