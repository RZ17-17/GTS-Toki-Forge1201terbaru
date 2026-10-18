package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyKickProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandyKick) {
			GtsModVariables.MapVariables.get(world).DisableCandyKick = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandyKick = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
