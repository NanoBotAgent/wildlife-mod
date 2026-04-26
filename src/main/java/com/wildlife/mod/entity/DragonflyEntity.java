package com.wildlife.mod.entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

public class DragonflyEntity extends Animal {
    private float wingAngle = 0;
    private float bodyTilt = 0;
    private int hoverTimer = 0;
    
    public DragonflyEntity(EntityType<? extends DragonflyEntity> type, Level level) {
        super(type, level);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 3.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.5D)
            .add(Attributes.FLYING_SPEED, 0.7D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DragonflyHoverGoal(this));
        this.goalSelector.addGoal(2, new DragonflyDartGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Very fast wing animation
        wingAngle += 0.6F;
        if (wingAngle > (float)Math.PI * 2) wingAngle -= (float)Math.PI * 2;
        
        // Body tilt based on movement
        Vec3 motion = this.getDeltaMovement();
        bodyTilt = (float)(motion.y * 0.5);
        
        // Hover behavior
        if (hoverTimer > 0) {
            hoverTimer--;
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * 0.6F) * 0.7F;
    }
    
    public float getBodyTilt() {
        return bodyTilt;
    }
    
    public boolean isHovering() {
        return hoverTimer > 0;
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
        return SoundEvents.BEE_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH.value();
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
    
    static class DragonflyHoverGoal extends Goal {
        private final DragonflyEntity dragonfly;
        private int hoverTime = 0;
        
        DragonflyHoverGoal(DragonflyEntity dragonfly) {
            this.dragonfly = dragonfly;
        }
        
        @Override
        public boolean canUse() {
            return dragonfly.random.nextInt(60) == 0;
        }
        
        @Override
        public void start() {
            hoverTime = 30 + dragonfly.random.nextInt(50);
            dragonfly.hoverTimer = hoverTime;
        }
        
        @Override
        public void tick() {
            if (hoverTime > 0) {
                hoverTime--;
                // Stay in place with slight bobbing
                if (dragonfly.random.nextInt(8) == 0) {
                    double dy = (dragonfly.random.nextDouble() - 0.5) * 0.05;
                    dragonfly.setDeltaMovement(dragonfly.getDeltaMovement().multiply(0.8, 1, 0.8).add(0, dy, 0));
                }
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return hoverTime > 0;
        }
    }
    
    static class DragonflyDartGoal extends Goal {
        private final DragonflyEntity dragonfly;
        private Vec3 targetPos;
        private int dartTime = 0;
        
        DragonflyDartGoal(DragonflyEntity dragonfly) {
            this.dragonfly = dragonfly;
        }
        
        @Override
        public boolean canUse() {
            return dragonfly.random.nextInt(40) == 0 && dragonfly.hoverTimer <= 0;
        }
        
        @Override
        public void start() {
            dartTime = 20 + dragonfly.random.nextInt(40);
            findNewTarget();
        }
        
        private void findNewTarget() {
            double x = dragonfly.getX() + (dragonfly.random.nextDouble() - 0.5) * 12;
            double y = dragonfly.getY() + (dragonfly.random.nextDouble() - 0.5) * 4;
            double z = dragonfly.getZ() + (dragonfly.random.nextDouble() - 0.5) * 12;
            
            y = Math.max(dragonfly.level().getSeaLevel() - 3, Math.min(y, dragonfly.level().getSeaLevel() + 10));
            
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (dartTime > 0 && targetPos != null) {
                dartTime--;
                dragonfly.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.7D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return dartTime > 0 && targetPos != null && dragonfly.distanceToSqr(targetPos) > 1.0;
        }
    }
    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS);
    }
}
