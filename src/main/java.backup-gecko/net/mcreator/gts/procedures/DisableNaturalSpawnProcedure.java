package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableNaturalSpawnProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableNaturalSpawn) {
			GtsModVariables.MapVariables.get(world).DisableNaturalSpawn = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableNaturalSpawn = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
