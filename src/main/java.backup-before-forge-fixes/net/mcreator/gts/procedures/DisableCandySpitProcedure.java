package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandySpitProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandySpit) {
			GtsModVariables.MapVariables.get(world).DisableCandySpit = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandySpit = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
