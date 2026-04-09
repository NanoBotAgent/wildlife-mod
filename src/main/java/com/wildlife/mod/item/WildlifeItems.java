package com.wildlife.mod.item;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.entity.WildlifeEntities;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class WildlifeItems {

    // Spawn Eggs
    public static final Item DEER_SPAWN_EGG = registerSpawnEgg("deer_spawn_egg", WildlifeEntities.DEER, 0x8B4513, 0xFFFFFF);
    public static final Item BOAR_SPAWN_EGG = registerSpawnEgg("boar_spawn_egg", WildlifeEntities.BOAR, 0x8B4513, 0xD2B48C);
    public static final Item FOX_SPAWN_EGG = registerSpawnEgg("fox_spawn_egg", WildlifeEntities.FOX, 0xFF6600, 0xFFFFFF);
    public static final Item RACCOON_SPAWN_EGG = registerSpawnEgg("raccoon_spawn_egg", WildlifeEntities.RACCOON, 0x808080, 0x2F2F2F);
    public static final Item BADGER_SPAWN_EGG = registerSpawnEgg("badger_spawn_egg", WildlifeEntities.BADGER, 0x696969, 0x1C1C1C);
    public static final Item MONKEY_SPAWN_EGG = registerSpawnEgg("monkey_spawn_egg", WildlifeEntities.MONKEY, 0x8B4513, 0xD2691E);
    public static final Item TAPIR_SPAWN_EGG = registerSpawnEgg("tapir_spawn_egg", WildlifeEntities.TAPIR, 0x1C1C1C, 0xF5F5DC);
    public static final Item TOUCCAN_SPAWN_EGG = registerSpawnEgg("toucan_spawn_egg", WildlifeEntities.TOUCAN, 0x000000, 0xFFA500);
    public static final Item MEERKAT_SPAWN_EGG = registerSpawnEgg("meerkat_spawn_egg", WildlifeEntities.MEERKAT, 0xD2B48C, 0x8B4513);
    public static final Item OSTRICH_SPAWN_EGG = registerSpawnEgg("ostrich_spawn_egg", WildlifeEntities.OSTRICH, 0x1C1C1C, 0xF5F5F5);
    public static final Item OTTER_SPAWN_EGG = registerSpawnEgg("otter_spawn_egg", WildlifeEntities.OTTER, 0x8B4513, 0xF5F5DC);
    public static final Item BEAVER_SPAWN_EGG = registerSpawnEgg("beaver_spawn_egg", WildlifeEntities.BEAVER, 0x8B4513, 0xD2691E);
    public static final Item DUCK_SPAWN_EGG = registerSpawnEgg("duck_spawn_egg", WildlifeEntities.DUCK, 0xFFFFFF, 0x006400);
    public static final Item MARMOT_SPAWN_EGG = registerSpawnEgg("marmot_spawn_egg", WildlifeEntities.MARMOT, 0x8B4513, 0xD2B48C);
    public static final Item GOAT_SPAWN_EGG = registerSpawnEgg("goat_spawn_egg", WildlifeEntities.GOAT, 0xF5F5F5, 0xD2B48C);
    public static final Item PENGUIN_SPAWN_EGG = registerSpawnEgg("penguin_spawn_egg", WildlifeEntities.PENGUIN, 0x1C1C1C, 0xFFFFFF);
    public static final Item OWL_SPAWN_EGG = registerSpawnEgg("owl_spawn_egg", WildlifeEntities.OWL, 0x8B4513, 0xD2B48C);
    public static final Item SNAKE_SPAWN_EGG = registerSpawnEgg("snake_spawn_egg", WildlifeEntities.SNAKE, 0x228B22, 0x8B4513);
    public static final Item BUTTERFLY_SPAWN_EGG = registerSpawnEgg("butterfly_spawn_egg", WildlifeEntities.BUTTERFLY, 0xFFA500, 0x000000);
    public static final Item BEE_SPAWN_EGG = registerSpawnEgg("bee_spawn_egg", WildlifeEntities.BEE, 0xFFD700, 0x000000);
    public static final Item LADYBUG_SPAWN_EGG = registerSpawnEgg("ladybug_spawn_egg", WildlifeEntities.LADYBUG, 0xFF0000, 0x000000);
    public static final Item DRAGONFLY_SPAWN_EGG = registerSpawnEgg("dragonfly_spawn_egg", WildlifeEntities.DRAGONFLY, 0x0000FF, 0x00FF00);
    public static final Item FIREFLY_SPAWN_EGG = registerSpawnEgg("firefly_spawn_egg", WildlifeEntities.FIREFLY, 0xFFFF00, 0x000000);
    public static final Item SPARROW_SPAWN_EGG = registerSpawnEgg("sparrow_spawn_egg", WildlifeEntities.SPARROW, 0x8B4513, 0xD2B48C);
    public static final Item ROBIN_SPAWN_EGG = registerSpawnEgg("robin_spawn_egg", WildlifeEntities.ROBIN, 0x8B4513, 0xFF0000);
    public static final Item CROW_SPAWN_EGG = registerSpawnEgg("crow_spawn_egg", WildlifeEntities.CROW, 0x1C1C1C, 0x1C1C1C);
    public static final Item CARDINAL_SPAWN_EGG = registerSpawnEgg("cardinal_spawn_egg", WildlifeEntities.CARDINAL, 0xFF0000, 0x1C1C1C);
    public static final Item BLUEJAY_SPAWN_EGG = registerSpawnEgg("bluejay_spawn_egg", WildlifeEntities.BLUEJAY, 0x0000FF, 0xFFFFFF);

    private static Item registerSpawnEgg(String name, net.minecraft.world.entity.EntityType<?> type, int primaryColor, int secondaryColor) {
        Item item = new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties());
        return registerItem(name, item);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, WildlifeMod.id(name), item);
    }

    public static void register() {
        // Items are registered via static initialization
    }
}
