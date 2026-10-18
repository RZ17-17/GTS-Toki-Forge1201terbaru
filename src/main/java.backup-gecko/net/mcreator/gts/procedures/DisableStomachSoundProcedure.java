package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableStomachSoundProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableStomachSound) {
			GtsModVariables.MapVariables.get(world).DisableStomachSound = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableStomachSound = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
