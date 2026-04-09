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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class MonkeyEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_IS_CLIMBING = 
        SynchedEntityData.defineId(MonkeyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_SITTING = 
        SynchedEntityData.defineId(MonkeyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = 
        SynchedEntityData.defineId(MonkeyEntity.class, EntityDataSerializers.INT);
    
    private int sitTimer = 0;
    private int climbCooldown = 0;
    private float tailSwing = 0;
    private float armSwing = 0;

    public MonkeyEntity(EntityType<? extends MonkeyEntity> type, Level level) {
        super(type, level);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 8.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.3D)
            .add(Attributes.FOLLOW_RANGE, 24.0D)
            .add(Attributes.JUMP_STRENGTH, 1.5D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_CLIMBING, false);
        builder.define(DATA_IS_SITTING, false);
        builder.define(DATA_VARIANT, this.random.nextInt(3));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MonkeyPanicGoal(this, 1.8D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.APPLE, Items.SWEET_BERRIES), false));
        this.goalSelector.addGoal(4, new MonkeyClimbTreeGoal(this));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new MonkeySitGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        this.tailSwing += 0.15F;
        if (this.isClimbing()) {
            this.armSwing += 0.3F;
        } else if (this.moveControl.hasWanted()) {
            this.armSwing += 0.15F;
        }
        
        if (!this.level().isClientSide()) {
            if (this.climbCooldown > 0) {
                this.climbCooldown--;
            }
            
            if (this.random.nextInt(400) == 0 && !this.isClimbing() && !this.isSitting()) {
                this.setSitting(true);
                this.sitTimer = 100 + this.random.nextInt(200);
            }
            
            if (this.isSitting()) {
                this.sitTimer--;
                if (this.sitTimer <= 0 || this.isInWater()) {
                    this.setSitting(false);
                }
            }
            
            if (this.isClimbing()) {
                if (!this.horizontalCollision && !this.verticalCollision) {
                    this.setClimbing(false);
                }
            }
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isClimbing() && !this.isInWater()) {
            this.setDeltaMovement(travelVector.x * 0.5D, travelVector.y * 0.8D, travelVector.z * 0.5D);
            super.travel(Vec3.ZERO);
        } else if (this.isSitting()) {
            this.setDeltaMovement(Vec3.ZERO);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.APPLE) || stack.is(Items.SWEET_BERRIES) || stack.is(Items.COCOA_BEANS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.MONKEY.spawn(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.FOX_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH.value();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CAT_STEP.value(), 0.15F, 1.2F);
    }

    public boolean isClimbing() {
        return this.entityData.get(DATA_IS_CLIMBING);
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(DATA_IS_CLIMBING, climbing);
    }

    public boolean isSitting() {
        return this.entityData.get(DATA_IS_SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(DATA_IS_SITTING, sitting);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public float getTailSwing(float partialTick) {
        return Mth.sin(this.tailSwing + partialTick) * 0.3F;
    }

    public float getArmSwing(float partialTick) {
        return Mth.sin(this.armSwing + partialTick) * 0.5F;
    }

@Override
public void addAdditionalSaveData(ValueOutput output) {
    super.addAdditionalSaveData(output);
    output.putBoolean("Climbing", this.isClimbing());
    output.putBoolean("Sitting", this.isSitting());
    output.putInt("Variant", this.getVariant());
}

@Override
public void readAdditionalSaveData(ValueInput input) {
    super.readAdditionalSaveData(input);
    this.setClimbing(input.getBoolean("Climbing", false));
    this.setSitting(input.getBoolean("Sitting", false));
    this.setVariant(input.getInt("Variant", 0));
}

@Override
public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
    MobSpawnType reason, @Nullable SpawnGroupData spawnData,
    @Nullable ValueInput dataTag) {
    if (spawnData == null) {
        spawnData = new AgeableMob.AgeableMobGroupData(0.1F);
    }
    return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
}

    public static class MonkeyClimbTreeGoal extends Goal {
        private final MonkeyEntity monkey;
        private BlockPos targetPos;

        public MonkeyClimbTreeGoal(MonkeyEntity monkey) {
            this.monkey = monkey;
            this.setRequiredVelocityMask(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (monkey.climbCooldown > 0 || monkey.isSitting()) return false;
            if (monkey.random.nextInt(100) != 0) return false;
            
            BlockPos pos = monkey.blockPosition();
            for (int y = 0; y < 10; y++) {
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos checkPos = pos.offset(x, y, z);
                        if (monkey.level().getBlockState(checkPos).getBlock() instanceof LeavesBlock) {
                            targetPos = checkPos;
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public void start() {
            if (targetPos != null) {
                monkey.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.2D);
            }
        }

        @Override
        public void tick() {
            if (monkey.horizontalCollision && monkey.climbCooldown <= 0) {
                monkey.setClimbing(true);
            }
            
            if (monkey.isClimbing()) {
                monkey.setDeltaMovement(0, 0.15D, 0);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return monkey.isClimbing() && monkey.random.nextInt(50) != 0;
        }

        @Override
        public void stop() {
            monkey.setClimbing(false);
            monkey.climbCooldown = 200;
        }
    }

    public static class MonkeySitGoal extends Goal {
        private final MonkeyEntity monkey;

        public MonkeySitGoal(MonkeyEntity monkey) {
            this.monkey = monkey;
        }

        @Override
        public boolean canUse() {
            return monkey.isSitting();
        }

        @Override
        public void start() {
            monkey.getNavigation().stop();
        }
    }

    public static class MonkeyPanicGoal extends PanicGoal {
        public MonkeyPanicGoal(MonkeyEntity monkey, double speed) {
            super(monkey, speed);
        }

        @Override
        public void tick() {
            super.tick();
            if (this.mob.horizontalCollision) {
                ((MonkeyEntity) this.mob).setClimbing(true);
            }
        }
    }
}
