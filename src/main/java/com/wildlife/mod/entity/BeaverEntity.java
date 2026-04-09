package com.wildlife.mod.entity;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class BeaverEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_SWIMMING = 
        SynchedEntityData.defineId(BeaverEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_BUILDING = 
        SynchedEntityData.defineId(BeaverEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HOLDING_WOOD = 
        SynchedEntityData.defineId(BeaverEntity.class, EntityDataSerializers.BOOLEAN);
    
    private float tailSlap = 0;
    private int buildTimer = 0;
    private BlockPos buildTarget = null;

    public BeaverEntity(EntityType<? extends BeaverEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 10.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.22D)
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWIMMING, false);
        builder.define(DATA_BUILDING, false);
        builder.define(DATA_HOLDING_WOOD, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BeaverBuildGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.OAK_LOG, Items.BIRCH_LOG), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.isInWater()) {
            this.setSwimming(true);
        } else {
            this.setSwimming(false);
        }
        
        if (!this.level().isClientSide()) {
            if (this.isBuilding()) {
                this.buildTimer--;
                if (this.buildTimer <= 0) {
                    this.setBuilding(false);
                }
            }
            
            if (this.random.nextInt(300) == 0 && !this.isHoldingWood()) {
                this.setHoldingWood(true);
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.OAK_LOG) || stack.is(Items.BIRCH_LOG) || stack.is(Items.SPRUCE_LOG);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.BEAVER.create(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.POLAR_BEAR_AMBIENT.value();
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
        this.playSound(SoundEvents.CAT_STEP.value(), 0.15F, 0.8F);
    }

    public boolean isSwimming() {
        return this.entityData.get(DATA_SWIMMING);
    }

    public void setSwimming(boolean swimming) {
        this.entityData.set(DATA_SWIMMING, swimming);
    }

    public boolean isBuilding() {
        return this.entityData.get(DATA_BUILDING);
    }

    public void setBuilding(boolean building) {
        this.entityData.set(DATA_BUILDING, building);
    }

    public boolean isHoldingWood() {
        return this.entityData.get(DATA_HOLDING_WOOD);
    }

    public void setHoldingWood(boolean holding) {
        this.entityData.set(DATA_HOLDING_WOOD, holding);
    }

    public float getTailSlap(float partialTick) {
        return Mth.sin(this.tailSlap + partialTick) * 0.3F;
    }

    static class BeaverBuildGoal extends Goal {
        private final BeaverEntity beaver;
        private int cooldown = 0;

        public BeaverBuildGoal(BeaverEntity beaver) {
            this.beaver = beaver;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (beaver.isBaby() || beaver.isBuilding() || cooldown > 0) return false;
            if (!beaver.isHoldingWood()) return false;
            
            return beaver.isInWater() && beaver.random.nextInt(100) == 0;
        }

        @Override
        public void start() {
            BlockPos waterPos = beaver.blockPosition();
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos checkPos = waterPos.relative(dir);
                if (beaver.level().getBlockState(checkPos).isAir()) {
                    beaver.buildTarget = checkPos;
                    beaver.getNavigation().moveTo(checkPos, 1.0D);
                    return;
                }
            }
        }

        @Override
        public void tick() {
            if (beaver.buildTarget != null) {
                if (beaver.distanceToSqr(beaver.buildTarget.getCenter()) < 2.0D) {
                    beaver.setBuilding(true);
                    beaver.buildTimer = 60;
                    
                    if (beaver.level().getBlockState(beaver.buildTarget).isAir()) {
                        beaver.level().setBlock(beaver.buildTarget, Blocks.OAK_LOG.defaultBlockState(), 3);
                    }
                    beaver.setHoldingWood(false);
                    beaver.setBuilding(false);
                }
            }
        }

        @Override
        public void stop() {
            cooldown = 200;
            beaver.buildTarget = null;
        }
    }
}
