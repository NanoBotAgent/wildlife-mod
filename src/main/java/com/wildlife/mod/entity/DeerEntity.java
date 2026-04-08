package com.wildlife.mod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Random;

public class DeerEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_IS_GRAZING = 
        SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_ANTLERS = 
        SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.BOOLEAN);
    
    private int grazingTime = 0;
    private float headRotX = 0;

    public DeerEntity(EntityType<? extends DeerEntity> type, Level level) {
        super(type, level);
        this.setHasAntlers(this.random.nextBoolean());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 10.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_GRAZING, false);
        builder.define(DATA_HAS_ANTLERS, true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide) {
            if (this.random.nextInt(500) == 0 && !this.isGrazing()) {
                this.setGrazing(true);
                this.grazingTime = 100 + this.random.nextInt(100);
            }
            
            if (this.isGrazing()) {
                this.grazingTime--;
                if (this.grazingTime <= 0) {
                    this.setGrazing(false);
                }
            }
        }
        
        if (this.isGrazing()) {
            this.headRotX = Mth.lerp(0.1F, this.headRotX, 0.5F);
        } else {
            this.headRotX = Mth.lerp(0.1F, this.headRotX, 0.0F);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        
        Player nearestPlayer = this.level().getNearestPlayer(this, 12.0D);
        if (nearestPlayer != null && !nearestPlayer.isCreative() && !nearestPlayer.isSpectator()) {
            Vec3 playerPos = nearestPlayer.position();
            Vec3 fleeDir = this.position().subtract(playerPos).normalize();
            this.getNavigation().moveTo(
                this.getX() + fleeDir.x * 15,
                this.getY(),
                this.getZ() + fleeDir.z * 15,
                1.5D
            );
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT) || stack.is(Items.APPLE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return ModEntities.DEER.get().create(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.COW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
    }

    public boolean isGrazing() {
        return this.entityData.get(DATA_IS_GRAZING);
    }

    public void setGrazing(boolean grazing) {
        this.entityData.set(DATA_IS_GRAZING, grazing);
    }

    public boolean hasAntlers() {
        return this.entityData.get(DATA_HAS_ANTLERS);
    }

    public void setHasAntlers(boolean antlers) {
        this.entityData.set(DATA_HAS_ANTLERS, antlers);
    }

    public float getHeadRotX() {
        return this.headRotX;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("HasAntlers", this.hasAntlers());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHasAntlers(compound.getBoolean("HasAntlers"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, 
                                        MobSpawnType reason, @Nullable SpawnGroupData spawnData,
                                        @Nullable CompoundTag dataTag) {
        if (spawnData == null) {
            spawnData = new AgeableMob.AgeableMobGroupData(0.2F);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }
}
