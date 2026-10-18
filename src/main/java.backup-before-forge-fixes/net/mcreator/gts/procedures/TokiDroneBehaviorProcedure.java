package net.mcreator.gts.procedures;

import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.mcreator.gts.entity.TokiDroneEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class TokiDroneBehaviorProcedure {
	@SubscribeEvent
	public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
		execute(event, event.getEntity().level(), event.getOriginalAboutToBeSetTarget(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof TokiDroneEntity && !world.isClientSide()) {
			if ((sourceentity instanceof LivingEntity _liveEnt && entity != null ? _liveEnt.hasLineOfSight(entity) : false) || (sourceentity != null ? entity.distanceTo(sourceentity) : -1) >= 5) {
				sourceentity.setDeltaMovement(new Vec3((sourceentity.getDeltaMovement().x() * 1.2), (sourceentity.getDeltaMovement().y()), (sourceentity.getDeltaMovement().z() * 1.2)));
				sourceentity.getPersistentData().putDouble("Attack Timer", (sourceentity.getPersistentData().getDouble("Attack Timer") + 1));
				if (sourceentity.getPersistentData().getDouble("Attack Timer") >= Mth.nextInt(RandomSource.create(), 100, 150)) {
					sourceentity.getPersistentData().putDouble("Attack Timer", 0);
					TokiDroneFireballProcedure.execute(world, entity, sourceentity);
				}
			} else {
				if (sourceentity instanceof Mob _entity)
					_entity.getNavigation().moveTo((entity.getX()), (entity.getY() + 1), (entity.getZ()), 1);
				sourceentity.getPersistentData().putDouble("Attack Timer", 0);
			}
		}
	}
}
