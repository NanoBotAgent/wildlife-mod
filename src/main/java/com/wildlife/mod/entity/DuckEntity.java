package com.wildlife.mod.entity;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

public class DuckEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_SWIMMING = 
        SynchedEntityData.defineId(DuckEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FLOATING = 
        SynchedEntityData.defineId(DuckEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = 
        SynchedEntityData.defineId(DuckEntity.class, EntityDataSerializers.INT);
    
    private float wingFlap = 0;
    private float quackAnim = 0;
    private int floatTimer = 0;

    public DuckEntity(EntityType<? extends DuckEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 4.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWIMMING, false);
        builder.define(DATA_FLOATING, false);
        builder.define(DATA_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.1D, Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.isInWater()) {
            this.setSwimming(true);
            this.wingFlap = Mth.lerp(0.1F, this.wingFlap, 0);
            
            if (this.random.nextInt(300) == 0 && !this.isFloating()) {
                this.setFloating(true);
                this.floatTimer = 100 + this.random.nextInt(100);
            }
        } else {
            this.setSwimming(false);
            this.setFloating(false);
            
            if (this.moveControl.hasWanted()) {
                this.wingFlap += 0.3F;
            } else {
                this.wingFlap = Mth.lerp(0.1F, this.wingFlap, 0);
            }
        }
        
        if (this.isFloating()) {
            this.floatTimer--;
            if (this.floatTimer <= 0) {
                this.setFloating(false);
            }
        }
        
        if (this.random.nextInt(100) == 0) {
            this.quackAnim = 1.0F;
        }
        this.quackAnim = Mth.lerp(0.1F, this.quackAnim, 0);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isInWater()) {
            this.moveRelative(0.01F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT_SEEDS) || stack.is(Items.MELON_SEEDS) || stack.is(Items.PUMPKIN_SEEDS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.DUCK.create(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CAT_STEP, 0.15F, 1.5F);
    }

    public boolean isSwimming() {
        return this.entityData.get(DATA_SWIMMING);
    }

    public void setSwimming(boolean swimming) {
        this.entityData.set(DATA_SWIMMING, swimming);
    }

    public boolean isFloating() {
        return this.entityData.get(DATA_FLOATING);
    }

    public void setFloating(boolean floating) {
        this.entityData.set(DATA_FLOATING, floating);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public float getWingFlap(float partialTick) {
        return Mth.sin(this.wingFlap + partialTick) * 0.5F;
    }

    public float getQuackAnim(float partialTick) {
        return Mth.sin(this.quackAnim * (float)Math.PI + partialTick) * 0.1F;
    }
}
