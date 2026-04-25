package com.wildlife.mod.entity;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;
import net.minecraft.server.level.ServerLevel;

public class BoarEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_CHARGING = 
        SynchedEntityData.defineId(BoarEntity.class, EntityDataSerializers.BOOLEAN);
    
    private int chargeTimer = 0;
    private Entity chargeTarget = null;

    public BoarEntity(EntityType<? extends BoarEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 12.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.28D)
            .add(Attributes.FOLLOW_RANGE, 24.0D)
            .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CHARGING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BoarChargeGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.CARROT, Items.POTATO), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide()) {
            if (this.isCharging()) {
                this.chargeTimer--;
                if (this.chargeTimer <= 0) {
                    this.setCharging(false);
                }
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.CARROT) || stack.is(Items.POTATO) || stack.is(Items.BEETROOT);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.BOAR.create(level, net.minecraft.world.entity.EntitySpawnReason.BREEDING);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 0.8F);
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_CHARGING, charging);
    }

    public void startCharge(Entity target) {
        this.chargeTarget = target;
        this.setCharging(true);
        this.chargeTimer = 60;
    }

    public static class BoarChargeGoal extends Goal {
        private final BoarEntity boar;
        private int cooldown = 0;

        public BoarChargeGoal(BoarEntity boar) {
            this.boar = boar;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (boar.isBaby() || boar.isCharging() || cooldown > 0) return false;
            
            LivingEntity target = boar.getLastHurtByMob();
            if (target != null && target.distanceTo(boar) < 8.0D) {
                return true;
            }
            return false;
        }

        @Override
        public void start() {
            boar.startCharge(boar.getLastHurtByMob());
        }

        @Override
        public void tick() {
            if (boar.chargeTarget != null && boar.chargeTarget.isAlive()) {
                boar.getNavigation().moveTo(boar.chargeTarget, 1.8D);
                
                if (boar.distanceTo(boar.chargeTarget) < 1.5D) {
                    boar.chargeTarget.hurt(boar.damageSources().mobAttack(boar), 3.0F);
                    boar.setCharging(false);
                }
            }
        }

        @Override
        public void stop() {
            cooldown = 100;
        }
    }
}
