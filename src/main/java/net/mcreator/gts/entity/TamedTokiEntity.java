
package net.mcreator.gts.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.ForgeEventFactory;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
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

import net.mcreator.gts.procedures.TamedTokiTickUpdateProcedure;
import net.mcreator.gts.procedures.TamedTokiMobAggroCheckProcedure;
import net.mcreator.gts.procedures.TamedTokiAiConditionProcedure;
import net.mcreator.gts.procedures.GtsDeathProcedure;
import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModAttributes;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.GtsNavigation;
import net.mcreator.gts.GtsMoveHelper;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class TamedTokiEntity extends TamableAnimal implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TamedTokiEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TamedTokiEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TamedTokiEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_passengerSlots = SynchedEntityData.defineId(TamedTokiEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<String> DATA_AnimationDecision = SynchedEntityData.defineId(TamedTokiEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_Sleep = SynchedEntityData.defineId(TamedTokiEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private Map<Integer, Vec3> passengerSlots;

	public TamedTokiEntity(EntityType<TamedTokiEntity> type, Level world) {
		super(type, world);
		xpReward = 10;
		setNoAi(false);
		passengerSlots = new HashMap();
		setPersistenceRequired();
		if (!GtsModVariables.MapVariables.get(this.level()).DisableLeavesBreakWalk) {
			this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
		} ;
		moveControl = new GtsMoveHelper(this, (float) GtsModVariables.MapVariables.get(this.level()).Toki_TurnSpeed);
		noCulling = true;
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new GtsNavigation(this, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "asuma_toki_1");
		this.entityData.define(DATA_passengerSlots, 1);
		this.entityData.define(DATA_AnimationDecision, "empty");
		this.entityData.define(DATA_Sleep, false);
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
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiAiConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiAiConditionProcedure.execute(entity);
			}
		}.setAlertOthers());
		this.goalSelector.addGoal(2, new OwnerHurtByTargetGoal(this) {
			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiAiConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiAiConditionProcedure.execute(entity);
			}
		});
		this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this) {
			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiAiConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiAiConditionProcedure.execute(entity);
			}
		});
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Monster.class, false, false) {
			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiMobAggroCheckProcedure.execute(world, entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiMobAggroCheckProcedure.execute(world, entity);
			}
		});
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack()
						&& (((entity.getX() - this.mob.getX()) * (entity.getX() - this.mob.getX())) + ((entity.getZ() - this.mob.getZ()) * (entity.getZ() - this.mob.getZ()))) <= ((this.mob.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() + 0.5)
								* (this.mob.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() + 0.5))
						&& (entity.getY() - this.mob.getY()) >= -1 && (entity.getY() - this.mob.getY()) < (this.mob.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() * 3) && this.mob.getSensing().hasLineOfSight(entity);
			}

			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiAiConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiAiConditionProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, false) {
			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiAiConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiAiConditionProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(7, new FloatGoal(this));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiAiConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiAiConditionProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canUse() && TamedTokiAiConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TamedTokiEntity.this.getX();
				double y = TamedTokiEntity.this.getY();
				double z = TamedTokiEntity.this.getZ();
				Entity entity = TamedTokiEntity.this;
				Level world = TamedTokiEntity.this.level();
				return super.canContinueToUse() && TamedTokiAiConditionProcedure.execute(entity);
			}
		});
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		if ((this.getHealth() / this.getMaxHealth()) <= 0.7 && this.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() >= 3 && !GtsModVariables.MapVariables.get(this.level()).DisableStomachSound) {
			return BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts:toki.stomach"));
		} else {
			return SoundEvents.EMPTY;
		}
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		GtsDeathProcedure.execute(this.level(), this);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DatapassengerSlots", this.entityData.get(DATA_passengerSlots));
		compound.putString("DataAnimationDecision", this.entityData.get(DATA_AnimationDecision));
		compound.putBoolean("DataSleep", this.entityData.get(DATA_Sleep));
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
		if (compound.contains("DataSleep"))
			this.entityData.set(DATA_Sleep, compound.getBoolean("DataSleep"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		Item item = itemstack.getItem();
		if (itemstack.getItem() instanceof SpawnEggItem) {
			retval = super.mobInteract(sourceentity, hand);
		} else if (this.level().isClientSide()) {
			retval = (this.isTame() && this.isOwnedBy(sourceentity) || this.isFood(itemstack)) ? InteractionResult.sidedSuccess(this.level().isClientSide()) : InteractionResult.PASS;
		} else {
			if (this.isTame()) {
				if (this.isOwnedBy(sourceentity)) {
					if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
						this.usePlayerItem(sourceentity, hand, itemstack);
						FoodProperties foodproperties = itemstack.getFoodProperties(this);
						float nutrition = foodproperties != null ? (float) foodproperties.nutrition() : 1;
						this.heal(nutrition);
						retval = InteractionResult.sidedSuccess(this.level().isClientSide());
					} else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
						this.usePlayerItem(sourceentity, hand, itemstack);
						this.heal(4);
						retval = InteractionResult.sidedSuccess(this.level().isClientSide());
					} else {
						retval = super.mobInteract(sourceentity, hand);
					}
				}
			} else if (this.isFood(itemstack)) {
				this.usePlayerItem(sourceentity, hand, itemstack);
				if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, sourceentity)) {
					this.tame(sourceentity);
					this.level().broadcastEntityEvent(this, (byte) 7);
				} else {
					this.level().broadcastEntityEvent(this, (byte) 6);
				}
				this.setPersistenceRequired();
				retval = InteractionResult.sidedSuccess(this.level().isClientSide());
			} else {
				retval = super.mobInteract(sourceentity, hand);
				if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
					this.setPersistenceRequired();
			}
		}
		sourceentity.startRiding(this);
		return retval;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		TamedTokiTickUpdateProcedure.execute(this.level(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(1f);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		TamedTokiEntity retval = GtsModEntities.TAMED_TOKI.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of().contains(stack.getItem());
	}

	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle()) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				float forward = passenger.zza;
				float strafe = passenger.xxa;
				super.travel(new Vec3(strafe, 0, forward));
			}
			double d1 = this.getX() - this.xo;
			double d0 = this.getZ() - this.zo;
			float f1 = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4;
			if (f1 > 1.0F)
				f1 = 1.0F;
			this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4F);
			this.walkAnimation.position(this.walkAnimation.position() + this.walkAnimation.speed());
			this.calculateEntityAnimation(true);
			return;
		}
		super.travel(dir);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isAlive()) {
			if ((this.horizontalCollision || this.verticalCollision) && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && !GtsModVariables.MapVariables.get(this.level()).DisableLeavesBreakWalk) {
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

	public static void init(SpawnPlacementRegisterEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 100);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(net.minecraftforge.common.ForgeMod.STEP_HEIGHT_ADDITION.get(), 2.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.getEntityData().get(TamedTokiEntity.DATA_AnimationDecision).equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("gts.walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("gts.idle"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.getEntityData().get(TamedTokiEntity.DATA_AnimationDecision)));
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 45) {
			this.remove(TamedTokiEntity.RemovalReason.KILLED);
			this.dropExperience();
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
		return this.getPassengers().size() < this.getEntityData().get(TamedTokiEntity.DATA_passengerSlots);
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
