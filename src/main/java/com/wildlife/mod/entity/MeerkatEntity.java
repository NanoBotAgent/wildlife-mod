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

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class MeerkatEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_STANDING = 
        SynchedEntityData.defineId(MeerkatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_DIGGING = 
        SynchedEntityData.defineId(MeerkatEntity.class, EntityDataSerializers.BOOLEAN);
    
    private int standTimer = 0;
    private int digTimer = 0;
    private float tailWag = 0;

    public MeerkatEntity(EntityType<? extends MeerkatEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.3D)
            .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_STANDING, false);
        builder.define(DATA_DIGGING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeerkatStandGoal(this));
        this.goalSelector.addGoal(2, new MeerkatDigGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D, Ingredient.of(Items.SPIDER_EYE, Items.CARROT), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        this.tailWag += 0.15F;
        
        if (!this.level().isClientSide) {
            if (this.isStanding()) {
                this.standTimer--;
                if (this.standTimer <= 0) {
                    this.setStanding(false);
                }
            }
            
            if (this.isDigging()) {
                this.digTimer--;
                if (this.digTimer <= 0) {
                    this.setDigging(false);
                }
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SPIDER_EYE) || stack.is(Items.CARROT) || stack.is(Items.BEETROOT);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.MEERKAT.create(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.FOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CAT_STEP, 0.15F, 1.5F);
    }

    public boolean isStanding() {
        return this.entityData.get(DATA_STANDING);
    }

    public void setStanding(boolean standing) {
        this.entityData.set(DATA_STANDING, standing);
    }

    public boolean isDigging() {
        return this.entityData.get(DATA_DIGGING);
    }

    public void setDigging(boolean digging) {
        this.entityData.set(DATA_DIGGING, digging);
    }

    public float getTailWag(float partialTick) {
        return Mth.sin(this.tailWag + partialTick) * 0.2F;
    }

    static class MeerkatStandGoal extends Goal {
        private final MeerkatEntity meerkat;

        public MeerkatStandGoal(MeerkatEntity meerkat) {
            this.meerkat = meerkat;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return !meerkat.isStanding() && meerkat.random.nextInt(150) == 0;
        }

        @Override
        public void start() {
            meerkat.setStanding(true);
            meerkat.standTimer = 100 + meerkat.random.nextInt(100);
            meerkat.getNavigation().stop();
        }

        @Override
        public boolean canContinueToUse() {
            return meerkat.isStanding() && meerkat.standTimer > 0;
        }
    }

    static class MeerkatDigGoal extends Goal {
        private final MeerkatEntity meerkat;
        private int cooldown = 0;

        public MeerkatDigGoal(MeerkatEntity meerkat) {
            this.meerkat = meerkat;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (meerkat.isDigging() || cooldown > 0) return false;
            return meerkat.onGround() && meerkat.random.nextInt(200) == 0;
        }

        @Override
        public void start() {
            meerkat.setDigging(true);
            meerkat.digTimer = 40;
        }

        @Override
        public void stop() {
            cooldown = 100;
        }
    }
}
