package com.wildlife.mod.entity;

import com.wildlife.mod.WildlifeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class WildlifeEntities {

 private static ResourceKey<EntityType<?>> key(String id) {
 return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(WildlifeMod.MOD_ID, id));
 }

 // Forest & Plains
 public static final EntityType<DeerEntity> DEER = EntityType.Builder
 .of(DeerEntity::new, MobCategory.CREATURE)
 .sized(0.9F, 1.3F)
 .build(key("deer"));

 public static final EntityType<BoarEntity> BOAR = EntityType.Builder
 .of(BoarEntity::new, MobCategory.CREATURE)
 .sized(0.9F, 0.9F)
 .build(key("boar"));

 // Fox, Bee, Goat removed — already exist in vanilla MC 26.1.2

 public static final EntityType<RaccoonEntity> RACCOON = EntityType.Builder
 .of(RaccoonEntity::new, MobCategory.CREATURE)
 .sized(0.6F, 0.5F)
 .build(key("raccoon"));

 public static final EntityType<BadgerEntity> BADGER = EntityType.Builder
 .of(BadgerEntity::new, MobCategory.CREATURE)
 .sized(0.6F, 0.4F)
 .build(key("badger"));

 // Jungle
 public static final EntityType<MonkeyEntity> MONKEY = EntityType.Builder
 .of(MonkeyEntity::new, MobCategory.CREATURE)
 .sized(0.4F, 0.8F)
 .build(key("monkey"));

 public static final EntityType<TapirEntity> TAPIR = EntityType.Builder
 .of(TapirEntity::new, MobCategory.CREATURE)
 .sized(1.2F, 1.0F)
 .build(key("tapir"));

 public static final EntityType<ToucanEntity> TOUCAN = EntityType.Builder
 .of(ToucanEntity::new, MobCategory.AMBIENT)
 .sized(0.4F, 0.4F)
 .build(key("toucan"));

 // Desert & Savanna
 public static final EntityType<MeerkatEntity> MEERKAT = EntityType.Builder
 .of(MeerkatEntity::new, MobCategory.CREATURE)
 .sized(0.4F, 0.5F)
 .build(key("meerkat"));

 public static final EntityType<OstrichEntity> OSTRICH = EntityType.Builder
 .of(OstrichEntity::new, MobCategory.CREATURE)
 .sized(1.0F, 2.0F)
 .build(key("ostrich"));

 // Water & Wetlands
 public static final EntityType<OtterEntity> OTTER = EntityType.Builder
 .of(OtterEntity::new, MobCategory.WATER_CREATURE)
 .sized(0.6F, 0.4F)
 .build(key("otter"));

 public static final EntityType<BeaverEntity> BEAVER = EntityType.Builder
 .of(BeaverEntity::new, MobCategory.WATER_CREATURE)
 .sized(0.8F, 0.5F)
 .build(key("beaver"));

 public static final EntityType<DuckEntity> DUCK = EntityType.Builder
 .of(DuckEntity::new, MobCategory.WATER_AMBIENT)
 .sized(0.4F, 0.5F)
 .build(key("duck"));

 // Mountains & Taiga
 public static final EntityType<MarmotEntity> MARMOT = EntityType.Builder
 .of(MarmotEntity::new, MobCategory.CREATURE)
 .sized(0.4F, 0.4F)
 .build(key("marmot"));

 // Snow & Ice
 public static final EntityType<PenguinEntity> PENGUIN = EntityType.Builder
 .of(PenguinEntity::new, MobCategory.WATER_CREATURE)
 .sized(0.5F, 1.0F)
 .build(key("penguin"));

 // Night
 public static final EntityType<OwlEntity> OWL = EntityType.Builder
 .of(OwlEntity::new, MobCategory.AMBIENT)
 .sized(0.4F, 0.5F)
 .build(key("owl"));

 // Reptiles
 public static final EntityType<SnakeEntity> SNAKE = EntityType.Builder
 .of(SnakeEntity::new, MobCategory.CREATURE)
 .sized(0.6F, 0.2F)
 .build(key("snake"));

 // Insects
 public static final EntityType<ButterflyEntity> BUTTERFLY = EntityType.Builder
 .of(ButterflyEntity::new, MobCategory.AMBIENT)
 .sized(0.3F, 0.2F)
 .build(key("butterfly"));

 public static final EntityType<LadybugEntity> LADYBUG = EntityType.Builder
 .of(LadybugEntity::new, MobCategory.AMBIENT)
 .sized(0.25F, 0.15F)
 .build(key("ladybug"));

 public static final EntityType<DragonflyEntity> DRAGONFLY = EntityType.Builder
 .of(DragonflyEntity::new, MobCategory.AMBIENT)
 .sized(0.4F, 0.2F)
 .build(key("dragonfly"));

 public static final EntityType<FireflyEntity> FIREFLY = EntityType.Builder
 .of(FireflyEntity::new, MobCategory.AMBIENT)
 .sized(0.15F, 0.1F)
 .build(key("firefly"));

 // Birds
 public static final EntityType<SparrowEntity> SPARROW = EntityType.Builder
 .of(SparrowEntity::new, MobCategory.AMBIENT)
 .sized(0.3F, 0.3F)
 .build(key("sparrow"));

 public static final EntityType<RobinEntity> ROBIN = EntityType.Builder
 .of(RobinEntity::new, MobCategory.AMBIENT)
 .sized(0.35F, 0.35F)
 .build(key("robin"));

 public static final EntityType<CrowEntity> CROW = EntityType.Builder
 .of(CrowEntity::new, MobCategory.AMBIENT)
 .sized(0.4F, 0.4F)
 .build(key("crow"));

 public static final EntityType<CardinalEntity> CARDINAL = EntityType.Builder
 .of(CardinalEntity::new, MobCategory.AMBIENT)
 .sized(0.35F, 0.35F)
 .build(key("cardinal"));

 public static final EntityType<BluejayEntity> BLUEJAY = EntityType.Builder
 .of(BluejayEntity::new, MobCategory.AMBIENT)
 .sized(0.35F, 0.35F)
 .build(key("bluejay"));
}
