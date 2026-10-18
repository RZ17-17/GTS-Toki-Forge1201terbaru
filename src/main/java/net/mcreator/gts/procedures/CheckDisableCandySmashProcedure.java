package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.mcreator.gts.network.GtsModVariables;

public class CheckDisableCandySmashProcedure {
	public static String execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableCandySmash) {
			return Component.translatable("Disabled").getString();
		}
		return Component.translatable("Enabled").getString();
	}
}
