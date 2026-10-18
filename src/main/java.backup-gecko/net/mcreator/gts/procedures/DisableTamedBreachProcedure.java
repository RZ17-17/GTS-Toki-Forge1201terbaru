package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableTamedBreachProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableTamedGiantessBreach) {
			GtsModVariables.MapVariables.get(world).DisableTamedGiantessBreach = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableTamedGiantessBreach = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
