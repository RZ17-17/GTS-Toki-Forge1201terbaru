package net.mcreator.gts.procedures;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TamedTokiEntity;

public class TamedTokiAiConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof TamedTokiEntity _datEntL0 && _datEntL0.getEntityData().get(TamedTokiEntity.DATA_Sleep)) && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof ArmorStand)
				&& (entity.getFirstPassenger()) == null;
	}
}
