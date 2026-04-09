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

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class ToucanEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_FLYING = 
        SynchedEntityData.defineId(ToucanEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PERCHED = 
        SynchedEntityData.defineId(ToucanEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = 
        SynchedEntityData.defineId(ToucanEntity.class, EntityDataSerializers.INT);
    
    private float wingFlap = 0;
    private float beakOpen = 0;
    private int perchTimer = 0;

    public ToucanEntity(EntityType<? extends ToucanEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.FLYING_SPEED, 0.3D)
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLYING, false);
        builder.define(DATA_PERCHED, false);
        builder.define(DATA_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ToucanFlyGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.SWEET_BERRIES, Items.MELON), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.isFlying()) {
            this.wingFlap += 0.4F;
        } else {
            this.wingFlap = Mth.lerp(0.1F, this.wingFlap, 0);
        }
        
        if (this.random.nextInt(80) == 0) {
            this.beakOpen = 1.0F;
        }
        this.beakOpen = Mth.lerp(0.1F, this.beakOpen, 0);
        
        if (!this.level().isClientSide()) {
            if (this.isPerched()) {
                this.perchTimer--;
                if (this.perchTimer <= 0) {
                    this.setPerched(false);
                }
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SWEET_BERRIES) || stack.is(Items.MELON) || stack.is(Items.APPLE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.TOUCAN.create(level, net.minecraft.world.entity.EntitySpawnReason.BREEDING);
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
        this.playSound(SoundEvents.CAT_STEP.value(), 0.15F, 1.0F);
    }

    public boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(DATA_FLYING, flying);
    }

    public boolean isPerched() {
        return this.entityData.get(DATA_PERCHED);
    }

    public void setPerched(boolean perched) {
        this.entityData.set(DATA_PERCHED, perched);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public float getWingFlap(float partialTick) {
        return Mth.sin(this.wingFlap + partialTick) * 0.5F;
    }

    public float getBeakOpen(float partialTick) {
        return this.beakOpen;
    }

    static class ToucanFlyGoal extends Goal {
        private final ToucanEntity toucan;
        private BlockPos targetPos;

        public ToucanFlyGoal(ToucanEntity toucan) {
            this.toucan = toucan;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return toucan.random.nextInt(100) == 0;
        }

        @Override
        public void start() {
            toucan.setFlying(true);
            
            for (int y = 0; y < 15; y++) {
                for (int x = -5; x <= 5; x++) {
for (int z = -5; z <= 5; z++) {
            BlockPos checkPos = toucan.blockPosition().offset(x, y, z);
            if (toucan.level().getBlockState(checkPos).getBlock() instanceof LeavesBlock) {
                targetPos = checkPos;
                toucan.getNavigation().moveTo(checkPos.getX(), checkPos.getY(), checkPos.getZ(), 1.0D);
                return;
            }
        }
    }
}
            
            double x = toucan.getX() + (toucan.random.nextDouble() - 0.5) * 20;
            double y = toucan.getY() + toucan.random.nextDouble() * 8 + 3;
            double z = toucan.getZ() + (toucan.random.nextDouble() - 0.5) * 20;
            toucan.getNavigation().moveTo(x, y, z, 1.0D);
        }

        @Override
        public void tick() {
            if (targetPos != null && toucan.distanceToSqr(targetPos.getCenter()) < 2.0D) {
                toucan.setFlying(false);
                toucan.setPerched(true);
                toucan.perchTimer = 100 + toucan.random.nextInt(100);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return toucan.isFlying() && toucan.random.nextInt(50) != 0;
        }
    }
}
