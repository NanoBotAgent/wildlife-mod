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

public class RobinEntity extends Animal {
    private float wingAngle = 0;
    private boolean isFlying = false;
    private int hopTimer = 0;
    private int singTimer = 0;
    
public RobinEntity(EntityType<? extends RobinEntity> type, Level level) {
    super(type, level);
    // Entity dimensions are set via EntityType in 26.1
}
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 5.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.22D)
            .add(Attributes.FLYING_SPEED, 0.45D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RobinSingGoal(this));
        this.goalSelector.addGoal(2, new RobinHopGoal(this));
        this.goalSelector.addGoal(3, new RobinFlyGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.2D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation
        if (isFlying) {
            wingAngle += 0.45F;
        } else {
            wingAngle *= 0.85F;
        }
        
        // Hop animation
        if (hopTimer > 0) {
            hopTimer--;
        }
        
        // Sing timer
        if (singTimer > 0) {
            singTimer--;
        }
        
        // Random flight
        if (!isFlying && random.nextInt(400) == 0) {
            isFlying = true;
        }
        if (isFlying && random.nextInt(150) == 0) {
            isFlying = false;
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * (isFlying ? 0.45F : 0.1F)) * (isFlying ? 0.7F : 0.1F);
    }
    
    public boolean isFlying() {
        return isFlying;
    }
    
    public float getHopOffset() {
        return hopTimer > 0 ? (float)Math.sin(hopTimer * 0.3F) * 0.15F : 0;
    }
    
    public boolean isSinging() {
        return singTimer > 0;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        if (isSinging()) {
            return SoundEvents.PARROT_AMBIENT.value();
        }
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARROT_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH.value();
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

@Override
public boolean isFood(net.minecraft.world.item.ItemStack stack) {
    return stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS);
}
    
    static class RobinSingGoal extends Goal {
        private final RobinEntity robin;
        
        RobinSingGoal(RobinEntity robin) {
            this.robin = robin;
        }
        
        @Override
        public boolean canUse() {
            return !robin.isFlying && robin.random.nextInt(200) == 0;
        }
        
        @Override
        public void start() {
            robin.singTimer = 40 + robin.random.nextInt(40);
        }
        
        @Override
        public boolean canContinueToUse() {
            return robin.singTimer > 0;
        }
    }
    
    static class RobinHopGoal extends Goal {
        private final RobinEntity robin;
        
        RobinHopGoal(RobinEntity robin) {
            this.robin = robin;
        }
        
        @Override
        public boolean canUse() {
            return !robin.isFlying && robin.onGround() && robin.random.nextInt(40) == 0;
        }
        
        @Override
        public void start() {
            robin.hopTimer = 10;
            double dx = (robin.random.nextDouble() - 0.5) * 0.3;
            double dz = (robin.random.nextDouble() - 0.5) * 0.3;
            robin.setDeltaMovement(robin.getDeltaMovement().add(dx, 0.2, dz));
        }
        
        @Override
        public boolean canContinueToUse() {
            return robin.hopTimer > 0;
        }
    }
    
    static class RobinFlyGoal extends Goal {
        private final RobinEntity robin;
        private Vec3 targetPos;
        
        RobinFlyGoal(RobinEntity robin) {
            this.robin = robin;
        }
        
        @Override
        public boolean canUse() {
            return robin.isFlying && robin.random.nextInt(30) == 0;
        }
        
        @Override
        public void start() {
            double x = robin.getX() + (robin.random.nextDouble() - 0.5) * 12;
            double y = robin.getY() + robin.random.nextDouble() * 6;
            double z = robin.getZ() + (robin.random.nextDouble() - 0.5) * 12;
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                robin.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.45D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return robin.isFlying && targetPos != null && robin.distanceToSqr(targetPos) > 1.0;
        }
    }
}
