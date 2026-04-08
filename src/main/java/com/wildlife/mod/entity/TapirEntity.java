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
import net.minecraft.util.Mth;
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

public class TapirEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_SWIMMING = 
        SynchedEntityData.defineId(TapirEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_WALLOWING = 
        SynchedEntityData.defineId(TapirEntity.class, EntityDataSerializers.BOOLEAN);
    
    private float earFlick = 0;
    private int wallowTimer = 0;

    public TapirEntity(EntityType<? extends TapirEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 16.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.22D)
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWIMMING, false);
        builder.define(DATA_WALLOWING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.APPLE, Items.CARROT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.random.nextInt(60) == 0) {
            this.earFlick = 1.0F;
        }
        this.earFlick = Mth.lerp(0.1F, this.earFlick, 0);
        
        if (this.isInWater()) {
            this.setSwimming(true);
            
            if (!this.level().isClientSide) {
                if (this.random.nextInt(200) == 0 && !this.isWallowing()) {
                    this.setWallowing(true);
                    this.wallowTimer = 100 + this.random.nextInt(100);
                }
            }
        } else {
            this.setSwimming(false);
            this.setWallowing(false);
        }
        
        if (!this.level().isClientSide && this.isWallowing()) {
            this.wallowTimer--;
            if (this.wallowTimer <= 0) {
                this.setWallowing(false);
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.APPLE) || stack.is(Items.CARROT) || stack.is(Items.POTATO);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.TAPIR.create(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIG_AMBIENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PIG_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH.value();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.COW_STEP.value(), 0.15F, 0.8F);
    }

    public boolean isSwimming() {
        return this.entityData.get(DATA_SWIMMING);
    }

    public void setSwimming(boolean swimming) {
        this.entityData.set(DATA_SWIMMING, swimming);
    }

    public boolean isWallowing() {
        return this.entityData.get(DATA_WALLOWING);
    }

    public void setWallowing(boolean wallowing) {
        this.entityData.set(DATA_WALLOWING, wallowing);
    }

    public float getEarFlick(float partialTick) {
        return Mth.sin(this.earFlick * (float)Math.PI + partialTick) * 0.2F;
    }
}
