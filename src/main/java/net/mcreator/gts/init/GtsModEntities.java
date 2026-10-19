
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.entity.FireflyEntity;
import net.mcreator.gts.entity.TokiDroneEntity;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TamedAraEntity;
import net.mcreator.gts.entity.TamedFireflyEntity;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.entity.HeartBulletEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;
import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.GtsMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GtsModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, GtsMod.MODID);
	public static final RegistryObject<EntityType<TokiEntity>> TOKI = register("toki",
			EntityType.Builder.<TokiEntity>of(TokiEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)

					.sized(0.4f, 2.76f));
	public static final RegistryObject<EntityType<TokiDroneEntity>> TOKI_DRONE = register("toki_drone",
			EntityType.Builder.<TokiDroneEntity>of(TokiDroneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.7f, 0.3f));
	public static final RegistryObject<EntityType<GiantessSpitEntity>> GIANTESS_SPIT = register("giantess_spit",
			EntityType.Builder.<GiantessSpitEntity>of(GiantessSpitEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<CandyEntity>> CANDY = register("candy",
			EntityType.Builder.<CandyEntity>of(CandyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)

					.sized(0.4f, 2.96f));
	public static final RegistryObject<EntityType<HeartBulletEntity>> HEART_BULLET = register("heart_bullet",
			EntityType.Builder.<HeartBulletEntity>of(HeartBulletEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<TamedTokiEntity>> TAMED_TOKI = register("tamed_toki",
			EntityType.Builder.<TamedTokiEntity>of(TamedTokiEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)

					.sized(0.4f, 2.76f));
	public static final RegistryObject<EntityType<TamedCandyEntity>> TAMED_CANDY = register("tamed_candy",
			EntityType.Builder.<TamedCandyEntity>of(TamedCandyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)

					.sized(0.4f, 2.96f));

	public static final RegistryObject<EntityType<TamedAraEntity>> TAMED_ARA = register("tamed_ara",
			EntityType.Builder.<TamedAraEntity>of(TamedAraEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 2.8f));
	public static final RegistryObject<EntityType<TamedFireflyEntity>> TAMED_FIREFLY = register("tamed_firefly",
			EntityType.Builder.<TamedFireflyEntity>of(TamedFireflyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 2.8f));

	public static final RegistryObject<EntityType<AraEntity>> ARA = register("ara",
			EntityType.Builder.<AraEntity>of(AraEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 2.8f));
	public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY = register("firefly",
			EntityType.Builder.<FireflyEntity>of(FireflyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 2.8f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(SpawnPlacementRegisterEvent event) {
		TokiEntity.init(event);
		TokiDroneEntity.init(event);
		CandyEntity.init(event);
		TamedTokiEntity.init(event);
		TamedCandyEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(TOKI.get(), TokiEntity.createAttributes().build());
		event.put(TOKI_DRONE.get(), TokiDroneEntity.createAttributes().build());
		event.put(CANDY.get(), CandyEntity.createAttributes().build());
		event.put(TAMED_TOKI.get(), TamedTokiEntity.createAttributes().build());
		event.put(TAMED_CANDY.get(), TamedCandyEntity.createAttributes().build());
		event.put(TAMED_ARA.get(), TamedAraEntity.createAttributes().build());
		event.put(TAMED_FIREFLY.get(), TamedFireflyEntity.createAttributes().build());
		event.put(ARA.get(), AraEntity.createAttributes().build());
		event.put(FIREFLY.get(), FireflyEntity.createAttributes().build());
	}
}
