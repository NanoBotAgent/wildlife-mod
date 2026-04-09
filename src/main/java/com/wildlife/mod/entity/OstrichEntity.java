package com.wildlife.mod.entity;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

import org.jetbrains.annotations.Nullable;

public class OstrichEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_RUNNING = 
        SynchedEntityData.defineId(OstrichEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HEAD_DOWN = 
        SynchedEntityData.defineId(OstrichEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SADDLED = 
        SynchedEntityData.defineId(OstrichEntity.class, EntityDataSerializers.BOOLEAN);
    
    private float legSwing = 0;
    private float neckBob = 0;
    private int headDownTimer = 0;
    
    public OstrichEntity(EntityType<? extends OstrichEntity> type, Level level) {
        super(type, level);
        this.maxUpStep = 1.0F;
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 14.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.35D)
            .add(Attributes.FOLLOW_RANGE, 32.0D)
            .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }
    
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_RUNNING, false);
        builder.define(DATA_HEAD_DOWN, false);
        builder.define(DATA_SADDLED, false);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.8D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }
    
    @Override
    public void tick() {
        super.tick();
        
        if (this.moveControl.hasWanted()) {
            this.legSwing += 0.4F;
            this.neckBob += 0.2F;
            this.setRunning(true);
        } else {
            this.legSwing = Mth.lerp(0.1F, this.legSwing, 0);
            this.neckBob = Mth.lerp(0.1F, this.neckBob, 0);
            this.setRunning(false);
        }
        
        if (!this.level().isClientSide()) {
            if (this.isHeadDown()) {
                this.headDownTimer--;
                if (this.headDownTimer <= 0) {
                    this.setHeadDown(false);
                }
            }
            
            if (this.random.nextInt(400) == 0 && !this.isHeadDown()) {
                this.setHeadDown(true);
                this.headDownTimer = 60 + this.random.nextInt(60);
            }
        }
    }
    
    // === Rideable functionality ===
    
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT_SEEDS) || stack.is(Items.MELON_SEEDS) || stack.is(Items.PUMPKIN_SEEDS);
    }
    
@Override
public InteractionResult mobInteract(Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);

    // Saddle interaction
    if (stack.is(Items.SADDLE) && !this.isSaddled() && !this.isBaby()) {
        if (!this.level().isClientSide()) {
            this.setSaddled(true);
            stack.shrink(1);
            this.playSound(SoundEvents.HORSE_SADDLE.value(), 0.5F, 1.0F);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    // Mount if saddled
    if (this.isSaddled() && !this.isBaby()) {
        if (!this.level().isClientSide()) {
            player.startRiding(this);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    return super.mobInteract(player, hand);
}
    
    @Override
    public void travel(Vec3 travelVector) {
        if (this.isSaddled() && this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            // Player-controlled movement
            this.setYRot(player.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(player.getXRot() * 0.5F);
            
            float speed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
            if (this.isSprinting()) {
                speed *= 1.5F; // Sprint boost
            }
            
            if (player.zza > 0) {
                this.setSpeed(speed);
            } else if (player.zza < 0) {
                this.setSpeed(speed * 0.25F); // Slower backwards
            } else {
                this.setSpeed(0);
            }
            
            super.travel(new Vec3(0, travelVector.y, travelVector.z));
        } else {
            super.travel(travelVector);
        }
    }
    
@Override
public void positionRider(Entity passenger) {
    if (this.hasPassenger(passenger)) {
        // Position rider on back
        passenger.setYRot(this.getYRot());
        passenger.setYHeadRot(this.getYHeadRot());
        passenger.setPos(this.getX(), this.getY() + 1.8D, this.getZ());
    }
}
    
    @Override
    public boolean isControlledByLocalInstance() {
        return this.isVehicle() && this.getControllingPassenger() instanceof Player;
    }
    
    @Override
    public boolean isPushable() {
        return !this.isVehicle();
    }
    
    @Override
    protected boolean canRide(Entity entity) {
        return true;
    }
    
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.isSaddled() && this.getFirstPassenger() instanceof Player player) {
            return player;
        }
        return null;
    }
    
    public boolean isSaddled() {
        return this.entityData.get(DATA_SADDLED);
    }
    
    public void setSaddled(boolean saddled) {
        this.entityData.set(DATA_SADDLED, saddled);
    }
    
@Override
public void addAdditionalSaveData(ValueOutput output) {
super.addAdditionalSaveData(output);
output.putBoolean("Saddled", this.isSaddled());
}

@Override
public void readAdditionalSaveData(ValueInput input) {
super.readAdditionalSaveData(input);
this.setSaddled(input.getBoolean("Saddled", false));
}
    
    // === End rideable ===
    
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return WildlifeEntities.OSTRICH.spawn(level);
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.CHICKEN_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CAMEL_STEP, 0.15F, 1.0F);
    }
    
    public boolean isRunning() {
        return this.entityData.get(DATA_RUNNING);
    }
    
    public void setRunning(boolean running) {
        this.entityData.set(DATA_RUNNING, running);
    }
    
    public boolean isHeadDown() {
        return this.entityData.get(DATA_HEAD_DOWN);
    }
    
    public void setHeadDown(boolean headDown) {
        this.entityData.set(DATA_HEAD_DOWN, headDown);
    }
    
    public float getLegSwing(float partialTick) {
        return Mth.sin(this.legSwing + partialTick) * 0.5F;
    }
    
    public float getNeckBob(float partialTick) {
        return Mth.sin(this.neckBob + partialTick) * 0.1F;
    }
}
