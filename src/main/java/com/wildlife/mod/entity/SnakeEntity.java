package com.wildlife.mod.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import net.minecraft.resources.Identifier;

public class SnakeEntity extends PathfinderMob {
    private static final EntityDataAccessor<Integer> VARIANT =
        SynchedEntityData.defineId(SnakeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_HISSING =
        SynchedEntityData.defineId(SnakeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> TAIL_WAVE =
        SynchedEntityData.defineId(SnakeEntity.class, EntityDataSerializers.FLOAT);

    private float tailWaveAnim = 0;
    private float hissAnim = 0;
    private int attackCooldown = 0;

    public SnakeEntity(EntityType<? extends SnakeEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 8.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 16.0D)
            .add(Attributes.ATTACK_DAMAGE, 2.0D)
            .add(Attributes.STEP_HEIGHT, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public boolean canUse() {
                return SnakeEntity.this.isAggressive() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.5D, 1.5D) {
            @Override
            public boolean canUse() {
                return !SnakeEntity.this.isAggressive() && super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, net.minecraft.world.entity.animal.rabbit.Rabbit.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, net.minecraft.world.entity.animal.chicken.Chicken.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(IS_HISSING, false);
        builder.define(TAIL_WAVE, 0.0F);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(input.getIntOr("Variant", 0));
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public boolean isHissing() {
        return this.entityData.get(IS_HISSING);
    }

    public void setHissing(boolean hissing) {
        this.entityData.set(IS_HISSING, hissing);
    }

    public boolean isAggressive() {
        return getVariant() == 2;
    }

    public boolean isVenomous() {
        return getVariant() == 1 || getVariant() == 2;
    }

    @Override
    public void tick() {
        super.tick();

        this.tailWaveAnim += 0.15F;
        float wave = (float) Math.sin(this.tailWaveAnim) * 0.5F;
        this.entityData.set(TAIL_WAVE, wave);

        if (this.hissAnim > 0) {
            this.hissAnim -= 0.1F;
        }

        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }

        if (this.random.nextInt(200) == 0 && !this.isHissing()) {
            this.setHissing(true);
            this.hissAnim = 1.0F;
        } else if (this.isHissing() && this.hissAnim <= 0) {
            this.setHissing(false);
        }
    }

    public float getTailWave(float partialTick) {
        return this.entityData.get(TAIL_WAVE);
    }

    public float getHissAnim(float partialTick) {
        return this.hissAnim;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (this.attackCooldown <= 0) {
            boolean success = super.doHurtTarget(level, target);
            if (success && target instanceof LivingEntity livingTarget) {
                this.attackCooldown = 40;

                if (this.isVenomous()) {
                    int poisonDuration = getVariant() == 2 ? 100 : 60;
                    int poisonLevel = getVariant() == 2 ? 1 : 0;
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, poisonDuration, poisonLevel));
                }
            }
            return success;
        }
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isHissing() ? SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.phantom.flap")) : SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.spider.ambient"));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.spider.hurt"));
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("minecraft", "entity.spider.death"));
    }

    @Override
    public float getVoicePitch() {
        return 1.5F;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushEntities() {
        // Snakes don't push other entities
    }
}
