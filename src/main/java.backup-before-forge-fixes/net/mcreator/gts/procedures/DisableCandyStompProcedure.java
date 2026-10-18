package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyStompProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandyStep) {
			GtsModVariables.MapVariables.get(world).DisableCandyStep = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandyStep = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
