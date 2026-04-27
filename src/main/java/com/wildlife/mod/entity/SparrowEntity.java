package com.wildlife.mod.entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SparrowEntity extends Animal {
    private float wingAngle = 0;
    private boolean isFlying = false;
    private int peckTimer = 0;
    private float targetWingAngle = 0;
    
    public SparrowEntity(EntityType<? extends SparrowEntity> type, Level level) {
        super(type, level);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 4.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FLYING_SPEED, 0.5D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SparrowPeckGoal(this));
        this.goalSelector.addGoal(2, new SparrowFlyGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.2D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation
        if (isFlying) {
            wingAngle += 0.5F;
        } else {
            wingAngle *= 0.8F;
        }
        
        // Peck animation
        if (peckTimer > 0) {
            peckTimer--;
        }
        
        // Random flight
        if (!isFlying && random.nextInt(300) == 0) {
            isFlying = true;
        }
        if (isFlying && random.nextInt(200) == 0) {
            isFlying = false;
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * (isFlying ? 0.5F : 0.1F)) * (isFlying ? 0.8F : 0.1F);
    }
    
    public boolean isFlying() {
        return isFlying;
    }
    
    public float getPeckAngle() {
        return peckTimer > 0 ? (float)Math.sin(peckTimer * 0.5F) * 0.3F : 0;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.parrot.ambient"));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.parrot.hurt"));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.parrot.death"));
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        // Silent hops
    }
    
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }
    
    static class SparrowPeckGoal extends Goal {
        private final SparrowEntity sparrow;
        private int peckTime = 0;
        
        SparrowPeckGoal(SparrowEntity sparrow) {
            this.sparrow = sparrow;
        }
        
        @Override
        public boolean canUse() {
            return !sparrow.isFlying && sparrow.random.nextInt(60) == 0;
        }
        
        @Override
        public void start() {
            peckTime = 10 + sparrow.random.nextInt(10);
            sparrow.peckTimer = peckTime;
        }
        
        @Override
        public boolean canContinueToUse() {
            return peckTime > 0;
        }
    }
    
    static class SparrowFlyGoal extends Goal {
        private final SparrowEntity sparrow;
        private Vec3 targetPos;
        
        SparrowFlyGoal(SparrowEntity sparrow) {
            this.sparrow = sparrow;
        }
        
        @Override
        public boolean canUse() {
            return sparrow.isFlying && sparrow.random.nextInt(20) == 0;
        }
        
        @Override
        public void start() {
            double x = sparrow.getX() + (sparrow.random.nextDouble() - 0.5) * 10;
            double y = sparrow.getY() + sparrow.random.nextDouble() * 5;
            double z = sparrow.getZ() + (sparrow.random.nextDouble() - 0.5) * 10;
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                sparrow.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.5D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return sparrow.isFlying && targetPos != null && sparrow.distanceToSqr(targetPos) > 1.0;
        }
    }
    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS);
    }
}
