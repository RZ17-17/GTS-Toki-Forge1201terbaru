package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyUpperSwipeProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandySwipe) {
			GtsModVariables.MapVariables.get(world).DisableCandySwipe = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandySwipe = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
