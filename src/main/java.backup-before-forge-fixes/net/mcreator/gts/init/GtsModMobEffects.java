
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.procedures.TokiDroneNoBatteryProcedure;
import net.mcreator.gts.potion.TokiDroneBatteryMobEffect;
import net.mcreator.gts.potion.ShockWaveImmuneMobEffect;
import net.mcreator.gts.potion.GtsTimerMobEffect;
import net.mcreator.gts.potion.GtsShrinkEffectMobEffect;
import net.mcreator.gts.potion.GiantessSpitOverlayEffectMobEffect;
import net.mcreator.gts.GtsMod;

@EventBusSubscriber
public class GtsModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, GtsMod.MODID);
	public static final RegistryObject<MobEffect> TOKI_DRONE_BATTERY = REGISTRY.register("toki_drone_battery", () -> new TokiDroneBatteryMobEffect());
	public static final RegistryObject<MobEffect> GIANTESS_SPIT_OVERLAY_EFFECT = REGISTRY.register("giantess_spit_overlay_effect", () -> new GiantessSpitOverlayEffectMobEffect());
	public static final RegistryObject<MobEffect> GTS_TIMER = REGISTRY.register("gts_timer", () -> new GtsTimerMobEffect());
	public static final RegistryObject<MobEffect> GTS_SHRINK_EFFECT = REGISTRY.register("gts_shrink_effect", () -> new GtsShrinkEffectMobEffect());
	public static final RegistryObject<MobEffect> SHOCK_WAVE_IMMUNE = REGISTRY.register("shock_wave_immune", () -> new ShockWaveImmuneMobEffect());

	@SubscribeEvent
	public static void onEffectRemoved(MobEffectEvent.Remove event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	@SubscribeEvent
	public static void onEffectExpired(MobEffectEvent.Expired event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	private static void expireEffects(Entity entity, MobEffectInstance effectInstance) {
		if (effectInstance.getEffect().is(TOKI_DRONE_BATTERY)) {
			TokiDroneNoBatteryProcedure.execute(entity);
		}
	}
}
