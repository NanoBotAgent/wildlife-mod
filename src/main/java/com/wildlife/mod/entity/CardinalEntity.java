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

public class CardinalEntity extends Animal {
    private float wingAngle = 0;
    private boolean isFlying = false;
    private int crestRaise = 0;
    
    public CardinalEntity(EntityType<? extends CardinalEntity> type, Level level) {
        super(type, level);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 5.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.23D)
            .add(Attributes.FLYING_SPEED, 0.48D);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CardinalCrestGoal(this));
        this.goalSelector.addGoal(2, new CardinalFlyGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.2D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation
        if (isFlying) {
            wingAngle += 0.4F;
        } else {
            wingAngle *= 0.85F;
        }
        
        // Crest animation
        if (crestRaise > 0) {
            crestRaise--;
        }
        
        // Random flight
        if (!isFlying && random.nextInt(380) == 0) {
            isFlying = true;
        }
        if (isFlying && random.nextInt(160) == 0) {
            isFlying = false;
        }
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * (isFlying ? 0.4F : 0.1F)) * (isFlying ? 0.75F : 0.1F);
    }
    
    public boolean isFlying() {
        return isFlying;
    }
    
    public float getCrestAngle() {
        return crestRaise > 0 ? 0.3F : 0;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT.value();
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
    
    static class CardinalCrestGoal extends Goal {
        private final CardinalEntity cardinal;
        
        CardinalCrestGoal(CardinalEntity cardinal) {
            this.cardinal = cardinal;
        }
        
        @Override
        public boolean canUse() {
            return !cardinal.isFlying && cardinal.random.nextInt(150) == 0;
        }
        
        @Override
        public void start() {
            cardinal.crestRaise = 30 + cardinal.random.nextInt(30);
        }
        
        @Override
        public boolean canContinueToUse() {
            return cardinal.crestRaise > 0;
        }
    }
    
    static class CardinalFlyGoal extends Goal {
        private final CardinalEntity cardinal;
        private Vec3 targetPos;
        
        CardinalFlyGoal(CardinalEntity cardinal) {
            this.cardinal = cardinal;
        }
        
        @Override
        public boolean canUse() {
            return cardinal.isFlying && cardinal.random.nextInt(35) == 0;
        }
        
        @Override
        public void start() {
            double x = cardinal.getX() + (cardinal.random.nextDouble() - 0.5) * 12;
            double y = cardinal.getY() + cardinal.random.nextDouble() * 6;
            double z = cardinal.getZ() + (cardinal.random.nextDouble() - 0.5) * 12;
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                cardinal.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.48D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return cardinal.isFlying && targetPos != null && cardinal.distanceToSqr(targetPos) > 1.0;
        }
    }
    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS);
    }
}
