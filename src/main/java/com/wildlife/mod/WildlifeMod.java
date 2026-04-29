package com.wildlife.mod;

import com.wildlife.mod.entity.*;
import com.wildlife.mod.item.WildlifeItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WildlifeMod implements ModInitializer {
    public static final String MOD_ID = "wildlife";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Wildlife Mod...");

        // Register entities
        registerEntities();

        // Register entity attributes (required for spawning!)
        registerAttributes();

        // Register items
        WildlifeItems.register();

        // Add spawn eggs to creative inventory
        registerCreativeTabEntries();

        LOGGER.info("Wildlife Mod initialized!");
    }

    private void registerEntities() {
        // Forest & Plains
        registerEntity("deer", WildlifeEntities.DEER);
        registerEntity("boar", WildlifeEntities.BOAR);
        registerEntity("fox", WildlifeEntities.FOX);
        registerEntity("raccoon", WildlifeEntities.RACCOON);
        registerEntity("badger", WildlifeEntities.BADGER);

        // Jungle
        registerEntity("monkey", WildlifeEntities.MONKEY);
        registerEntity("tapir", WildlifeEntities.TAPIR);
        registerEntity("toucan", WildlifeEntities.TOUCAN);

        // Desert & Savanna
        registerEntity("meerkat", WildlifeEntities.MEERKAT);
        registerEntity("ostrich", WildlifeEntities.OSTRICH);

        // Water & Wetlands
        registerEntity("otter", WildlifeEntities.OTTER);
        registerEntity("beaver", WildlifeEntities.BEAVER);
        registerEntity("duck", WildlifeEntities.DUCK);

        // Mountains & Taiga
        registerEntity("marmot", WildlifeEntities.MARMOT);
        registerEntity("goat", WildlifeEntities.GOAT);

        // Snow & Ice
        registerEntity("penguin", WildlifeEntities.PENGUIN);

        // Night
        registerEntity("owl", WildlifeEntities.OWL);

        // Reptiles
        registerEntity("snake", WildlifeEntities.SNAKE);

        // Insects
        registerEntity("butterfly", WildlifeEntities.BUTTERFLY);
        registerEntity("bee", WildlifeEntities.BEE);
        registerEntity("ladybug", WildlifeEntities.LADYBUG);
        registerEntity("dragonfly", WildlifeEntities.DRAGONFLY);
        registerEntity("firefly", WildlifeEntities.FIREFLY);

        // Birds
        registerEntity("sparrow", WildlifeEntities.SPARROW);
        registerEntity("robin", WildlifeEntities.ROBIN);
        registerEntity("crow", WildlifeEntities.CROW);
        registerEntity("cardinal", WildlifeEntities.CARDINAL);
        registerEntity("bluejay", WildlifeEntities.BLUEJAY);
    }

    private void registerAttributes() {
        // Forest & Plains
        FabricDefaultAttributeRegistry.register(WildlifeEntities.DEER, DeerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.BOAR, BoarEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.FOX, FoxEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.RACCOON, RaccoonEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.BADGER, BadgerEntity.createAttributes());

        // Jungle
        FabricDefaultAttributeRegistry.register(WildlifeEntities.MONKEY, MonkeyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.TAPIR, TapirEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.TOUCAN, ToucanEntity.createAttributes());

        // Desert & Savanna
        FabricDefaultAttributeRegistry.register(WildlifeEntities.MEERKAT, MeerkatEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.OSTRICH, OstrichEntity.createAttributes());

        // Water & Wetlands
        FabricDefaultAttributeRegistry.register(WildlifeEntities.OTTER, OtterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.BEAVER, BeaverEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.DUCK, DuckEntity.createAttributes());

        // Mountains & Taiga
        FabricDefaultAttributeRegistry.register(WildlifeEntities.MARMOT, MarmotEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.GOAT, GoatEntity.createAttributes());

        // Snow & Ice
        FabricDefaultAttributeRegistry.register(WildlifeEntities.PENGUIN, PenguinEntity.createAttributes());

        // Night
        FabricDefaultAttributeRegistry.register(WildlifeEntities.OWL, OwlEntity.createAttributes());

        // Reptiles
        FabricDefaultAttributeRegistry.register(WildlifeEntities.SNAKE, SnakeEntity.createAttributes());

        // Insects
        FabricDefaultAttributeRegistry.register(WildlifeEntities.BUTTERFLY, ButterflyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.BEE, BeeEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.LADYBUG, LadybugEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.DRAGONFLY, DragonflyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.FIREFLY, FireflyEntity.createAttributes());

        // Birds
        FabricDefaultAttributeRegistry.register(WildlifeEntities.SPARROW, SparrowEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.ROBIN, RobinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.CROW, CrowEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.CARDINAL, CardinalEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WildlifeEntities.BLUEJAY, BluejayEntity.createAttributes());

        LOGGER.info("Wildlife Mod entity attributes registered!");
    }

    private void registerCreativeTabEntries() {
        // Add all spawn eggs to the Spawn Eggs creative tab (MC 26.1.1+ API)
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> {
            content.accept(WildlifeItems.DEER_SPAWN_EGG);
            content.accept(WildlifeItems.BOAR_SPAWN_EGG);
            content.accept(WildlifeItems.FOX_SPAWN_EGG);
            content.accept(WildlifeItems.RACCOON_SPAWN_EGG);
            content.accept(WildlifeItems.BADGER_SPAWN_EGG);
            content.accept(WildlifeItems.MONKEY_SPAWN_EGG);
            content.accept(WildlifeItems.TAPIR_SPAWN_EGG);
            content.accept(WildlifeItems.TOUCAN_SPAWN_EGG);
            content.accept(WildlifeItems.MEERKAT_SPAWN_EGG);
            content.accept(WildlifeItems.OSTRICH_SPAWN_EGG);
            content.accept(WildlifeItems.OTTER_SPAWN_EGG);
            content.accept(WildlifeItems.BEAVER_SPAWN_EGG);
            content.accept(WildlifeItems.DUCK_SPAWN_EGG);
            content.accept(WildlifeItems.MARMOT_SPAWN_EGG);
            content.accept(WildlifeItems.GOAT_SPAWN_EGG);
            content.accept(WildlifeItems.PENGUIN_SPAWN_EGG);
            content.accept(WildlifeItems.OWL_SPAWN_EGG);
            content.accept(WildlifeItems.SNAKE_SPAWN_EGG);
            content.accept(WildlifeItems.BUTTERFLY_SPAWN_EGG);
            content.accept(WildlifeItems.BEE_SPAWN_EGG);
            content.accept(WildlifeItems.LADYBUG_SPAWN_EGG);
            content.accept(WildlifeItems.DRAGONFLY_SPAWN_EGG);
            content.accept(WildlifeItems.FIREFLY_SPAWN_EGG);
            content.accept(WildlifeItems.SPARROW_SPAWN_EGG);
            content.accept(WildlifeItems.ROBIN_SPAWN_EGG);
            content.accept(WildlifeItems.CROW_SPAWN_EGG);
            content.accept(WildlifeItems.CARDINAL_SPAWN_EGG);
            content.accept(WildlifeItems.BLUEJAY_SPAWN_EGG);
        });

        LOGGER.info("Wildlife Mod creative tab entries registered!");
    }

    private static <T extends EntityType<?>> void registerEntity(String name, T entity) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, name));
        Registry.register(BuiltInRegistries.ENTITY_TYPE, key, entity);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
