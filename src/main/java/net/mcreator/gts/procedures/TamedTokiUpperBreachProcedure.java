package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.GtsMod;

public class TamedTokiUpperBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (false && !GtsModVariables.MapVariables.get(world).DisableUpperBreach && !GtsModVariables.MapVariables.get(world).DisableTamedGiantessBreach) {
			if (sourceentity instanceof TamedTokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.UpperBreach");
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", true);
			sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
			GtsMod.queueServerWork(25, () -> {
				if (sourceentity.isAlive()) {
					if (world instanceof Level _level && !_level.isClientSide())
						_level.explode(null,
								(sourceentity.getX() + sourceentity.getLookAngle().x
										* (sourceentity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity12.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
								(sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity14.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 3),
								(sourceentity.getZ() + sourceentity.getLookAngle().z
										* (sourceentity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity17.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
								(float) ((sourceentity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity18.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2),
								Level.ExplosionInteraction.MOB);
				}
			});
			GtsMod.queueServerWork(40, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", false);
					sourceentity.getPersistentData().putBoolean("AttackCheck", false);
					if (sourceentity instanceof TamedTokiEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "empty");
				}
			});
		}
	}
}
