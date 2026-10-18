package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.world.inventory.GtsCommonGUIMenu;

public class ConfigResetProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _plr0 && _plr0.containerMenu instanceof GtsCommonGUIMenu) {
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
		ResetAllConfigProcedure.execute(world);
	}
}
