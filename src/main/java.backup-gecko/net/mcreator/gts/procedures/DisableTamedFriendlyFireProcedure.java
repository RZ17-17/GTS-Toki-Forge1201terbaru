package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableTamedFriendlyFireProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableTamedGiantessFriendlyFire) {
			GtsModVariables.MapVariables.get(world).DisableTamedGiantessFriendlyFire = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableTamedGiantessFriendlyFire = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
