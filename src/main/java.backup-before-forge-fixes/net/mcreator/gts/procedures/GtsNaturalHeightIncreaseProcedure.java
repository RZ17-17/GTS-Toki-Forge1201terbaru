package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class GtsNaturalHeightIncreaseProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).GtsNaturalSpawnHeight < 15) {
			GtsModVariables.MapVariables.get(world).GtsNaturalSpawnHeight = GtsModVariables.MapVariables.get(world).GtsNaturalSpawnHeight + 1;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
