package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableClothingDamageProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableClothingDamage) {
			GtsModVariables.MapVariables.get(world).DisableClothingDamage = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableClothingDamage = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
