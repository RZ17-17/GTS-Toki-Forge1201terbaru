package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.GtsMod;

public class TamedTokiThrowProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof TamedTokiEntity _datEntSetS)
			_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.ThrowAttack");
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER, 35, 1, false, false));
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
				entity.stopRiding();
				entity.setDeltaMovement(new Vec3(
						((-0.5) * sourceentity.getLookAngle().x
								* (sourceentity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity21.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
						(0.125 * (sourceentity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity22.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
						((-0.5) * sourceentity.getLookAngle().z
								* (sourceentity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity24.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0))));
				GtsMod.queueServerWork(1, () -> {
					entity.hurtMarked = true;
				});
			}
		});
		GtsMod.queueServerWork(35, () -> {
			if (sourceentity.isAlive()) {
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				entity.getPersistentData().putBoolean("BeingGrabbed", false);
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (sourceentity instanceof TamedTokiEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}
