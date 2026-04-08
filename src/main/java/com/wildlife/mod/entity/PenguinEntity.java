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
import net.minecraft.world.entity.ai.control.MoveControl;
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
import java.util.EnumSet;

public class PenguinEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_SWIMMING = 
        SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SLIDING = 
        SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_WADDLING = 
        SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    
    private float wingFlap = 0;
    private float waddleAnim = 0;
    private int slideTimer = 0;

    public PenguinEntity(EntityType<? extends PenguinEntity> type, Level level) {
        super(type, level);
        this.moveControl = new PenguinMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 8.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.FLYING_SPEED, 0.6D) // Swimming speed
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWIMMING, false);
        builder.define(DATA_SLIDING, false);
        builder.define(DATA_WADDLING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PenguinSlideGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 0.8D, 20));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        // Update swimming state
        this.setSwimming(this.isInWater());
        
        // Wing flap animation (faster when swimming)
        if (this.isSwimming()) {
            this.wingFlap += 0.4F;
        } else if (this.moveControl.hasWanted()) {
            this.wingFlap += 0.15F;
        } else {
            this.wingFlap = Mth.lerp(0.1F, this.wingFlap, 0);
        }
        
        // Waddle animation on land
        if (!this.isSwimming() && this.moveControl.hasWanted()) {
            this.waddleAnim += 0.25F;
            this.setWaddling(true);
        } else {
            this.waddleAnim = Mth.lerp(0.1F, this.waddleAnim, 0);
            this.setWaddling(false);
        }
        
        // Slide timer
        if (!this.level().isClientSide() && this.isSliding()) {
            this.slideTimer--;
            if (this.slideTimer <= 0) {
                this.setSliding(false);
            }
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isSwimming()) {
            this.moveRelative(0.01F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.COD) || stack.is(Items.SALMON) || stack.is(Items.TROPICAL_FISH);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.PENGUIN.spawn(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    public boolean isSwimming() {
        return this.entityData.get(DATA_SWIMMING);
    }

    public void setSwimming(boolean swimming) {
        this.entityData.set(DATA_SWIMMING, swimming);
    }

    public boolean isSliding() {
        return this.entityData.get(DATA_SLIDING);
    }

    public void setSliding(boolean sliding) {
        this.entityData.set(DATA_SLIDING, sliding);
    }

    public boolean isWaddling() {
        return this.entityData.get(DATA_WADDLING);
    }

    public void setWaddling(boolean waddling) {
        this.entityData.set(DATA_WADDLING, waddling);
    }

    public float getWingFlap(float partialTick) {
        return Mth.sin(this.wingFlap + partialTick) * 0.5F;
    }

    public float getWaddleAnim(float partialTick) {
        return Mth.sin(this.waddleAnim + partialTick) * 0.15F;
    }

    static class PenguinMoveControl extends MoveControl {
        private final PenguinEntity penguin;

        public PenguinMoveControl(PenguinEntity penguin) {
            super(penguin);
            this.penguin = penguin;
        }

        @Override
        public void tick() {
            if (penguin.isSwimming()) {
                if (this.operation == Operation.MOVE_TO) {
                    Vec3 target = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
                    Vec3 current = penguin.position();
                    Vec3 direction = target.subtract(current).normalize();
                    
                    penguin.setDeltaMovement(direction.scale(this.speedModifier));
                }
            } else {
                super.tick();
            }
        }
    }

    static class PenguinSlideGoal extends Goal {
        private final PenguinEntity penguin;

        public PenguinSlideGoal(PenguinEntity penguin) {
            this.penguin = penguin;
            this.setRequiredVelocityMask(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            // Slide on ice/snow blocks
            return !penguin.isSwimming() && !penguin.isSliding() && 
                   penguin.onGround() && penguin.random.nextInt(200) == 0;
        }

        @Override
        public void start() {
            penguin.setSliding(true);
            penguin.slideTimer = 40 + penguin.random.nextInt(30);
            
            // Slide forward
            Vec3 look = penguin.getLookAngle();
            penguin.setDeltaMovement(look.x * 0.5D, 0, look.z * 0.5D);
        }

        @Override
        public boolean canContinueToUse() {
            return penguin.isSliding() && penguin.slideTimer > 0;
        }
    }
}
