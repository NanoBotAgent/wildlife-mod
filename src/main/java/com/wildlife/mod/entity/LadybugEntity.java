package com.wildlife.mod.entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

public class LadybugEntity extends Animal {
    private float wingAngle = 0;
    private int walkTimer = 0;
    private boolean isFlying = false;
    private BlockPos targetLeaf;
    
    public LadybugEntity(EntityType<? extends LadybugEntity> type, Level level) {
        super(type, level);
        this.setDimensions(Pose.STANDING, new Dimensions(0.25F, 0.15F, 0.25F));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 3.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.15D)
            .add(Attributes.FLYING_SPEED, 0.4D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LadybugWalkGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.1D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation when flying
        if (isFlying) {
            wingAngle += 0.35F;
            if (wingAngle > (float)Math.PI * 2) wingAngle -= (float)Math.PI * 2;
        }
        
        // Walking animation
        if (!isFlying && walkTimer > 0) {
            walkTimer--;
        }
        
        // Random flight
        if (random.nextInt(200) == 0 && !isFlying) {
            isFlying = true;
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.2, 0));
        }
        
        if (isFlying && random.nextInt(100) == 0) {
            isFlying = false;
        }
    }
    
    public float getWingAngle(float partialTick) {
        if (isFlying) {
            return (float)Math.sin(wingAngle + partialTick * 0.35F) * 0.5F;
        }
        return 0;
    }
    
    public boolean isFlying() {
        return isFlying;
    }
    
    @Override
    public boolean isPushable() {
        return false;
    }
    
    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }
    
    @Override
    public boolean canBeLeashed() {
        return false;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BEE_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        // Silent
    }
    
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }
    
    static class LadybugWalkGoal extends Goal {
        private final LadybugEntity ladybug;
        private int walkTime = 0;
        
        LadybugWalkGoal(LadybugEntity ladybug) {
            this.ladybug = ladybug;
        }
        
        @Override
        public boolean canUse() {
            return !ladybug.isFlying && ladybug.random.nextInt(30) == 0;
        }
        
        @Override
        public void start() {
            walkTime = 60 + ladybug.random.nextInt(100);
            ladybug.walkTimer = walkTime;
        }
        
        @Override
        public void tick() {
            if (walkTime > 0) {
                walkTime--;
                // Small random movements
                if (ladybug.random.nextInt(15) == 0) {
                    double dx = (ladybug.random.nextDouble() - 0.5) * 0.05;
                    double dz = (ladybug.random.nextDouble() - 0.5) * 0.05;
                    ladybug.setDeltaMovement(ladybug.getDeltaMovement().add(dx, 0, dz));
                }
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return walkTime > 0 && !ladybug.isFlying;
        }
    }
}
