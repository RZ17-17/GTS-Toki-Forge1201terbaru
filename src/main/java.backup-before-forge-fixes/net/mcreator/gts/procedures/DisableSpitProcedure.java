package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableSpitProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableSpit) {
			GtsModVariables.MapVariables.get(world).DisableSpit = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableSpit = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
