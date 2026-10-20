package net.mcreator.gts.entity;

import java.util.EnumSet;
import java.util.Map;
import java.util.HashMap;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraft.world.entity.monster.Monster;
import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.entity.FireflyEntity;
import net.mcreator.gts.init.GtsModEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.mcreator.gts.world.inventory.TamedGtsInteractionMenuMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkHooks;
import net.mcreator.gts.procedures.TokiSpitAttackProcedure;

public abstract class TokiVariantEntity extends TamableAnimal implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Map<Integer, Vec3> passengerSlots = new HashMap<>();

    protected TokiVariantEntity(EntityType<? extends TokiVariantEntity> type, Level level) {
        super(type, level);
        this.xpReward = 50;
        this.setNoAi(false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new Goal() {
            {
                this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
            }
            @Override public boolean canUse() { return TokiVariantEntity.this.isOrderedToSit(); }
            @Override public boolean canContinueToUse() { return TokiVariantEntity.this.isOrderedToSit(); }
            @Override public void start() { TokiVariantEntity.this.getNavigation().stop(); }
            @Override public void tick() { TokiVariantEntity.this.getNavigation().stop(); }
            @Override public void stop() { TokiVariantEntity.this.getNavigation().stop(); }
        });
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.35D, 4.0F, 2.0F, true) {
            @Override public boolean canUse() {
                return !TokiVariantEntity.this.isOrderedToSit() && TokiVariantEntity.this.getTarget() == null && super.canUse();
            }
            @Override public boolean canContinueToUse() {
                return !TokiVariantEntity.this.isOrderedToSit() && TokiVariantEntity.this.getTarget() == null && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.15D, true) {
            @Override public boolean canUse() { return !TokiVariantEntity.this.isOrderedToSit() && super.canUse(); }
            @Override public boolean canContinueToUse() { return !TokiVariantEntity.this.isOrderedToSit() && super.canContinueToUse(); }
        });
        this.goalSelector.addGoal(5, new Goal() {
            private int cooldown;
            @Override
            public boolean canUse() {
                if (this.cooldown > 0) this.cooldown--;
                LivingEntity target = TokiVariantEntity.this.getTarget();
                return !TokiVariantEntity.this.isOrderedToSit() && target != null && target.isAlive()
                    && TokiVariantEntity.this.distanceToSqr(target) >= 16.0D
                    && TokiVariantEntity.this.distanceToSqr(target) <= 400.0D
                    && this.cooldown <= 0;
            }
            @Override
            public boolean canContinueToUse() {
                return false;
            }
            @Override
            public void tick() {
                LivingEntity target = TokiVariantEntity.this.getTarget();
                if (target != null && target.isAlive() && !TokiVariantEntity.this.level().isClientSide()) {
                    TokiSpitAttackProcedure.execute(TokiVariantEntity.this.level(), target, TokiVariantEntity.this);
                    this.cooldown = 100;
                }
            }
            @Override
            public void start() {
                this.cooldown = 100;
            }
        });
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8D) {
            @Override public boolean canUse() { return !TokiVariantEntity.this.isOrderedToSit() && super.canUse(); }
            @Override public boolean canContinueToUse() { return !TokiVariantEntity.this.isOrderedToSit() && super.canContinueToUse(); }
        });
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return !TokiVariantEntity.this.isTame() && super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                return !TokiVariantEntity.this.isTame() && super.canContinueToUse();
            }
        });
        // Prioritize hostile monsters so tamed Ara/Firefly fight instead of repeatedly following their owner.
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Monster>(this, Monster.class, 10, true, false, target -> !isFriendlyTamedTarget(target) && !(target instanceof TamableAnimal tameTarget && tameTarget.isTame()) && !TokiVariantEntity.this.isAlliedTo(target)));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<Player>(this, Player.class, false) {
            @Override
            public boolean canUse() {
                return (TokiVariantEntity.this instanceof AraEntity || TokiVariantEntity.this instanceof FireflyEntity)
                    && !TokiVariantEntity.this.isTame() && super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                return (TokiVariantEntity.this instanceof AraEntity || TokiVariantEntity.this instanceof FireflyEntity)
                    && !TokiVariantEntity.this.isTame() && super.canContinueToUse();
            }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<TokiEntity>(this, TokiEntity.class, 10, true, false, target -> !isFriendlyTamedTarget(target)));
        // Tamed variants do not hunt friendly Candy; wild variants retain the original behavior.
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<CandyEntity>(this, CandyEntity.class, false) {
            @Override public boolean canUse() { return !TokiVariantEntity.this.isTame() && super.canUse(); }
            @Override public boolean canContinueToUse() { return !TokiVariantEntity.this.isTame() && super.canContinueToUse(); }
        });
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<TokiVariantEntity>(this, TokiVariantEntity.class, 10, true, false, target -> target instanceof TokiVariantEntity variant && !variant.isTame()) {
            @Override public boolean canUse() { return TokiVariantEntity.this.isTame() && super.canUse(); }
            @Override public boolean canContinueToUse() { return TokiVariantEntity.this.isTame() && super.canContinueToUse(); }
        });
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isTame()) {
            if (this.isOwnedBy(player) && stack.isEmpty()) {
                this.navigation.stop();
                this.setTarget(null);
                if (player.isShiftKeyDown()) {
                    player.getPersistentData().putString("targetedTamedGiantess", this.getUUID().toString());
                    if (player instanceof ServerPlayer serverPlayer) {
                        NetworkHooks.openScreen(serverPlayer,
                            new SimpleMenuProvider((id, inventory, owner) -> new TamedGtsInteractionMenuMenu(id, inventory, null), Component.literal("Companion Controls")),
                            buf -> buf.writeBlockPos(this.blockPosition()));
                    }
                } else {
                    this.setOrderedToSit(false);
                    if (!this.level().isClientSide() && !this.isVehicle()) {
                        player.startRiding(this);
                    }
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
            return super.mobInteract(player, hand);
        }
        if (!isTamingFood(stack)) return super.mobInteract(player, hand);
        if (!this.level().isClientSide()) {
            TokiVariantEntity companion = null;
            if (this instanceof AraEntity) companion = GtsModEntities.TAMED_ARA.get().create(this.level());
            else if (this instanceof FireflyEntity) companion = GtsModEntities.TAMED_FIREFLY.get().create(this.level());
            if (companion == null) return InteractionResult.PASS;
            companion.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            companion.tame(player);
            companion.applyTamedStats();
            companion.setOrderedToSit(false);
            companion.setPersistenceRequired();
            if (this.hasCustomName()) {
                companion.setCustomName(this.getCustomName());
                companion.setCustomNameVisible(this.isCustomNameVisible());
            }
            if (!player.getAbilities().instabuild) stack.shrink(1);
            this.level().addFreshEntity(companion);
            this.discard();
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    private boolean isTamingFood(ItemStack stack) {
        // Setiap varian hanya menerima item yang ditentukan, dan taming selalu berhasil.
        if (this instanceof AraEntity) return stack.is(Items.BREAD);
        if (this instanceof FireflyEntity) return stack.is(Items.DIAMOND);
        return false;
    }

    private void applyTamedStats() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(500.0D);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.36D);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(40.0D);
        this.getAttribute(Attributes.ARMOR).setBaseValue(20.0D);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(96.0D);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(4.0D);
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            // Spawned tamed variants bind to a nearby player, rather than treating that player as prey.
            if (!this.isTame() && (this instanceof TamedAraEntity || this instanceof TamedFireflyEntity)) {
                Player nearby = this.level().getNearestPlayer(this, 24.0D);
                if (nearby != null) {
                    this.tame(nearby);
                    this.setPersistenceRequired();
                    this.applyTamedStats();
                }
            }
            if (this.isTame() && this.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() < 40.0D) {
                this.applyTamedStats();
            }
            if (this.isTame() && this.isOrderedToSit()) {
                this.setTarget(null);
                this.getNavigation().stop();
                this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
                this.setSprinting(false);
            }
            if (this.isTame() && !this.isOrderedToSit()) {
                LivingEntity owner = this.getOwner();
                LivingEntity target = this.getTarget();
                if (target != null && (!target.isAlive()
                        || (owner != null && owner.isAlive() && this.distanceToSqr(owner) > 256.0D
                            && target.distanceToSqr(owner) > 256.0D))) {
                    this.setTarget(null);
                    this.getNavigation().stop();
                }
            }
        }
    }

    // Forge 1.20.1 does not declare getPassengerAttachmentPoint on Entity,
    // so keep this helper without @Override to avoid a compile error.
    public Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float yaw) {
        int index = this.getPassengers().indexOf(passenger);
        if (passengerSlots.containsKey(index)) {
            return passengerSlots.get(index);
        }
        // Keep riders attached relative to the actual entity height, including scaled variants.
        return new Vec3(0.0D, this.getBbHeight() * 0.88D, 0.0D);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        // Match Candy's default one-rider limit without changing movement controls.
        return this.getPassengers().size() < 1;
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

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isVehicle() && this.getPassengers().get(0) instanceof LivingEntity rider) {
            this.setYRot(rider.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(rider.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = rider.getYRot();
            this.yHeadRot = rider.getYRot();
            this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            super.travel(new Vec3(rider.xxa, 0.0D, rider.zza));
            this.calculateEntityAnimation(true);
            return;
        }
        super.travel(travelVector);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return (AgeableMob) this.getType().create(level);
    }

    private boolean isFriendlyTamedTarget(Entity target) {
        if (target == null || target == this || this.isAlliedTo(target)) return true;
        if (target instanceof TamableAnimal tameTarget && tameTarget.isTame()) return true;
        if (target instanceof net.minecraft.world.entity.OwnableEntity owned && owned.getOwner() != null) return true;
        if (target instanceof TamedTokiEntity || target instanceof TamedCandyEntity) return true;
        return target instanceof TokiVariantEntity variant && variant.isTame();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity attacker = source.getEntity();
        // Prevent hits from the owner and allied tamed companions.
        if (this.isTame() && attacker != null && this.isAlliedTo(attacker)) return false;
        if (this.isTame() && attacker instanceof TamableAnimal tameAttacker && tameAttacker.isTame()
                && (attacker instanceof TamedTokiEntity || attacker instanceof TamedCandyEntity || attacker instanceof TokiVariantEntity)) return false;
        return super.hurt(source, amount);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        if (this.isInWaterOrBubble()) return false;
        return super.causeFallDamage(fallDistance, multiplier, source);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (this.isTame() && isFriendlyTamedTarget(target)) return false;
        boolean hit = super.doHurtTarget(target);
        if (hit && !this.level().isClientSide() && target instanceof LivingEntity) {
            this.triggerAnim("movement", "attack");
        }
        return hit;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 180.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.30D)
            .add(Attributes.ATTACK_DAMAGE, 12.0D)
            .add(Attributes.ARMOR, 8.0D)
            .add(Attributes.FOLLOW_RANGE, 48.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
            .add(Attributes.ATTACK_KNOCKBACK, 1.5D);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 5, state -> {
            if (this.isOrderedToSit()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop(
                    (this instanceof FireflyEntity || this instanceof TamedFireflyEntity) ? "firefly.sitting" : "Ara.sit"));
            }
            if (state.isMoving()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("gts.walk"));
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop("gts.idle"));
        }).triggerableAnim("attack", RawAnimation.begin().thenPlay(
            (this instanceof FireflyEntity || this instanceof TamedFireflyEntity) ? "firefly.swordswing"
            : (this instanceof AraEntity || this instanceof TamedAraEntity) ? "Ara.WaistAtk1" : "gts.walk")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public abstract String getVariantModelPath();
    public abstract String getVariantAnimationPath();
    public abstract String getVariantTexturePath();
}
