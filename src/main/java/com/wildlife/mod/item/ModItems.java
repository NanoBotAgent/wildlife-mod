package com.wildlife.mod.item;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.entity.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WildlifeMod.MOD_ID);

    // Spawn Eggs
    public static final RegistryObject<Item> DEER_SPAWN_EGG = ITEMS.register("deer_spawn_egg",
        () -> new SpawnEggItem(ModEntities.DEER.get(), 0x8B5A2B, 0xD2B48C, new Item.Properties()));

    public static final RegistryObject<Item> BOAR_SPAWN_EGG = ITEMS.register("boar_spawn_egg",
        () -> new SpawnEggItem(ModEntities.BOAR.get(), 0x4A3728, 0x8B7355, new Item.Properties()));

    public static final RegistryObject<Item> MONKEY_SPAWN_EGG = ITEMS.register("monkey_spawn_egg",
        () -> new SpawnEggItem(ModEntities.MONKEY.get(), 0x785030, 0xFFDCA0, new Item.Properties()));

    public static final RegistryObject<Item> OTTER_SPAWN_EGG = ITEMS.register("otter_spawn_egg",
        () -> new SpawnEggItem(ModEntities.OTTER.get(), 0x5C4033, 0xC4A484, new Item.Properties()));

    public static final RegistryObject<Item> OWL_SPAWN_EGG = ITEMS.register("owl_spawn_egg",
        () -> new SpawnEggItem(ModEntities.OWL.get(), 0x8B7355, 0xD2B48C, new Item.Properties()));

    public static final RegistryObject<Item> BEAVER_SPAWN_EGG = ITEMS.register("beaver_spawn_egg",
        () -> new SpawnEggItem(ModEntities.BEAVER.get(), 0x6B4423, 0x8B7355, new Item.Properties()));

    public static final RegistryObject<Item> MEERKAT_SPAWN_EGG = ITEMS.register("meerkat_spawn_egg",
        () -> new SpawnEggItem(ModEntities.MEERKAT.get(), 0xC4A484, 0x8B7355, new Item.Properties()));

    public static final RegistryObject<Item> DUCK_SPAWN_EGG = ITEMS.register("duck_spawn_egg",
        () -> new SpawnEggItem(ModEntities.DUCK.get(), 0x2F4F4F, 0x00FF7F, new Item.Properties()));

    public static final RegistryObject<Item> TOUCAN_SPAWN_EGG = ITEMS.register("toucan_spawn_egg",
        () -> new SpawnEggItem(ModEntities.TOUCAN.get(), 0x000000, 0xFF6B35, new Item.Properties()));

    public static final RegistryObject<Item> OSTRICH_SPAWN_EGG = ITEMS.register("ostrich_spawn_egg",
        () -> new SpawnEggItem(ModEntities.OSTRICH.get(), 0x1C1C1C, 0xF5F5DC, new Item.Properties()));

    public static final RegistryObject<Item> TAPIR_SPAWN_EGG = ITEMS.register("tapir_spawn_egg",
        () -> new SpawnEggItem(ModEntities.TAPIR.get(), 0x1C1C1C, 0xF5F5DC, new Item.Properties()));

    public static final RegistryObject<Item> MARMOT_SPAWN_EGG = ITEMS.register("marmot_spawn_egg",
        () -> new SpawnEggItem(ModEntities.MARMOT.get(), 0x8B7355, 0xD2B48C, new Item.Properties()));
    
    public static final RegistryObject<Item> SNAKE_SPAWN_EGG = ITEMS.register("snake_spawn_egg",
        () -> new SpawnEggItem(ModEntities.SNAKE.get(), 0x4A5D23, 0x8B4513, new Item.Properties()));

    // New animals spawn eggs
    public static final RegistryObject<Item> FOX_SPAWN_EGG = ITEMS.register("fox_spawn_egg",
        () -> new SpawnEggItem(ModEntities.FOX.get(), 0xCD5C34, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> RACCOON_SPAWN_EGG = ITEMS.register("raccoon_spawn_egg",
        () -> new SpawnEggItem(ModEntities.RACCOON.get(), 0x82828C, 0x32323C, new Item.Properties()));

    public static final RegistryObject<Item> BADGER_SPAWN_EGG = ITEMS.register("badger_spawn_egg",
        () -> new SpawnEggItem(ModEntities.BADGER.get(), 0xFFFFFF, 0x19191E, new Item.Properties()));

    public static final RegistryObject<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg",
        () -> new SpawnEggItem(ModEntities.PENGUIN.get(), 0x0F0F14, 0xFF8C00, new Item.Properties()));

    public static final RegistryObject<Item> GOAT_SPAWN_EGG = ITEMS.register("goat_spawn_egg",
        () -> new SpawnEggItem(ModEntities.GOAT.get(), 0xF5F5F5, 0xA08C73, new Item.Properties()));

    // Food Items
    public static final RegistryObject<Item> VENISON = ITEMS.register("venison",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(3)
                .saturationModifier(0.3F)
                .meat()
                .build())));

    public static final RegistryObject<Item> COOKED_VENISON = ITEMS.register("cooked_venison",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(6)
                .saturationModifier(0.7F)
                .meat()
                .build())));

    public static final RegistryObject<Item> BOAR_MEAT = ITEMS.register("boar_meat",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(3)
                .saturationModifier(0.3F)
                .meat()
                .build())));

    public static final RegistryObject<Item> COOKED_BOAR_MEAT = ITEMS.register("cooked_boar_meat",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(6)
                .saturationModifier(0.7F)
                .meat()
                .build())));

    public static final RegistryObject<Item> DUCK_MEAT = ITEMS.register("duck_meat",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(2)
                .saturationModifier(0.3F)
                .meat()
                .build())));

    public static final RegistryObject<Item> COOKED_DUCK = ITEMS.register("cooked_duck",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(5)
                .saturationModifier(0.6F)
                .meat()
                .build())));

    public static final RegistryObject<Item> OSTRICH_MEAT = ITEMS.register("ostrich_meat",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(3)
                .saturationModifier(0.4F)
                .meat()
                .build())));

    public static final RegistryObject<Item> COOKED_OSTRICH = ITEMS.register("cooked_ostrich",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                .nutrition(7)
                .saturationModifier(0.8F)
                .meat()
                .build())));

    // Materials
    public static final RegistryObject<Item> DEER_HIDE = ITEMS.register("deer_hide",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MONKEY_FUR = ITEMS.register("monkey_fur",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OTTER_FUR = ITEMS.register("otter_fur",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BEAVER_FUR = ITEMS.register("beaver_fur",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FEATHER = ITEMS.register("feather",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OWL_FEATHER = ITEMS.register("owl_feather",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TOUCAN_FEATHER = ITEMS.register("toucan_feather",
        () -> new Item(new Item.Properties()));
}
