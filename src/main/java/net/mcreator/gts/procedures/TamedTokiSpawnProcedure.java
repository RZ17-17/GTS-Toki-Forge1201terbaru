package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.entity.TamedTokiEntity;

import java.util.Comparator;

public class TamedTokiSpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double health, double maxhealth, double movementspeed, double pitch, double safefall, double scale, double yaw) {
		if (entity == null)
			return;
		double WaveCount = 0;
		double TokiSpawnScale = 0;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = GtsModEntities.TAMED_TOKI.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot((float) yaw);
				entityToSpawn.setYBodyRot((float) yaw);
				entityToSpawn.setYHeadRot((float) yaw);
				entityToSpawn.setXRot((float) pitch);
			}
		}
		if (!world.getEntitiesOfClass(TamedTokiEntity.class, AABB.ofSize(new Vec3(x, y, z), 2, 2, 2), e -> true).isEmpty()) {
			if (((Entity) world.getEntitiesOfClass(TamedTokiEntity.class, AABB.ofSize(new Vec3(x, y, z), 2, 2, 2), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()))
				_livingEntity3.getAttribute(GtsModAttributes.SCALE.get()).setBaseValue(scale);
			}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof LivingEntity _entity)
				_entity.setHealth((float) health);
			if (((Entity) world.getEntitiesOfClass(TamedTokiEntity.class, AABB.ofSize(new Vec3(x, y, z), 2, 2, 2), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof TamableAnimal _toTame && entity instanceof Player _owner)
				_toTame.tame(_owner);
		}
	}
}
