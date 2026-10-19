package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.GtsMod;

public class TokiThrowAttackProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof TokiEntity _datEntSetS)
			_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.ThrowAttack");
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER.get(), 35, 1, false, false));
		sourceentity.getPersistentData().putDouble("TargetStruggleCount", 0);
		if (sourceentity.getXRot() < -35) {
			{
				Entity _ent = sourceentity;
				_ent.setYRot(sourceentity.getYRot());
				_ent.setXRot(-35);
				_ent.setYBodyRot(_ent.getYRot());
				_ent.setYHeadRot(_ent.getYRot());
				_ent.yRotO = _ent.getYRot();
				_ent.xRotO = _ent.getXRot();
				if (_ent instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
		} else if (sourceentity.getXRot() > 35) {
			{
				Entity _ent = sourceentity;
				_ent.setYRot(sourceentity.getYRot());
				_ent.setXRot(35);
				_ent.setYBodyRot(_ent.getYRot());
				_ent.setYHeadRot(_ent.getYRot());
				_ent.yRotO = _ent.getYRot();
				_ent.xRotO = _ent.getXRot();
				if (_ent instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
		}
		GtsMod.queueServerWork(15, () -> {
			if (sourceentity.isAlive()) {
				entity.setShiftKeyDown(false);
				entity.startRiding(sourceentity);
			}
		});
		GtsMod.queueServerWork(20, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				sourceentity.getPersistentData().putBoolean("ThrowingTarget", true);
				entity.stopRiding();
				entity.setDeltaMovement(new Vec3(
						((-0.5) * sourceentity.getLookAngle().x
								* (sourceentity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
						(0.125 * (sourceentity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity28.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
						((-0.5) * sourceentity.getLookAngle().z
								* (sourceentity instanceof LivingEntity _livingEntity30 && _livingEntity30.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity30.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
				GtsMod.queueServerWork(1, () -> {
					entity.hurtMarked = true;
				});
			}
		});
		GtsMod.queueServerWork(35, () -> {
			if (sourceentity.isAlive() && sourceentity.getPersistentData()
					.getDouble("TargetStruggleCount") < (sourceentity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity36.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)) {
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				entity.getPersistentData().putBoolean("BeingGrabbed", false);
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (sourceentity instanceof TokiEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}
