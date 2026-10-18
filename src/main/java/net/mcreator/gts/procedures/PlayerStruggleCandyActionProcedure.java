package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.GtsMod;

public class PlayerStruggleCandyActionProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		if (entity instanceof CandyEntity _datEntSetS)
			_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.angry");
		entity.getPersistentData().putBoolean("ThrowingTarget", true);
		sourceentity.stopRiding();
		entity.getPersistentData().putBoolean("CanBeFriend", false);
		{
			Entity _ent = sourceentity;
			if (!_ent.level().isClientSide() && _ent.getServer() != null) {
				_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
						_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/stopsound @a[distance=..10] * gts:toki.lick");
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, (entity.getX()),
					(entity.getY() + (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity9.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 2), (entity.getZ()),
					(int) ((entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity14.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 10),
					((entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity11.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2),
					((entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity12.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2),
					((entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity13.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 2), 0);
		GtsMod.queueServerWork(45, () -> {
			if (entity.isAlive()) {
				if (entity instanceof CandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "empty");
				entity.setDeltaMovement(new Vec3(0, 0, 0));
				if (entity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				entity.getPersistentData().putBoolean("AttackCheck", false);
			}
		});
	}
}
