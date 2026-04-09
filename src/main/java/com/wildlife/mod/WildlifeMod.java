package com.wildlife.mod;

import com.wildlife.mod.entity.*;
import com.wildlife.mod.item.WildlifeItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
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

        // Register items
        WildlifeItems.register();

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

    private static <T extends EntityType<?>> void registerEntity(String name, T entity) {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), entity);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
