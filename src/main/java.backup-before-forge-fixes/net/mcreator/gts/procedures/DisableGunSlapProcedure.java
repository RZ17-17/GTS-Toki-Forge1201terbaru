package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableGunSlapProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableGunSlap) {
			GtsModVariables.MapVariables.get(world).DisableGunSlap = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableGunSlap = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
