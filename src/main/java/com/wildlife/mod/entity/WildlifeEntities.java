package com.wildlife.mod.entity;

import com.wildlife.mod.WildlifeMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class WildlifeEntities {

	// Forest & Plains
	public static final EntityType<DeerEntity> DEER = EntityType.Builder
			.of(DeerEntity::new, MobCategory.CREATURE)
			.sized(0.9F, 1.3F)
			.build(WildlifeMod.id("deer").toString());

	public static final EntityType<BoarEntity> BOAR = EntityType.Builder
			.of(BoarEntity::new, MobCategory.CREATURE)
			.sized(0.9F, 0.9F)
			.build(WildlifeMod.id("boar").toString());

	public static final EntityType<FoxEntity> FOX = EntityType.Builder
			.of(FoxEntity::new, MobCategory.CREATURE)
			.sized(0.6F, 0.7F)
			.build(WildlifeMod.id("fox").toString());

	public static final EntityType<RaccoonEntity> RACCOON = EntityType.Builder
			.of(RaccoonEntity::new, MobCategory.CREATURE)
			.sized(0.6F, 0.5F)
			.build(WildlifeMod.id("raccoon").toString());

	public static final EntityType<BadgerEntity> BADGER = EntityType.Builder
			.of(BadgerEntity::new, MobCategory.CREATURE)
			.sized(0.6F, 0.4F)
			.build(WildlifeMod.id("badger").toString());

	// Jungle
	public static final EntityType<MonkeyEntity> MONKEY = EntityType.Builder
			.of(MonkeyEntity::new, MobCategory.CREATURE)
			.sized(0.4F, 0.8F)
			.build(WildlifeMod.id("monkey").toString());

	public static final EntityType<TapirEntity> TAPIR = EntityType.Builder
			.of(TapirEntity::new, MobCategory.CREATURE)
			.sized(1.2F, 1.0F)
			.build(WildlifeMod.id("tapir").toString());

	public static final EntityType<ToucanEntity> TOUCAN = EntityType.Builder
			.of(ToucanEntity::new, MobCategory.AMBIENT)
			.sized(0.4F, 0.4F)
			.build(WildlifeMod.id("toucan").toString());

	// Desert & Savanna
	public static final EntityType<MeerkatEntity> MEERKAT = EntityType.Builder
			.of(MeerkatEntity::new, MobCategory.CREATURE)
			.sized(0.4F, 0.5F)
			.build(WildlifeMod.id("meerkat").toString());

	public static final EntityType<OstrichEntity> OSTRICH = EntityType.Builder
			.of(OstrichEntity::new, MobCategory.CREATURE)
			.sized(1.0F, 2.0F)
			.build(WildlifeMod.id("ostrich").toString());

	// Water & Wetlands
	public static final EntityType<OtterEntity> OTTER = EntityType.Builder
			.of(OtterEntity::new, MobCategory.WATER_CREATURE)
			.sized(0.6F, 0.4F)
			.build(WildlifeMod.id("otter").toString());

	public static final EntityType<BeaverEntity> BEAVER = EntityType.Builder
			.of(BeaverEntity::new, MobCategory.WATER_CREATURE)
			.sized(0.8F, 0.5F)
			.build(WildlifeMod.id("beaver").toString());

	public static final EntityType<DuckEntity> DUCK = EntityType.Builder
			.of(DuckEntity::new, MobCategory.WATER_AMBIENT)
			.sized(0.4F, 0.5F)
			.build(WildlifeMod.id("duck").toString());

	// Mountains & Taiga
	public static final EntityType<MarmotEntity> MARMOT = EntityType.Builder
			.of(MarmotEntity::new, MobCategory.CREATURE)
			.sized(0.4F, 0.4F)
			.build(WildlifeMod.id("marmot").toString());

	public static final EntityType<GoatEntity> GOAT = EntityType.Builder
			.of(GoatEntity::new, MobCategory.CREATURE)
			.sized(0.9F, 1.3F)
			.build(WildlifeMod.id("goat").toString());

	// Snow & Ice
	public static final EntityType<PenguinEntity> PENGUIN = EntityType.Builder
			.of(PenguinEntity::new, MobCategory.WATER_CREATURE)
			.sized(0.5F, 1.0F)
			.build(WildlifeMod.id("penguin").toString());

	// Night
	public static final EntityType<OwlEntity> OWL = EntityType.Builder
			.of(OwlEntity::new, MobCategory.AMBIENT)
			.sized(0.4F, 0.5F)
			.build(WildlifeMod.id("owl").toString());

	// Reptiles
	public static final EntityType<SnakeEntity> SNAKE = EntityType.Builder
			.of(SnakeEntity::new, MobCategory.CREATURE)
			.sized(0.6F, 0.2F)
			.build(WildlifeMod.id("snake").toString());

	// Insects
	public static final EntityType<ButterflyEntity> BUTTERFLY = EntityType.Builder
			.of(ButterflyEntity::new, MobCategory.AMBIENT)
			.sized(0.3F, 0.2F)
			.build(WildlifeMod.id("butterfly").toString());

	public static final EntityType<BeeEntity> BEE = EntityType.Builder
			.of(BeeEntity::new, MobCategory.AMBIENT)
			.sized(0.3F, 0.3F)
			.build(WildlifeMod.id("bee").toString());

	public static final EntityType<LadybugEntity> LADYBUG = EntityType.Builder
			.of(LadybugEntity::new, MobCategory.AMBIENT)
			.sized(0.25F, 0.15F)
			.build(WildlifeMod.id("ladybug").toString());

	public static final EntityType<DragonflyEntity> DRAGONFLY = EntityType.Builder
			.of(DragonflyEntity::new, MobCategory.AMBIENT)
			.sized(0.4F, 0.2F)
			.build(WildlifeMod.id("dragonfly").toString());

	public static final EntityType<FireflyEntity> FIREFLY = EntityType.Builder
			.of(FireflyEntity::new, MobCategory.AMBIENT)
			.sized(0.15F, 0.1F)
			.build(WildlifeMod.id("firefly").toString());

	// Birds
	public static final EntityType<SparrowEntity> SPARROW = EntityType.Builder
			.of(SparrowEntity::new, MobCategory.AMBIENT)
			.sized(0.3F, 0.3F)
			.build(WildlifeMod.id("sparrow").toString());

	public static final EntityType<RobinEntity> ROBIN = EntityType.Builder
			.of(RobinEntity::new, MobCategory.AMBIENT)
			.sized(0.35F, 0.35F)
			.build(WildlifeMod.id("robin").toString());

	public static final EntityType<CrowEntity> CROW = EntityType.Builder
			.of(CrowEntity::new, MobCategory.AMBIENT)
			.sized(0.4F, 0.4F)
			.build(WildlifeMod.id("crow").toString());

	public static final EntityType<CardinalEntity> CARDINAL = EntityType.Builder
			.of(CardinalEntity::new, MobCategory.AMBIENT)
			.sized(0.35F, 0.35F)
			.build(WildlifeMod.id("cardinal").toString());

	public static final EntityType<BluejayEntity> BLUEJAY = EntityType.Builder
			.of(BluejayEntity::new, MobCategory.AMBIENT)
			.sized(0.35F, 0.35F)
			.build(WildlifeMod.id("bluejay").toString());
}
