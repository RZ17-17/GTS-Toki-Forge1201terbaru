package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableTamedSeekOutHostileMobProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableTamedGiantessMobAggro) {
			GtsModVariables.MapVariables.get(world).DisableTamedGiantessMobAggro = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableTamedGiantessMobAggro = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
