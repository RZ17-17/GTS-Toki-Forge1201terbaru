package net.mcreator.gts.procedures;

import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.GtsMod;

import javax.annotation.Nullable;

import java.util.ArrayList;

@EventBusSubscriber
public class CandyJoinTheWorldProcedure {
	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent event) {
		execute(event, event.getLevel(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide() && entity instanceof CandyEntity) {
			if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE))
				_livingEntity2.getAttribute(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE).setBaseValue(100);
			entity.getPersistentData().putDouble("RangeAttack", 0);
			entity.getPersistentData().putDouble("BreachAttack", 0);
			entity.getPersistentData().putBoolean("AttackCheck", false);
			entity.getPersistentData().putBoolean("ExplosionImmunityCheck", false);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER, 60, 1, false, false));
			if ((entity instanceof CandyEntity animatable ? animatable.getTexture() : "null").equals("candyspawntexture")) {
				if (entity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(true);
				}
				GtsMod.queueServerWork(25, () -> {
					if (entity instanceof CandyEntity animatable)
						animatable.setTexture("candytexture");
				});
				GtsMod.queueServerWork(105, () -> {
					if (entity instanceof CandyEntity _datEntSetS)
						_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "empty");
					if (entity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
				});
			} else {
				if (entity instanceof CandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "empty");
				if (entity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
			}
			for (Entity entityiterator : new ArrayList<>(entity.getPassengers())) {
				entity.getPersistentData().putBoolean("ThrowingTarget", true);
				entityiterator.stopRiding();
			}
		}
	}
}
