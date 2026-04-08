package com.wildlife.mod.entity;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;
import java.util.List;

public class OwlEntity extends Animal {
    private static final EntityDataAccessor<Boolean> DATA_FLYING =
        SynchedEntityData.defineId(OwlEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PERCHED =
        SynchedEntityData.defineId(OwlEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT =
        SynchedEntityData.defineId(OwlEntity.class, EntityDataSerializers.INT);

    private float wingFlap = 0;
    private int perchTimer = 0;
    private BlockPos perchTarget = null;

    public OwlEntity(EntityType<? extends OwlEntity> type, Level level) {
        super(type, level);
        this.moveControl = new OwlMoveControl(this);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.FLYING_SPEED, 0.4D)
            .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLYING, true);
        builder.define(DATA_PERCHED, false);
        builder.define(DATA_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new OwlPerchGoal(this));
        this.goalSelector.addGoal(1, new OwlFlyGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.RABBIT, Items.CHICKEN), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isFlying()) {
            this.wingFlap += 0.5F;
        } else {
            this.wingFlap = Mth.lerp(0.1F, this.wingFlap, 0);
        }

        if (!this.level().isClientSide) {
            if (this.isPerched()) {
                this.perchTimer--;
                if (this.perchTimer <= 0) {
                    this.setPerched(false);
                    this.setFlying(true);
                }
            }

            if (this.isFlying() && this.onGround()) {
                this.setFlying(false);
            }
            
            // Night Vision Aura - gives nearby players night vision at night
            if (this.level().isNight()) {
                giveNightVisionToNearbyPlayers();
            }
        }
    }
    
    private void giveNightVisionToNearbyPlayers() {
        List<Player> nearbyPlayers = this.level().getEntitiesOfClass(
            Player.class, 
            new AABB(this.blockPosition()).inflate(8.0D)
        );
        
        for (Player player : nearbyPlayers) {
            // Give night vision for 5 seconds, refreshed each tick while in range
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0, true, false));
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isFlying() && !this.onGround()) {
            this.moveRelative(0.01F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.RABBIT) || stack.is(Items.CHICKEN) || stack.is(Items.BEEF);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return ModEntities.OWL.get().create(level);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CAT_STEP, 0.15F, 1.0F);
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

    static class OwlMoveControl extends MoveControl {
        private final OwlEntity owl;

        public OwlMoveControl(OwlEntity owl) {
            super(owl);
            this.owl = owl;
        }

        @Override
        public void tick() {
            if (owl.isFlying()) {
                if (this.operation == Operation.MOVE_TO) {
                    Vec3 target = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
                    Vec3 current = owl.position();
                    Vec3 direction = target.subtract(current).normalize();

                    double speed = this.speedModifier;
                    owl.setDeltaMovement(direction.scale(speed));

                    if (direction.y > 0.1) {
                        owl.setDeltaMovement(owl.getDeltaMovement().add(0, 0.05D, 0));
                    }
                }
            } else {
                super.tick();
            }
        }
    }

    static class OwlFlyGoal extends Goal {
        private final OwlEntity owl;

        public OwlFlyGoal(OwlEntity owl) {
            this.owl = owl;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !owl.isPerched() && owl.random.nextInt(30) == 0;
        }

        @Override
        public void start() {
            owl.setFlying(true);
            double x = owl.getX() + (owl.random.nextDouble() - 0.5) * 30;
            double y = owl.getY() + owl.random.nextDouble() * 10 + 5;
            double z = owl.getZ() + (owl.random.nextDouble() - 0.5) * 30;
            owl.getNavigation().moveTo(x, y, z, 1.0D);
        }
    }

    static class OwlPerchGoal extends Goal {
        private final OwlEntity owl;

        public OwlPerchGoal(OwlEntity owl) {
            this.owl = owl;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return owl.isFlying() && owl.random.nextInt(200) == 0;
        }

        @Override
        public void start() {
            BlockPos pos = owl.blockPosition();
            for (int y = 0; y < 10; y++) {
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos checkPos = pos.offset(x, y, z);
                        if (owl.level().getBlockState(checkPos.above()).isAir() &&
                            !owl.level().getBlockState(checkPos).isAir()) {
                            owl.perchTarget = checkPos.above();
                            owl.getNavigation().moveTo(checkPos.above(), 1.0D);
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public void tick() {
            if (owl.perchTarget != null && owl.distanceToSqr(owl.perchTarget.getCenter()) < 2.0D) {
                owl.setFlying(false);
                owl.setPerched(true);
                owl.perchTimer = 200 + owl.random.nextInt(200);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return owl.isPerched() && owl.perchTimer > 0;
        }
    }
}
