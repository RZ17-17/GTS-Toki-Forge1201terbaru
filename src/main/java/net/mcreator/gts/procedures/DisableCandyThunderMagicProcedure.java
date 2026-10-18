package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableCandyThunderMagicProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandyThunderMagic) {
			GtsModVariables.MapVariables.get(world).DisableCandyThunderMagic = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableCandyThunderMagic = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
