package com.wildlife.mod.entity;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

public class BeeEntity extends Animal {
    private float wingAngle = 0;
    private float bobOffset = 0;
    private boolean isAngry = false;
    private int angerTimer = 0;
    
    public BeeEntity(EntityType<? extends BeeEntity> type, Level level) {
        super(type, level);
        this.setDimensions(Pose.STANDING, new Dimensions(0.3F, 0.3F, 0.3F));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 4.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.35D)
            .add(Attributes.FLYING_SPEED, 0.5D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BeeBuzzGoal(this));
        this.goalSelector.addGoal(2, new RandomFlyingGoal(this, 0.5D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation - faster than butterfly
        wingAngle += 0.4F;
        if (wingAngle > (float)Math.PI * 2) wingAngle -= (float)Math.PI * 2;
        
        // Bobbing motion
        bobOffset = (float)Math.sin(this.tickCount * 0.1F) * 0.05F;
        
        // Anger timer
        if (angerTimer > 0) {
            angerTimer--;
            if (angerTimer <= 0) {
                isAngry = false;
            }
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * 0.4F) * 0.6F;
    }
    
    public float getBobOffset() {
        return bobOffset;
    }
    
    public boolean isAngry() {
        return isAngry;
    }
    
    public void setAngry(boolean angry) {
        this.isAngry = angry;
        if (angry) {
            this.angerTimer = 200;
        }
    }
    
@Override
public boolean hurt(DamageSource source, float amount) {
    boolean wasAlive = this.isAlive();
    super.hurt(source, amount);
    if (this.isAlive() && source.getEntity() instanceof Player) {
        this.setAngry(true);
    }
    return this.hurtMarked;
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
        return SoundEvents.BEE_LOOP;
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
    
    static class BeeBuzzGoal extends Goal {
        private final BeeEntity bee;
        private int buzzTime = 0;
        
        BeeBuzzGoal(BeeEntity bee) {
            this.bee = bee;
        }
        
        @Override
        public boolean canUse() {
            return bee.random.nextInt(80) == 0;
        }
        
        @Override
        public void start() {
            buzzTime = 40 + bee.random.nextInt(60);
        }
        
        @Override
        public void tick() {
            if (buzzTime > 0) {
                buzzTime--;
                // Hover in place with slight movement
                if (bee.random.nextInt(5) == 0) {
                    double dx = (bee.random.nextDouble() - 0.5) * 0.1;
                    double dy = (bee.random.nextDouble() - 0.5) * 0.15;
                    double dz = (bee.random.nextDouble() - 0.5) * 0.1;
                    bee.setDeltaMovement(bee.getDeltaMovement().add(dx, dy, dz));
                }
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return buzzTime > 0;
        }
    }
    
    static class RandomFlyingGoal extends Goal {
        private final BeeEntity bee;
        private Vec3 targetPos;
        
        RandomFlyingGoal(BeeEntity bee, double speed) {
            this.bee = bee;
        }
        
        @Override
        public boolean canUse() {
            return bee.random.nextInt(40) == 0;
        }
        
        @Override
        public void start() {
            findFlower();
        }
        
        private void findFlower() {
            double x = bee.getX() + (bee.random.nextDouble() - 0.5) * 8;
            double y = bee.getY() + (bee.random.nextDouble() - 0.5) * 3;
            double z = bee.getZ() + (bee.random.nextDouble() - 0.5) * 8;
            
            y = Math.max(bee.level().getSeaLevel() - 2, Math.min(y, bee.level().getSeaLevel() + 8));
            
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                bee.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.5D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return targetPos != null && bee.distanceToSqr(targetPos) > 1.0;
        }
    }
}
