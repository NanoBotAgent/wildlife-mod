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

public class CrowEntity extends Animal {
    private float wingAngle = 0;
    private boolean isFlying = false;
    private int glideTimer = 0;
    
    public CrowEntity(EntityType<? extends CrowEntity> type, Level level) {
        super(type, level);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.28D)
            .add(Attributes.FLYING_SPEED, 0.55D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CrowGlideGoal(this));
        this.goalSelector.addGoal(2, new CrowFlyGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.25D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation - slower, more powerful flaps
        if (isFlying) {
            wingAngle += 0.35F;
        } else {
            wingAngle *= 0.85F;
        }
        
        // Glide timer
        if (glideTimer > 0) {
            glideTimer--;
        }
        
        // Random flight
        if (!isFlying && random.nextInt(350) == 0) {
            isFlying = true;
        }
        if (isFlying && random.nextInt(180) == 0) {
            isFlying = false;
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * (isFlying ? 0.35F : 0.1F)) * (isFlying ? 0.9F : 0.1F);
    }
    
    public boolean isFlying() {
        return isFlying;
    }
    
    public boolean isGliding() {
        return glideTimer > 0;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT; // Caw sound
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
        // Silent
    }
    
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }
    
    static class CrowGlideGoal extends Goal {
        private final CrowEntity crow;
        
        CrowGlideGoal(CrowEntity crow) {
            this.crow = crow;
        }
        
        @Override
        public boolean canUse() {
            return crow.isFlying && crow.random.nextInt(100) == 0;
        }
        
        @Override
        public void start() {
            crow.glideTimer = 60 + crow.random.nextInt(60);
        }
        
        @Override
        public boolean canContinueToUse() {
            return crow.glideTimer > 0;
        }
    }
    
    static class CrowFlyGoal extends Goal {
        private final CrowEntity crow;
        private Vec3 targetPos;
        
        CrowFlyGoal(CrowEntity crow) {
            this.crow = crow;
        }
        
        @Override
        public boolean canUse() {
            return crow.isFlying && crow.random.nextInt(40) == 0;
        }
        
        @Override
        public void start() {
            double x = crow.getX() + (crow.random.nextDouble() - 0.5) * 15;
            double y = crow.getY() + crow.random.nextDouble() * 8;
            double z = crow.getZ() + (crow.random.nextDouble() - 0.5) * 15;
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                crow.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.55D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return crow.isFlying && targetPos != null && crow.distanceToSqr(targetPos) > 1.0;
        }
    }
    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS);
    }
}
