package com.wildlife.mod.entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
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
import java.util.Random;

public class ButterflyEntity extends Animal {
    private static final int[] VARIANT_COLORS = {0xFF6B35, 0x4ECDC4, 0xFFE66D, 0x95E1D3, 0xF38181};
    
    private float wingAngle = 0;
    private float wingSpeed = 0.15F;
    private Vec3 targetPos;
    private int flutterTimer = 0;
    
    public ButterflyEntity(EntityType<? extends ButterflyEntity> type, Level level) {
        super(type, level);
        this.setDimensions(Pose.STANDING, new Dimensions(0.3F, 0.2F, 0.3F));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 2.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.4D)
            .add(Attributes.FLYING_SPEED, 0.6D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ButterflyFlutterGoal(this));
        this.goalSelector.addGoal(2, new RandomFlyingGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 4.0F));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation
        wingAngle += wingSpeed;
        if (wingAngle > (float)Math.PI * 2) wingAngle -= (float)Math.PI * 2;
        
        // Flutter behavior
        if (flutterTimer > 0) {
            flutterTimer--;
            wingSpeed = 0.3F;
        } else {
            wingSpeed = 0.15F;
            if (random.nextInt(100) == 0) {
                flutterTimer = 20 + random.nextInt(30);
            }
        }
        
        // Stay above ground
        if (this.getY() < this.level().getSeaLevel() - 5) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05, 0));
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * wingSpeed) * 0.5F;
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
        return null; // Silent
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
        return null; // Cannot breed
    }
    
    static class ButterflyFlutterGoal extends Goal {
        private final ButterflyEntity butterfly;
        private int flutterTime = 0;
        
        ButterflyFlutterGoal(ButterflyEntity butterfly) {
            this.butterfly = butterfly;
        }
        
        @Override
        public boolean canUse() {
            return butterfly.random.nextInt(50) == 0;
        }
        
        @Override
        public void start() {
            flutterTime = 60 + butterfly.random.nextInt(80);
            butterfly.flutterTimer = flutterTime;
        }
        
        @Override
        public void tick() {
            if (flutterTime > 0) {
                flutterTime--;
                // Random flutter movement
                if (butterfly.random.nextInt(10) == 0) {
                    double dx = (butterfly.random.nextDouble() - 0.5) * 0.3;
                    double dy = (butterfly.random.nextDouble() - 0.5) * 0.2;
                    double dz = (butterfly.random.nextDouble() - 0.5) * 0.3;
                    butterfly.setDeltaMovement(butterfly.getDeltaMovement().add(dx, dy, dz));
                }
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return flutterTime > 0;
        }
    }
    
    static class RandomFlyingGoal extends Goal {
        private final ButterflyEntity butterfly;
        private Vec3 targetPos;
        
        RandomFlyingGoal(ButterflyEntity butterfly, double speed) {
            this.butterfly = butterfly;
        }
        
        @Override
        public boolean canUse() {
            return butterfly.random.nextInt(30) == 0 && butterfly.flutterTimer <= 0;
        }
        
        @Override
        public void start() {
            findNewTarget();
        }
        
        private void findNewTarget() {
            double x = butterfly.getX() + (butterfly.random.nextDouble() - 0.5) * 10;
            double y = butterfly.getY() + (butterfly.random.nextDouble() - 0.5) * 4;
            double z = butterfly.getZ() + (butterfly.random.nextDouble() - 0.5) * 10;
            
            // Keep within reasonable height
            y = Math.max(butterfly.level().getSeaLevel() - 2, Math.min(y, butterfly.level().getSeaLevel() + 10));
            
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                butterfly.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.6D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return targetPos != null && butterfly.distanceToSqr(targetPos) > 1.0 && butterfly.flutterTimer <= 0;
        }
    }
}
