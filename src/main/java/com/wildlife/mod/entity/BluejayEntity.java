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

public class BluejayEntity extends Animal {
    private float wingAngle = 0;
    private boolean isFlying = false;
    private int callTimer = 0;
    private int crestRaise = 0;
    
    public BluejayEntity(EntityType<? extends BluejayEntity> type, Level level) {
        super(type, level);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 5.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.24D)
            .add(Attributes.FLYING_SPEED, 0.5D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BluejayCallGoal(this));
        this.goalSelector.addGoal(2, new BluejayCrestGoal(this));
        this.goalSelector.addGoal(3, new BluejayFlyGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.2D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation
        if (isFlying) {
            wingAngle += 0.42F;
        } else {
            wingAngle *= 0.85F;
        }
        
        // Call timer
        if (callTimer > 0) {
            callTimer--;
        }
        
        // Crest animation
        if (crestRaise > 0) {
            crestRaise--;
        }
        
        // Random flight
        if (!isFlying && random.nextInt(360) == 0) {
            isFlying = true;
        }
        if (isFlying && random.nextInt(170) == 0) {
            isFlying = false;
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * (isFlying ? 0.42F : 0.1F)) * (isFlying ? 0.75F : 0.1F);
    }
    
    public boolean isFlying() {
        return isFlying;
    }
    
    public boolean isCalling() {
        return callTimer > 0;
    }
    
    public float getCrestAngle() {
        return crestRaise > 0 ? 0.25F : 0;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        if (isCalling()) {
            return SoundEvents.PARROT_AMBIENT;
        }
        return null;
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
    
    static class BluejayCallGoal extends Goal {
        private final BluejayEntity bluejay;
        
        BluejayCallGoal(BluejayEntity bluejay) {
            this.bluejay = bluejay;
        }
        
        @Override
        public boolean canUse() {
            return !bluejay.isFlying && bluejay.random.nextInt(180) == 0;
        }
        
        @Override
        public void start() {
            bluejay.callTimer = 20 + bluejay.random.nextInt(20);
        }
        
        @Override
        public boolean canContinueToUse() {
            return bluejay.callTimer > 0;
        }
    }
    
    static class BluejayCrestGoal extends Goal {
        private final BluejayEntity bluejay;
        
        BluejayCrestGoal(BluejayEntity bluejay) {
            this.bluejay = bluejay;
        }
        
        @Override
        public boolean canUse() {
            return !bluejay.isFlying && bluejay.random.nextInt(120) == 0;
        }
        
        @Override
        public void start() {
            bluejay.crestRaise = 25 + bluejay.random.nextInt(25);
        }
        
        @Override
        public boolean canContinueToUse() {
            return bluejay.crestRaise > 0;
        }
    }
    
    static class BluejayFlyGoal extends Goal {
        private final BluejayEntity bluejay;
        private Vec3 targetPos;
        
        BluejayFlyGoal(BluejayEntity bluejay) {
            this.bluejay = bluejay;
        }
        
        @Override
        public boolean canUse() {
            return bluejay.isFlying && bluejay.random.nextInt(30) == 0;
        }
        
        @Override
        public void start() {
            double x = bluejay.getX() + (bluejay.random.nextDouble() - 0.5) * 12;
            double y = bluejay.getY() + bluejay.random.nextDouble() * 6;
            double z = bluejay.getZ() + (bluejay.random.nextDouble() - 0.5) * 12;
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                bluejay.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.5D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return bluejay.isFlying && targetPos != null && bluejay.distanceToSqr(targetPos) > 1.0;
        }
    }
    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS);
    }
}
