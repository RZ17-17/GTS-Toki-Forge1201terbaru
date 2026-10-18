package net.mcreator.gts.procedures;

import net.minecraftforge.event.entity.living.LivingIncomingDamageEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.CandyEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class CandyClothDamageProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double HealthPercentage = 0;
		if (entity instanceof CandyEntity && !GtsModVariables.MapVariables.get(world).DisableClothingDamage) {
			HealthPercentage = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
			if (HealthPercentage < 0.7 && (entity instanceof CandyEntity animatable ? animatable.getTexture() : "null").equals("candytexture")) {
				if (entity instanceof CandyEntity animatable)
					animatable.setTexture("candydamaged1");
			} else if (HealthPercentage < 0.5
					&& ((entity instanceof CandyEntity animatable ? animatable.getTexture() : "null").equals("candytexture") || (entity instanceof CandyEntity animatable ? animatable.getTexture() : "null").equals("candydamaged1"))) {
				if (entity instanceof CandyEntity animatable)
					animatable.setTexture("candydamaged2");
			} else if (HealthPercentage < 0.2 && ((entity instanceof CandyEntity animatable ? animatable.getTexture() : "null").equals("candytexture")
					|| (entity instanceof CandyEntity animatable ? animatable.getTexture() : "null").equals("candydamaged1") || (entity instanceof CandyEntity animatable ? animatable.getTexture() : "null").equals("candydamaged2"))) {
				if (entity instanceof CandyEntity animatable)
					animatable.setTexture("candydamaged3");
			}
		}
	}
}
