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
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class OtterEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_SWIMMING = 
        SynchedEntityData.defineId(OtterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FLOATING = 
        SynchedEntityData.defineId(OtterEntity.class, EntityDataSerializers.BOOLEAN);
    
    private float tailWave = 0;
    private int floatTimer = 0;

    public OtterEntity(EntityType<? extends OtterEntity> type, Level level) {
        super(type, level);
        this.moveControl = new OtterMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 8.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWIMMING, false);
        builder.define(DATA_FLOATING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new OtterSwimGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.COD, Items.SALMON), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        this.tailWave += 0.2F;
        
        if (!this.level().isClientSide()) {
            if (this.isInWater()) {
                this.setSwimming(true);
                
                if (this.random.nextInt(200) == 0 && !this.isFloating()) {
                    this.setFloating(true);
                    this.floatTimer = 100 + this.random.nextInt(100);
                }
            } else {
                this.setSwimming(false);
                this.setFloating(false);
            }
            
            if (this.isFloating()) {
                this.floatTimer--;
                if (this.floatTimer <= 0) {
                    this.setFloating(false);
                }
            }
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.COD) || stack.is(Items.SALMON) || stack.is(Items.TROPICAL_FISH);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.OTTER.spawn(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.DOLPHIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.DOLPHIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CAT_STEP.value(), 0.15F, 1.2F);
    }

    public boolean isSwimming() {
        return this.entityData.get(DATA_SWIMMING);
    }

    public void setSwimming(boolean swimming) {
        this.entityData.set(DATA_SWIMMING, swimming);
    }

    public boolean isFloating() {
        return this.entityData.get(DATA_FLOATING);
    }

    public void setFloating(boolean floating) {
        this.entityData.set(DATA_FLOATING, floating);
    }

    public float getTailWave(float partialTick) {
        return Mth.sin(this.tailWave + partialTick) * 0.3F;
    }

    static class OtterMoveControl extends MoveControl {
        private final OtterEntity otter;

        public OtterMoveControl(OtterEntity otter) {
            super(otter);
            this.otter = otter;
        }

        @Override
        public void tick() {
            if (otter.isInWater()) {
                otter.setDeltaMovement(otter.getDeltaMovement().add(0, 0.005D, 0));
                
                if (this.operation == Operation.MOVE_TO) {
                    Vec3 target = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
                    Vec3 current = otter.position();
                    Vec3 direction = target.subtract(current).normalize();
                    
                    double speed = this.speedModifier * 0.8D;
                    otter.setDeltaMovement(direction.scale(speed));
                }
            } else {
                super.tick();
            }
        }
    }

    static class OtterSwimGoal extends Goal {
        private final OtterEntity otter;

        public OtterSwimGoal(OtterEntity otter) {
            this.otter = otter;
            this.setRequiredVelocityMask(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return otter.isInWater() && otter.random.nextInt(50) == 0;
        }

        @Override
        public void start() {
            double x = otter.getX() + (otter.random.nextDouble() - 0.5) * 20;
            double y = otter.getY() + (otter.random.nextDouble() - 0.5) * 5;
            double z = otter.getZ() + (otter.random.nextDouble() - 0.5) * 20;
            otter.getNavigation().moveTo(x, y, z, 1.0D);
        }
    }
}
