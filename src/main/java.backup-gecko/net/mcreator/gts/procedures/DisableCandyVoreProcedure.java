package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyVoreProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandyVore) {
			GtsModVariables.MapVariables.get(world).DisableCandyVore = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandyVore = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
