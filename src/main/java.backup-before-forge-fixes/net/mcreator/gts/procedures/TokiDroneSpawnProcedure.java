package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.entity.TokiDroneEntity;

import java.util.List;
import java.util.Comparator;

public class TokiDroneSpawnProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!world.isClientSide()) {
			for (int index0 = 0; index0 < 3; index0++) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = GtsModEntities.TOKI_DRONE.get().spawn(_level,
							BlockPos.containing(sourceentity.getX(),
									sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity3.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 3,
									sourceentity.getZ()),
							MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setDeltaMovement((Mth.nextDouble(RandomSource.create(), -0.5, 0.5)), 0.5, (Mth.nextDouble(RandomSource.create(), -0.5, 0.5)));
					}
				}
				{
					final Vec3 _center = new Vec3((sourceentity.getX()),
							(sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity10.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 3),
							(sourceentity.getZ()));
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof TokiDroneEntity) {
							if (entityiterator instanceof Mob _entity && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _ent)
								_entity.setTarget(_ent);
							entityiterator.setCustomName(Component.literal(SupporterNameProcedure.execute()));
						}
					}
				}
			}
		}
	}
}
