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
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class FoxEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_SITTING = 
        SynchedEntityData.defineId(FoxEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_POUNCING = 
        SynchedEntityData.defineId(FoxEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = 
        SynchedEntityData.defineId(FoxEntity.class, EntityDataSerializers.INT);
    
    private float tailWag = 0;
    private float earTwist = 0;
    private int sitTimer = 0;
    private float pounceAnim = 0;

    public FoxEntity(EntityType<? extends FoxEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 10.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.3D)
            .add(Attributes.FOLLOW_RANGE, 32.0D)
            .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SITTING, false);
        builder.define(DATA_POUNCING, false);
        builder.define(DATA_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FoxPounceGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.SWEET_BERRIES, Items.GLOW_BERRIES), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new FoxSitGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
        // Tail wag animation
        if (this.moveControl.hasWanted()) {
            this.tailWag += 0.3F;
        } else {
            this.tailWag = Mth.lerp(0.1F, this.tailWag, 0);
        }
        
        // Ear twitch animation
        this.earTwist += 0.05F;
        
        // Pounce animation decay
        if (this.pounceAnim > 0) {
            this.pounceAnim -= 0.1F;
            if (this.pounceAnim <= 0) {
                this.setPouncing(false);
            }
        }
        
        // Sitting timer
        if (!this.level().isClientSide() && this.isSitting()) {
            this.sitTimer--;
            if (this.sitTimer <= 0) {
                this.setSitting(false);
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SWEET_BERRIES) || stack.is(Items.GLOW_BERRIES);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        FoxEntity baby = WildlifeEntities.FOX.create(level, net.minecraft.world.entity.EntitySpawnReason.BREEDING);
        if (baby != null) {
            baby.setVariant(this.random.nextInt(3));
        }
        return baby;
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
        this.playSound(SoundEvents.FOX_STEP.value(), 0.15F, 1.0F);
    }

    public boolean isSitting() {
        return this.entityData.get(DATA_SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(DATA_SITTING, sitting);
    }

    public boolean isPouncing() {
        return this.entityData.get(DATA_POUNCING);
    }

    public void setPouncing(boolean pouncing) {
        this.entityData.set(DATA_POUNCING, pouncing);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public float getTailWag(float partialTick) {
        return Mth.sin(this.tailWag + partialTick) * 0.5F;
    }

    public float getEarTwist(float partialTick) {
        return Mth.sin(this.earTwist + partialTick) * 0.1F;
    }

    public float getPounceAnim(float partialTick) {
        return this.pounceAnim;
    }

    static class FoxSitGoal extends Goal {
        private final FoxEntity fox;

        public FoxSitGoal(FoxEntity fox) {
            this.fox = fox;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return !fox.isSitting() && fox.random.nextInt(300) == 0 && !fox.moveControl.hasWanted();
        }

        @Override
        public void start() {
            fox.setSitting(true);
            fox.sitTimer = 100 + fox.random.nextInt(200);
        }

        @Override
        public boolean canContinueToUse() {
            return fox.isSitting() && fox.sitTimer > 0;
        }
    }

    static class FoxPounceGoal extends Goal {
        private final FoxEntity fox;
        private int cooldown = 0;

        public FoxPounceGoal(FoxEntity fox) {
            this.fox = fox;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (cooldown > 0) {
                cooldown--;
                return false;
            }
            return fox.onGround() && fox.random.nextInt(100) == 0;
        }

        @Override
        public void start() {
            double jumpX = fox.getX() + (fox.random.nextDouble() - 0.5) * 4;
            double jumpZ = fox.getZ() + (fox.random.nextDouble() - 0.5) * 4;
            fox.getNavigation().moveTo(jumpX, fox.getY(), jumpZ, 1.5D);
            
            // Pounce jump
            fox.setDeltaMovement(fox.getDeltaMovement().add(0, 0.4D, 0));
            fox.setPouncing(true);
            fox.pounceAnim = 1.0F;
            cooldown = 60;
        }
    }
}
