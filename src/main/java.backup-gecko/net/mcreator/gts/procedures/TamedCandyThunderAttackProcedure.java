package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.GtsMod;

import com.ibm.icu.number.Scale;

public class TamedCandyThunderAttackProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double Scale = 0;
		if (!GtsModVariables.MapVariables.get(world).DisableCandyThunderMagic) {
			if (sourceentity instanceof TamedCandyEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.thunderMagic");
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			GtsMod.queueServerWork(20, () -> {
				if (sourceentity.isAlive()) {
					for (int index0 = 0; index0 < (int) (sourceentity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(GtsModAttributes.SCALE.get())
							? _livingEntity5.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue()
							: 0); index0++) {
						if (world instanceof ServerLevel _level) {
							LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
							entityToSpawn
									.moveTo(Vec3
											.atBottomCenterOf(BlockPos.containing(
													(entity instanceof Player && GtsModVariables.getPlayerVariables(entity).Player_Speed >= 6 ? entity.getX() - entity.getLookAngle().x * 6 : entity.getX()) + Mth.nextDouble(
															RandomSource.create(),
															(sourceentity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity10.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)
																	* (-1),
															sourceentity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity11.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0),
													entity.getY(),
													(entity instanceof Player && GtsModVariables.getPlayerVariables(entity).Player_Speed >= 6 ? entity.getZ() - entity.getLookAngle().z * 6 : entity.getZ()) + Mth.nextDouble(RandomSource.create(),
															(sourceentity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity18.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)
																	* (-1),
															sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsModAttributes.SCALE.get())
																	? _livingEntity19.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue()
																	: 0))));;
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			});
			GtsMod.queueServerWork(30, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					if (sourceentity instanceof TamedCandyEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
					sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				}
			});
		}
	}
}
