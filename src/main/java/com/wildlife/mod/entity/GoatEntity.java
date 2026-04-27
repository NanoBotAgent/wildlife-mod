package com.wildlife.mod.entity;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
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
import java.util.EnumSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;

public class GoatEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_RAMMING = 
        SynchedEntityData.defineId(GoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_JUMPING = 
        SynchedEntityData.defineId(GoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = 
        SynchedEntityData.defineId(GoatEntity.class, EntityDataSerializers.INT);
    
    private float earFlick = 0;
    private float ramAnim = 0;
    private int ramCooldown = 0;
    private int ramChargeTime = 0;

    public GoatEntity(EntityType<? extends GoatEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 10.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 24.0D)
            .add(Attributes.ATTACK_DAMAGE, 2.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
            .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_RAMMING, false);
        builder.define(DATA_JUMPING, false);
        builder.define(DATA_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GoatRamGoal(this));
        this.goalSelector.addGoal(2, new GoatJumpGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.WHEAT, Items.HAY_BLOCK), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        // Ear flick animation
        this.earFlick += 0.08F;
        
        // Ram animation
        if (this.isRamming()) {
            this.ramAnim += 0.2F;
            this.ramChargeTime++;
        } else {
            this.ramAnim = Mth.lerp(0.1F, this.ramAnim, 0);
        }
        
        // Ram cooldown
        if (this.ramCooldown > 0) {
            this.ramCooldown--;
        }
        
        // Reset ramming after charge
        if (this.isRamming() && this.ramChargeTime > 20) {
            this.setRamming(false);
            this.ramChargeTime = 0;
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT) || stack.is(Items.HAY_BLOCK);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        GoatEntity baby = WildlifeEntities.GOAT.create(level, EntitySpawnReason.BREEDING);
        if (baby != null) {
            baby.setVariant(this.random.nextInt(2));
        }
        return baby;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.goat.ambient"));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.goat.hurt"));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.goat.death"));
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.sheep.step")), 0.15F, 1.0F);
    }

    public boolean isRamming() {
        return this.entityData.get(DATA_RAMMING);
    }

    public void setRamming(boolean ramming) {
        this.entityData.set(DATA_RAMMING, ramming);
    }

    public boolean isJumping() {
        return this.entityData.get(DATA_JUMPING);
    }

    public void setJumping(boolean jumping) {
        this.entityData.set(DATA_JUMPING, jumping);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public float getEarFlick(float partialTick) {
        return Mth.sin(this.earFlick + partialTick) * 0.1F;
    }

    public float getRamAnim(float partialTick) {
        return Mth.sin(this.ramAnim + partialTick) * 0.3F;
    }

    static class GoatRamGoal extends Goal {
        private final GoatEntity goat;
        private LivingEntity target;

        public GoatRamGoal(GoatEntity goat) {
            this.goat = goat;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (goat.ramCooldown > 0 || goat.isBaby()) return false;
            
            LivingEntity hurtBy = goat.getLastHurtByMob();
            if (hurtBy != null && hurtBy.distanceTo(goat) < 8.0D) {
                target = hurtBy;
                return true;
            }
            return false;
        }

        @Override
        public void start() {
            goat.setRamming(true);
            goat.ramChargeTime = 0;
        }

        @Override
        public void tick() {
            if (target != null && target.isAlive()) {
                goat.getLookControl().setLookAt(target);
                goat.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.5D);
                
                if (goat.distanceTo(target) < 1.5D && goat.ramChargeTime > 10) {
                    // Ram impact
                    target.hurt(goat.damageSources().mobAttack(goat), 2.0F);
                    target.knockback(0.8D, goat.getX() - target.getX(), goat.getZ() - target.getZ());
                    goat.setRamming(false);
                    goat.ramCooldown = 100;
                }
            }
        }

        @Override
        public void stop() {
            goat.setRamming(false);
            target = null;
        }
    }

    static class GoatJumpGoal extends Goal {
        private final GoatEntity goat;

        public GoatJumpGoal(GoatEntity goat) {
            this.goat = goat;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return goat.onGround() && goat.random.nextInt(100) == 0;
        }

        @Override
        public void start() {
            // Random high jump
            double jumpHeight = 0.6D + goat.random.nextDouble() * 0.4D;
            Vec3 motion = goat.getDeltaMovement();
            goat.setDeltaMovement(motion.x, jumpHeight, motion.z);
            goat.setJumping(true);
        }
    }
}
