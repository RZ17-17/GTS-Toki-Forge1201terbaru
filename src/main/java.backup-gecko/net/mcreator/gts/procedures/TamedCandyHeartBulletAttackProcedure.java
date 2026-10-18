package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.entity.HeartBulletEntity;
import net.mcreator.gts.GtsMod;

public class TamedCandyHeartBulletAttackProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!GtsModVariables.MapVariables.get(world).DisableCandyHeartMagic) {
			if (sourceentity instanceof TamedCandyEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.rangeAttack");
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
			GtsMod.queueServerWork(45, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY() + 1), (entity.getZ())));
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY() + 1), (entity.getZ())));
							{
								Entity _shootFrom = sourceentity;
								Level projectileLevel = _shootFrom.level();
								if (!projectileLevel.isClientSide()) {
									Projectile _entityToSpawn = new Object() {
										public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
											AbstractArrow entityToSpawn = new HeartBulletEntity(GtsModEntities.HEART_BULLET.get(), level) {
												@Override
												public byte getPierceLevel() {
													return piercing;
												}

												@Override
												protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
													if (knockback > 0) {
														double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
														Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
														if (vec3.lengthSqr() > 0.0) {
															livingEntity.push(vec3.x, 0.1, vec3.z);
														}
													}
												}
											};
											entityToSpawn.setOwner(shooter);
											entityToSpawn.setBaseDamage(damage);
											entityToSpawn.setSilent(true);
											return entityToSpawn;
										}
									}.getArrow(projectileLevel, sourceentity, 0, 1, (byte) 1);
									_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
									_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 6, 0);
									projectileLevel.addFreshEntity(_entityToSpawn);
								}
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
			});
			GtsMod.queueServerWork(60, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					sourceentity.getPersistentData().putBoolean("AttackCheck", true);
					if (sourceentity instanceof TamedCandyEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
				}
			});
		}
	}
}
