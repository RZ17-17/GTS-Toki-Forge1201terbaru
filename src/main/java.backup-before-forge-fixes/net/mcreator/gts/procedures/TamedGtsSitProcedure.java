package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TamedCandyEntity;

import java.util.UUID;

public class TamedGtsSitProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity TamedGts = null;
		TamedGts = new Object() {
			Entity entityFromStringUUID(String uuid, Level world) {
				Entity _uuidentity = null;
				if (world instanceof ServerLevel _server) {
					try {
						_uuidentity = _server.getEntity(UUID.fromString(uuid));
					} catch (Exception e) {
					}
				}
				return _uuidentity;
			}
		}.entityFromStringUUID((entity.getPersistentData().getString("targetedTamedGiantess")), (Level) world);
		if (TamedGts instanceof TamedCandyEntity) {
			TamedCandySitandStandProcedure.execute(world, TamedGts);
		} else if (TamedGts instanceof TamedTokiEntity) {
			TamedTokiSitAndStandProcedure.execute(world, TamedGts);
		}
	}
}
