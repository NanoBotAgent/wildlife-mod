package com.wildlife.mod.entity;

import com.wildlife.mod.WildlifeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = 
        DeferredRegister.create(Registries.ENTITY_TYPE, WildlifeMod.MOD_ID);

    // Forest/Plains animals
    public static final RegistryObject<EntityType<DeerEntity>> DEER = 
        ENTITIES.register("deer", () -> EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE)
            .sized(0.9F, 1.8F)
            .clientTrackingRange(10)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":deer"));

    public static final RegistryObject<EntityType<BoarEntity>> BOAR = 
        ENTITIES.register("boar", () -> EntityType.Builder.of(BoarEntity::new, MobCategory.CREATURE)
            .sized(0.9F, 0.9F)
            .clientTrackingRange(10)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":boar"));



    // Jungle animals
    public static final RegistryObject<EntityType<MonkeyEntity>> MONKEY = 
        ENTITIES.register("monkey", () -> EntityType.Builder.of(MonkeyEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.9F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":monkey"));

    public static final RegistryObject<EntityType<TapirEntity>> TAPIR = 
        ENTITIES.register("tapir", () -> EntityType.Builder.of(TapirEntity::new, MobCategory.CREATURE)
            .sized(1.0F, 1.0F)
            .clientTrackingRange(10)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":tapir"));

    public static final RegistryObject<EntityType<ToucanEntity>> TOUCAN = 
        ENTITIES.register("toucan", () -> EntityType.Builder.of(ToucanEntity::new, MobCategory.CREATURE)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":toucan"));

    // Desert/Savanna animals
    public static final RegistryObject<EntityType<MeerkatEntity>> MEERKAT = 
        ENTITIES.register("meerkat", () -> EntityType.Builder.of(MeerkatEntity::new, MobCategory.CREATURE)
            .sized(0.4F, 0.5F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":meerkat"));

    public static final RegistryObject<EntityType<OstrichEntity>> OSTRICH = 
        ENTITIES.register("ostrich", () -> EntityType.Builder.of(OstrichEntity::new, MobCategory.CREATURE)
            .sized(1.0F, 2.0F)
            .clientTrackingRange(10)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":ostrich"));

    // Water/Wetland animals
    public static final RegistryObject<EntityType<OtterEntity>> OTTER = 
        ENTITIES.register("otter", () -> EntityType.Builder.of(OtterEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.4F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":otter"));

    public static final RegistryObject<EntityType<BeaverEntity>> BEAVER = 
        ENTITIES.register("beaver", () -> EntityType.Builder.of(BeaverEntity::new, MobCategory.CREATURE)
            .sized(0.7F, 0.6F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":beaver"));

    public static final RegistryObject<EntityType<DuckEntity>> DUCK = 
        ENTITIES.register("duck", () -> EntityType.Builder.of(DuckEntity::new, MobCategory.CREATURE)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":duck"));

    // Mountain/Taiga animals
    public static final RegistryObject<EntityType<MarmotEntity>> MARMOT = 
        ENTITIES.register("marmot", () -> EntityType.Builder.of(MarmotEntity::new, MobCategory.CREATURE)
            .sized(0.5F, 0.4F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":marmot"));

    // Night/Special animals
    public static final RegistryObject<EntityType<OwlEntity>> OWL =
        ENTITIES.register("owl", () -> EntityType.Builder.of(OwlEntity::new, MobCategory.CREATURE)
            .sized(0.5F, 0.7F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":owl"));
    
    // Reptiles
    public static final RegistryObject<EntityType<SnakeEntity>> SNAKE =
        ENTITIES.register("snake", () -> EntityType.Builder.of(SnakeEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.3F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":snake"));
    
    // Forest/Woodland animals (new)
    public static final RegistryObject<EntityType<FoxEntity>> FOX =
        ENTITIES.register("fox", () -> EntityType.Builder.of(FoxEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.7F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":fox"));
    
    public static final RegistryObject<EntityType<RaccoonEntity>> RACCOON =
        ENTITIES.register("raccoon", () -> EntityType.Builder.of(RaccoonEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.5F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":raccoon"));
    
    public static final RegistryObject<EntityType<BadgerEntity>> BADGER =
        ENTITIES.register("badger", () -> EntityType.Builder.of(BadgerEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.5F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":badger"));
    
    // Snow/Ice animals (new)
    public static final RegistryObject<EntityType<PenguinEntity>> PENGUIN =
        ENTITIES.register("penguin", () -> EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE)
            .sized(0.5F, 0.9F)
            .clientTrackingRange(8)
            .spawnFarFromPlayer()
            .build(WildlifeMod.MOD_ID + ":penguin"));
    
    public static final RegistryObject<EntityType<GoatEntity>> GOAT =
    ENTITIES.register("goat", () -> EntityType.Builder.of(GoatEntity::new, MobCategory.CREATURE)
        .sized(0.9F, 1.2F)
        .clientTrackingRange(10)
        .spawnFarFromPlayer()
        .build(WildlifeMod.MOD_ID + ":goat"));

    // Insects (very small)
    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY =
    ENTITIES.register("butterfly", () -> EntityType.Builder.of(ButterflyEntity::new, MobCategory.AMBIENT)
        .sized(0.3F, 0.2F)
        .clientTrackingRange(5)
        .build(WildlifeMod.MOD_ID + ":butterfly"));

    public static final RegistryObject<EntityType<BeeEntity>> BEE =
    ENTITIES.register("bee", () -> EntityType.Builder.of(BeeEntity::new, MobCategory.AMBIENT)
        .sized(0.3F, 0.3F)
        .clientTrackingRange(5)
        .build(WildlifeMod.MOD_ID + ":bee"));

    public static final RegistryObject<EntityType<LadybugEntity>> LADYBUG =
    ENTITIES.register("ladybug", () -> EntityType.Builder.of(LadybugEntity::new, MobCategory.AMBIENT)
        .sized(0.25F, 0.15F)
        .clientTrackingRange(4)
        .build(WildlifeMod.MOD_ID + ":ladybug"));

    public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY =
    ENTITIES.register("dragonfly", () -> EntityType.Builder.of(DragonflyEntity::new, MobCategory.AMBIENT)
        .sized(0.4F, 0.2F)
        .clientTrackingRange(6)
        .build(WildlifeMod.MOD_ID + ":dragonfly"));

    public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY =
    ENTITIES.register("firefly", () -> EntityType.Builder.of(FireflyEntity::new, MobCategory.AMBIENT)
        .sized(0.15F, 0.1F)
        .clientTrackingRange(4)
        .build(WildlifeMod.MOD_ID + ":firefly"));

    // Birds (small flying)
    public static final RegistryObject<EntityType<SparrowEntity>> SPARROW =
    ENTITIES.register("sparrow", () -> EntityType.Builder.of(SparrowEntity::new, MobCategory.AMBIENT)
        .sized(0.3F, 0.3F)
        .clientTrackingRange(6)
        .build(WildlifeMod.MOD_ID + ":sparrow"));

    public static final RegistryObject<EntityType<RobinEntity>> ROBIN =
    ENTITIES.register("robin", () -> EntityType.Builder.of(RobinEntity::new, MobCategory.AMBIENT)
        .sized(0.35F, 0.35F)
        .clientTrackingRange(6)
        .build(WildlifeMod.MOD_ID + ":robin"));

    public static final RegistryObject<EntityType<CrowEntity>> CROW =
    ENTITIES.register("crow", () -> EntityType.Builder.of(CrowEntity::new, MobCategory.AMBIENT)
        .sized(0.4F, 0.4F)
        .clientTrackingRange(8)
        .build(WildlifeMod.MOD_ID + ":crow"));

    public static final RegistryObject<EntityType<CardinalEntity>> CARDINAL =
    ENTITIES.register("cardinal", () -> EntityType.Builder.of(CardinalEntity::new, MobCategory.AMBIENT)
        .sized(0.35F, 0.35F)
        .clientTrackingRange(6)
        .build(WildlifeMod.MOD_ID + ":cardinal"));

    public static final RegistryObject<EntityType<BluejayEntity>> BLUEJAY =
    ENTITIES.register("bluejay", () -> EntityType.Builder.of(BluejayEntity::new, MobCategory.AMBIENT)
        .sized(0.35F, 0.35F)
        .clientTrackingRange(6)
        .build(WildlifeMod.MOD_ID + ":bluejay"));
}
