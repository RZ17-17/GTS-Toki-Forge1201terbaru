package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.GtsMod;

public class TokiDroneFireballProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!world.isClientSide()) {
			new Object() {
				void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
					sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY() + 1), (entity.getZ())));
					if (world instanceof ServerLevel projectileLevel) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getFireball(Level level, Entity shooter) {
								AbstractHurtingProjectile entityToSpawn = new SmallFireball(EntityType.SMALL_FIREBALL, level);
								entityToSpawn.setOwner(shooter);
								return entityToSpawn;
							}
						}.getFireball(projectileLevel, sourceentity);
						_entityToSpawn.setPos((sourceentity.getX() + sourceentity.getLookAngle().x / 2), (sourceentity.getY() + sourceentity.getLookAngle().y / 2 - 0.1), (sourceentity.getZ() + sourceentity.getLookAngle().z / 2));
						_entityToSpawn.shoot((sourceentity.getLookAngle().x), (sourceentity.getLookAngle().y), (sourceentity.getLookAngle().z), 1, (float) 0.5);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
					final int tick2 = ticks;
					GtsMod.queueServerWork(tick2, () -> {
						if (timedlooptotal > timedloopiterator + 1) {
							timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
						}
					});
				}
			}.timedLoop(0, 3, 2);
		}
	}
}
