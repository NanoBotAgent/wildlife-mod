package com.wildlife.mod.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class BadgerEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_DIGGING = 
        SynchedEntityData.defineId(BadgerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_AGGRESSIVE = 
        SynchedEntityData.defineId(BadgerEntity.class, EntityDataSerializers.BOOLEAN);
    
    private float digAnim = 0;
    private int digTimer = 0;
    private float snarlAnim = 0;

    public BadgerEntity(EntityType<? extends BadgerEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 12.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.22D)
            .add(Attributes.FOLLOW_RANGE, 20.0D)
            .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DIGGING, false);
        builder.define(DATA_AGGRESSIVE, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public boolean canUse() {
                return BadgerEntity.this.isAggressive() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new BadgerDigGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D, Ingredient.of(Items.SWEET_BERRIES, Items.CARROT), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, net.minecraft.world.entity.animal.Rabbit.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        
        // Dig animation
        if (this.isDigging()) {
            this.digAnim += 0.3F;
        } else {
            this.digAnim = Mth.lerp(0.1F, this.digAnim, 0);
        }
        
        // Snarl animation when aggressive
        if (this.isAggressive()) {
            this.snarlAnim = Mth.lerp(0.1F, this.snarlAnim, 1.0F);
        } else {
            this.snarlAnim = Mth.lerp(0.1F, this.snarlAnim, 0);
        }
        
        // Dig timer
        if (!this.level().isClientSide() && this.isDigging()) {
            this.digTimer--;
            if (this.digTimer <= 0) {
                this.setDigging(false);
            }
        }
        
        // Become aggressive when hurt
        if (this.getLastHurtByMob() != null && !this.isAggressive()) {
            this.setAggressive(true);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SWEET_BERRIES) || stack.is(Items.CARROT) || stack.is(Items.BEEF);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.BADGER.create(level, EntitySpawnReason.BREEDING);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isAggressive() ? SoundEvents.POLAR_BEAR_WARNING.value() : SoundEvents.POLAR_BEAR_AMBIENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.POLAR_BEAR_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.POLAR_BEAR_DEATH.value();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP.value(), 0.15F, 1.0F);
    }

    public boolean isDigging() {
        return this.entityData.get(DATA_DIGGING);
    }

    public void setDigging(boolean digging) {
        this.entityData.set(DATA_DIGGING, digging);
    }

    public boolean isAggressive() {
        return this.entityData.get(DATA_AGGRESSIVE);
    }

    public void setAggressive(boolean aggressive) {
        this.entityData.set(DATA_AGGRESSIVE, aggressive);
    }

    public float getDigAnim(float partialTick) {
        return Mth.sin(this.digAnim + partialTick) * 0.5F;
    }

    public float getSnarlAnim() {
        return this.snarlAnim;
    }

    static class BadgerDigGoal extends Goal {
        private final BadgerEntity badger;

        public BadgerDigGoal(BadgerEntity badger) {
            this.badger = badger;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return !badger.isDigging() && badger.onGround() && badger.random.nextInt(300) == 0;
        }

        @Override
        public void start() {
            badger.setDigging(true);
            badger.digTimer = 60 + badger.random.nextInt(40);
        }

        @Override
        public boolean canContinueToUse() {
            return badger.isDigging() && badger.digTimer > 0;
        }
    }
}
