package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableSniperProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableSniper) {
			GtsModVariables.MapVariables.get(world).DisableSniper = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableSniper = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
