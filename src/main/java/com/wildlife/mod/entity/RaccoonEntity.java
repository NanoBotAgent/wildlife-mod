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

public class RaccoonEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_WASHING = 
        SynchedEntityData.defineId(RaccoonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STANDING = 
        SynchedEntityData.defineId(RaccoonEntity.class, EntityDataSerializers.BOOLEAN);
    
    private float tailRing = 0;
    private float washAnim = 0;
    private int washTimer = 0;
    private float standAnim = 0;

    public RaccoonEntity(EntityType<? extends RaccoonEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 8.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_WASHING, false);
        builder.define(DATA_STANDING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RaccoonWashGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.SWEET_BERRIES, Items.COD, Items.SALMON), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RaccoonStandGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        // Tail ring animation
        this.tailRing += 0.2F;
        
        // Wash animation
        if (this.isWashing()) {
            this.washAnim += 0.15F;
        } else {
            this.washAnim = Mth.lerp(0.1F, this.washAnim, 0);
        }
        
        // Stand animation
        if (this.isStanding()) {
            this.standAnim = Mth.lerp(0.1F, this.standAnim, 1.0F);
        } else {
            this.standAnim = Mth.lerp(0.1F, this.standAnim, 0);
        }
        
        // Wash timer
        if (!this.level().isClientSide() && this.isWashing()) {
            this.washTimer--;
            if (this.washTimer <= 0) {
                this.setWashing(false);
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SWEET_BERRIES) || stack.is(Items.COD) || stack.is(Items.SALMON);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.RACCOON.create(level, net.minecraft.world.entity.EntitySpawnReason.BREEDING);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT; // Close enough
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
        this.playSound(SoundEvents.CAT_STEP, 0.15F, 1.0F);
    }

    public boolean isWashing() {
        return this.entityData.get(DATA_WASHING);
    }

    public void setWashing(boolean washing) {
        this.entityData.set(DATA_WASHING, washing);
    }

    public boolean isStanding() {
        return this.entityData.get(DATA_STANDING);
    }

    public void setStanding(boolean standing) {
        this.entityData.set(DATA_STANDING, standing);
    }

    public float getTailRing(float partialTick) {
        return Mth.sin(this.tailRing + partialTick) * 0.1F;
    }

    public float getWashAnim(float partialTick) {
        return Mth.sin(this.washAnim + partialTick) * 0.3F;
    }

    public float getStandAnim() {
        return this.standAnim;
    }

    static class RaccoonWashGoal extends Goal {
        private final RaccoonEntity raccoon;

        public RaccoonWashGoal(RaccoonEntity raccoon) {
            this.raccoon = raccoon;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return !raccoon.isWashing() && raccoon.isInWater() && raccoon.random.nextInt(60) == 0;
        }

        @Override
        public void start() {
            raccoon.setWashing(true);
            raccoon.washTimer = 80 + raccoon.random.nextInt(60);
        }

        @Override
        public boolean canContinueToUse() {
            return raccoon.isWashing() && raccoon.isInWater();
        }
    }

    static class RaccoonStandGoal extends Goal {
        private final RaccoonEntity raccoon;
        private int standTimer = 0;

        public RaccoonStandGoal(RaccoonEntity raccoon) {
            this.raccoon = raccoon;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !raccoon.isStanding() && raccoon.random.nextInt(200) == 0 && !raccoon.moveControl.hasWanted();
        }

        @Override
        public void start() {
            raccoon.setStanding(true);
            standTimer = 40 + raccoon.random.nextInt(60);
        }

        @Override
        public void tick() {
            standTimer--;
            if (standTimer <= 0) {
                raccoon.setStanding(false);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return raccoon.isStanding() && standTimer > 0;
        }
    }
}
