package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableRaidWaveSizeIncreaseProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableRaidWaveSizeChange) {
			GtsModVariables.MapVariables.get(world).DisableRaidWaveSizeChange = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableRaidWaveSizeChange = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
