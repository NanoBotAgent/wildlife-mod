package com.wildlife.mod.entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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

public class FireflyEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_GLOWING = 
        SynchedEntityData.defineId(FireflyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = 
        SynchedEntityData.defineId(FireflyEntity.class, EntityDataSerializers.INT);
    
    private float wingAngle = 0;
    private int glowTimer = 0;
    private int glowCycle = 0;
    
    public FireflyEntity(EntityType<? extends FireflyEntity> type, Level level) {
        super(type, level);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 2.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FLYING_SPEED, 0.35D);
    }
    
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_GLOWING, false);
        builder.define(DATA_VARIANT, 0);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FireflyGlowGoal(this));
        this.goalSelector.addGoal(2, new FireflyWanderGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 4.0F));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Wing animation
        wingAngle += 0.25F;
        if (wingAngle > (float)Math.PI * 2) wingAngle -= (float)Math.PI * 2;
        
        // Glow cycle
        glowCycle++;
        if (glowCycle > 40) glowCycle = 0;
        
        // Update glow state
        boolean shouldGlow = glowCycle < 20;
        this.entityData.set(DATA_GLOWING, shouldGlow);
    }
    
    public float getWingAngle(float partialTick) {
        return (float)Math.sin(wingAngle + partialTick * 0.25F) * 0.4F;
    }
    
    public boolean isGlowing() {
        return this.entityData.get(DATA_GLOWING);
    }
    
    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }
    
    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", getVariant());
    }
    
    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        if (tag.contains("Variant")) {
            this.entityData.set(DATA_VARIANT, input.getInt("Variant", 0));
        }
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
    
    static class FireflyGlowGoal extends Goal {
        private final FireflyEntity firefly;
        
        FireflyGlowGoal(FireflyEntity firefly) {
            this.firefly = firefly;
        }
        
        @Override
        public boolean canUse() {
            return firefly.random.nextInt(100) == 0;
        }
        
        @Override
        public void tick() {
            // Glow is handled in tick()
        }
    }
    
    static class FireflyWanderGoal extends Goal {
        private final FireflyEntity firefly;
        private Vec3 targetPos;
        
        FireflyWanderGoal(FireflyEntity firefly) {
            this.firefly = firefly;
        }
        
        @Override
        public boolean canUse() {
            return firefly.random.nextInt(50) == 0;
        }
        
        @Override
        public void start() {
            double x = firefly.getX() + (firefly.random.nextDouble() - 0.5) * 6;
            double y = firefly.getY() + (firefly.random.nextDouble() - 0.5) * 3;
            double z = firefly.getZ() + (firefly.random.nextDouble() - 0.5) * 6;
            
            y = Math.max(firefly.level().getSeaLevel() - 2, Math.min(y, firefly.level().getSeaLevel() + 6));
            
            targetPos = new Vec3(x, y, z);
        }
        
        @Override
        public void tick() {
            if (targetPos != null) {
                firefly.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 0.35D);
            }
        }
        
        @Override
        public boolean canContinueToUse() {
            return targetPos != null && firefly.distanceToSqr(targetPos) > 0.5;
        }
    }
    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS);
    }
}
