
package net.mcreator.gts.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.event.entity.RegisterSpawnPlacementsEvent;
import net.minecraftforge.fml.common.asm.enumextension.EnumProxy;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.procedures.TokiTickUpdateProcedure;
import net.mcreator.gts.procedures.TokiCelebrationProcedure;
import net.mcreator.gts.procedures.GtsSpawnProcedure;
import net.mcreator.gts.procedures.GtsSpawnConditionProcedure;
import net.mcreator.gts.procedures.GtsHurtProcedure;
import net.mcreator.gts.procedures.GtsDeathProcedure;
import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModItems;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.GtsNavigation;
import net.mcreator.gts.GtsMoveHelper;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.HashMap;

public class TokiEntity extends Raider implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TokiEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TokiEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TokiEntity.class, EntityDataSerializers.STRING);
	public static final EnumProxy<Raid.RaiderType> RAIDER_TYPE = new EnumProxy<>(Raid.RaiderType.class, GtsModEntities.TOKI, new int[]{0, 0, 0, 1, 1, 1, 1, 1});
	public static final EntityDataAccessor<Integer> DATA_passengerSlots = SynchedEntityData.defineId(TokiEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<String> DATA_AnimationDecision = SynchedEntityData.defineId(TokiEntity.class, EntityDataSerializers.STRING);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private Map<Integer, Vec3> passengerSlots;

	public TokiEntity(EntityType<TokiEntity> type, Level world) {
		super(type, world);
		xpReward = 50;
		setNoAi(false);
		passengerSlots = new HashMap();
		if (!GtsModVariables.MapVariables.get(this.level()).DisableLeavesBreakWalk) {
			this.setPathfindingMalus(PathType.LEAVES, 0.0F);
		} ;
		moveControl = new GtsMoveHelper(this, (float) GtsModVariables.MapVariables.get(this.level()).Toki_TurnSpeed);
		noCulling = true;
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new GtsNavigation(this, world);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "asuma_toki_spawn");
		builder.define(DATA_passengerSlots, 1);
		builder.define(DATA_AnimationDecision, "Toki.Spawn");
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractGolem.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Villager.class, false, false));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1, true) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack()
						&& (((entity.getX() - this.mob.getX()) * (entity.getX() - this.mob.getX())) + ((entity.getZ() - this.mob.getZ()) * (entity.getZ() - this.mob.getZ()))) <= ((this.mob.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() + 0.5)
								* (this.mob.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() + 0.5))
						&& (entity.getY() - this.mob.getY()) >= -1 && (entity.getY() - this.mob.getY()) < (this.mob.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() * 3) && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.goalSelector.addGoal(6, new FloatGoal(this));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1));
	}

	protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
		this.spawnAtLocation(new ItemStack(GtsModItems.TOKI_DISK.get()));
	}

	@Override
	public SoundEvent getAmbientSound() {
		if ((this.getHealth() / this.getMaxHealth()) <= 0.5 && this.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() >= 3 && !GtsModVariables.MapVariables.get(this.level()).DisableStomachSound) {
			return BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.stomach"));
		} else {
			return SoundEvents.EMPTY;
		}
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.EMPTY;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		GtsHurtProcedure.execute(this, source.getEntity());
		Entity immediatesourceentity = source.getDirectEntity();
		return super.hurt(source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		GtsDeathProcedure.execute(this.level(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		GtsSpawnProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DatapassengerSlots", this.entityData.get(DATA_passengerSlots));
		compound.putString("DataAnimationDecision", this.entityData.get(DATA_AnimationDecision));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DatapassengerSlots"))
			this.entityData.set(DATA_passengerSlots, compound.getInt("DatapassengerSlots"));
		if (compound.contains("DataAnimationDecision"))
			this.entityData.set(DATA_AnimationDecision, compound.getString("DataAnimationDecision"));
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		TokiCelebrationProcedure.execute(this.level(), entity, this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		TokiTickUpdateProcedure.execute(this.level(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isAlive()) {
			if ((this.horizontalCollision || this.verticalCollision) && net.minecraftforge.event.EventHooks.canEntityGrief(this.level(), this) && !GtsModVariables.MapVariables.get(this.level()).DisableLeavesBreakWalk) {
				boolean flag = false;
				AABB aabb = this.getBoundingBox().inflate(1);
				for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					BlockState blockstate = this.level().getBlockState(blockpos);
					Block block = blockstate.getBlock();
					if (block instanceof LeavesBlock) {
						flag = this.level().destroyBlock(blockpos, true, this) || flag;
					}
				}
			}
		}
		this.updateSwingTime();
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(GtsModEntities.TOKI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return GtsSpawnConditionProcedure.execute(world, x, y, z);
		}, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	@Override
	public void applyRaidBuffs(ServerLevel serverLevel, int num, boolean logic) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 80);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 80);
		builder = builder.add(Attributes.STEP_HEIGHT, 2.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.getEntityData().get(TokiEntity.DATA_AnimationDecision).equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("gts.walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("gts.idle"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.getEntityData().get(TokiEntity.DATA_AnimationDecision)));
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 61) {
			this.remove(TokiEntity.RemovalReason.KILLED);
			this.dropExperience(this);
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 5, this::movementPredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float yaw) {
		int index = this.getPassengers().indexOf(entity);
		Vec3 offset = getPassengerSlotOffset(index);
		return offset;
	}

	@Override
	protected boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < this.getEntityData().get(TokiEntity.DATA_passengerSlots);
	}

	public Vec3 getPassengerSlotOffset(int index) {
		if (passengerSlots.containsKey(index)) {
			return passengerSlots.get(index);
		}
		return Vec3.ZERO;
	}

	public void setPassengerSlotOffset(int index, Vec3 offset) {
		passengerSlots.put(index, offset);
	}
}
