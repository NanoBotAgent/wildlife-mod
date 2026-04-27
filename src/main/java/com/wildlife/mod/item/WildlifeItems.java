package com.wildlife.mod.item;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.entity.WildlifeEntities;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class WildlifeItems {

    // Spawn Eggs
    public static final Item DEER_SPAWN_EGG = registerSpawnEgg("deer_spawn_egg", WildlifeEntities.DEER);
    public static final Item BOAR_SPAWN_EGG = registerSpawnEgg("boar_spawn_egg", WildlifeEntities.BOAR);
    public static final Item FOX_SPAWN_EGG = registerSpawnEgg("fox_spawn_egg", WildlifeEntities.FOX);
    public static final Item RACCOON_SPAWN_EGG = registerSpawnEgg("raccoon_spawn_egg", WildlifeEntities.RACCOON);
    public static final Item BADGER_SPAWN_EGG = registerSpawnEgg("badger_spawn_egg", WildlifeEntities.BADGER);
    public static final Item MONKEY_SPAWN_EGG = registerSpawnEgg("monkey_spawn_egg", WildlifeEntities.MONKEY);
    public static final Item TAPIR_SPAWN_EGG = registerSpawnEgg("tapir_spawn_egg", WildlifeEntities.TAPIR);
    public static final Item TOUCCAN_SPAWN_EGG = registerSpawnEgg("toucan_spawn_egg", WildlifeEntities.TOUCAN);
    public static final Item MEERKAT_SPAWN_EGG = registerSpawnEgg("meerkat_spawn_egg", WildlifeEntities.MEERKAT);
    public static final Item OSTRICH_SPAWN_EGG = registerSpawnEgg("ostrich_spawn_egg", WildlifeEntities.OSTRICH);
    public static final Item OTTER_SPAWN_EGG = registerSpawnEgg("otter_spawn_egg", WildlifeEntities.OTTER);
    public static final Item BEAVER_SPAWN_EGG = registerSpawnEgg("beaver_spawn_egg", WildlifeEntities.BEAVER);
    public static final Item DUCK_SPAWN_EGG = registerSpawnEgg("duck_spawn_egg", WildlifeEntities.DUCK);
    public static final Item MARMOT_SPAWN_EGG = registerSpawnEgg("marmot_spawn_egg", WildlifeEntities.MARMOT);
    public static final Item GOAT_SPAWN_EGG = registerSpawnEgg("goat_spawn_egg", WildlifeEntities.GOAT);
    public static final Item PENGUIN_SPAWN_EGG = registerSpawnEgg("penguin_spawn_egg", WildlifeEntities.PENGUIN);
    public static final Item OWL_SPAWN_EGG = registerSpawnEgg("owl_spawn_egg", WildlifeEntities.OWL);
    public static final Item SNAKE_SPAWN_EGG = registerSpawnEgg("snake_spawn_egg", WildlifeEntities.SNAKE);
    public static final Item BUTTERFLY_SPAWN_EGG = registerSpawnEgg("butterfly_spawn_egg", WildlifeEntities.BUTTERFLY);
    public static final Item BEE_SPAWN_EGG = registerSpawnEgg("bee_spawn_egg", WildlifeEntities.BEE);
    public static final Item LADYBUG_SPAWN_EGG = registerSpawnEgg("ladybug_spawn_egg", WildlifeEntities.LADYBUG);
    public static final Item DRAGONFLY_SPAWN_EGG = registerSpawnEgg("dragonfly_spawn_egg", WildlifeEntities.DRAGONFLY);
    public static final Item FIREFLY_SPAWN_EGG = registerSpawnEgg("firefly_spawn_egg", WildlifeEntities.FIREFLY);
    public static final Item SPARROW_SPAWN_EGG = registerSpawnEgg("sparrow_spawn_egg", WildlifeEntities.SPARROW);
    public static final Item ROBIN_SPAWN_EGG = registerSpawnEgg("robin_spawn_egg", WildlifeEntities.ROBIN);
    public static final Item CROW_SPAWN_EGG = registerSpawnEgg("crow_spawn_egg", WildlifeEntities.CROW);
    public static final Item CARDINAL_SPAWN_EGG = registerSpawnEgg("cardinal_spawn_egg", WildlifeEntities.CARDINAL);
    public static final Item BLUEJAY_SPAWN_EGG = registerSpawnEgg("bluejay_spawn_egg", WildlifeEntities.BLUEJAY);

    private static Item registerSpawnEgg(String name, EntityType<?> type) {
        Item item = new SpawnEggItem(new Item.Properties().spawnEgg(type));
        return registerItem(name, item);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, WildlifeMod.id(name), item);
    }

    public static void register() {
        // Items are registered via static initialization
    }
}
