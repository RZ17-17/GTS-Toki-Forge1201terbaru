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
import net.mcreator.gts.entity.TamedCandyEntity;

import javax.annotation.Nullable;

import java.util.ArrayList;

@EventBusSubscriber
public class TamedCandyJoinTheWorldProcedure {
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
		if (!world.isClientSide() && entity instanceof TamedCandyEntity) {
			if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
				_livingEntity2.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(100);
			entity.getPersistentData().putDouble("RangeAttack", 0);
			entity.getPersistentData().putDouble("BreachAttack", 0);
			entity.getPersistentData().putBoolean("AttackCheck", false);
			entity.getPersistentData().putBoolean("ExplosionImmunityCheck", false);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER.get(), 60, 1, false, false));
			if (entity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(false);
			}
			for (Entity entityiterator : new ArrayList<>(entity.getPassengers())) {
				entityiterator.stopRiding();
			}
			if (!((entity instanceof TamedCandyEntity _datEntS ? _datEntS.getEntityData().get(TamedCandyEntity.DATA_AnimationDecision) : "").equals("candy.sleep"))
					&& !((entity instanceof TamedCandyEntity _datEntS ? _datEntS.getEntityData().get(TamedCandyEntity.DATA_AnimationDecision) : "").equals("candy.sit"))) {
				if (entity instanceof TamedCandyEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "empty");
			}
		}
	}
}
