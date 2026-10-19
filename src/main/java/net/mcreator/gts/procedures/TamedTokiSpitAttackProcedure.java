package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;
import net.mcreator.gts.GtsMod;

import com.ibm.icu.number.Scale;

public class TamedTokiSpitAttackProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double Scale = 0;
		if (!GtsModVariables.MapVariables.get(world).DisableSpit) {
			if (sourceentity instanceof TamedTokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.Spit");
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			GtsMod.queueServerWork(15, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES,
							new Vec3((entity.getX()),
									(entity.getY() + (sourceentity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity7.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) + 1),
									(entity.getZ())));
					{
						Entity _shootFrom = sourceentity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
									AbstractArrow entityToSpawn = new GiantessSpitEntity(GtsModEntities.GIANTESS_SPIT.get(), level) {
										@Override
										public byte getPierceLevel() {
											return piercing;
										}
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
							}.getArrow(projectileLevel, sourceentity, 0, 0, (byte) 0);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z,
									(float) ((sourceentity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity10.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 3), 1);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:spitsoundeffect")), SoundSource.HOSTILE,
									(float) (sourceentity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity16.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1);
						} else {
							_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:spitsoundeffect")), SoundSource.HOSTILE,
									(float) (sourceentity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity16.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
						}
					}
				}
			});
			GtsMod.queueServerWork(25, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					if (sourceentity instanceof TamedTokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
					sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				}
			});
		}
	}
}
