package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyUpperSmashProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandySmash) {
			GtsModVariables.MapVariables.get(world).DisableCandySmash = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandySmash = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
