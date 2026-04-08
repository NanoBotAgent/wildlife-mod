package com.wildlife.mod.world;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.BiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    // Forest/Plains animals
    public static final ResourceKey<BiomeModifier> ADD_DEER_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, 
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_deer_spawns"));
    
    public static final ResourceKey<BiomeModifier> ADD_BOAR_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_boar_spawns"));
    
    // Jungle animals
    public static final ResourceKey<BiomeModifier> ADD_MONKEY_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_monkey_spawns"));
    
    public static final ResourceKey<BiomeModifier> ADD_TAPIR_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_tapir_spawns"));
    
    public static final ResourceKey<BiomeModifier> ADD_TOUCAN_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_toucan_spawns"));
    
    // Water/Wetland animals
    public static final ResourceKey<BiomeModifier> ADD_OTTER_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_otter_spawns"));
    
    public static final ResourceKey<BiomeModifier> ADD_BEAVER_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_beaver_spawns"));
    
    public static final ResourceKey<BiomeModifier> ADD_DUCK_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_duck_spawns"));
    
    // Desert/Savanna animals
    public static final ResourceKey<BiomeModifier> ADD_MEERKAT_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_meerkat_spawns"));
    
    public static final ResourceKey<BiomeModifier> ADD_OSTRICH_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_ostrich_spawns"));
    
    // Mountain/Taiga animals
    public static final ResourceKey<BiomeModifier> ADD_MARMOT_SPAWNS = 
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_marmot_spawns"));
    
    // Night/Special animals
    public static final ResourceKey<BiomeModifier> ADD_OWL_SPAWNS =
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_owl_spawns"));
    
    // Reptiles
    public static final ResourceKey<BiomeModifier> ADD_SNAKE_SPAWNS =
        ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "add_snake_spawns"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        // Forest/Plains animals
        context.register(ADD_DEER_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                biomes.getOrThrow(BiomeTags.IS_TAIGA),
                biomes.getOrThrow(BiomeTags.IS_PLAINS)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.DEER.get(), 8, 2, 4))));

        context.register(ADD_BOAR_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                biomes.getOrThrow(BiomeTags.IS_PLAINS)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.BOAR.get(), 6, 2, 4))));

        // Jungle animals
        context.register(ADD_MONKEY_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(biomes.getOrThrow(BiomeTags.IS_JUNGLE)),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.MONKEY.get(), 6, 2, 5))));

        context.register(ADD_TAPIR_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(biomes.getOrThrow(BiomeTags.IS_JUNGLE)),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.TAPIR.get(), 4, 1, 3))));

        context.register(ADD_TOUCAN_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(biomes.getOrThrow(BiomeTags.IS_JUNGLE)),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.TOUCAN.get(), 5, 2, 4))));

        // Water/Wetland animals
        context.register(ADD_OTTER_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_RIVER),
                biomes.getOrThrow(BiomeTags.IS_SWAMP)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.OTTER.get(), 5, 2, 4))));

        context.register(ADD_BEAVER_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_RIVER),
                biomes.getOrThrow(BiomeTags.IS_FOREST)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.BEAVER.get(), 4, 1, 3))));

        context.register(ADD_DUCK_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_RIVER),
                biomes.getOrThrow(BiomeTags.IS_SWAMP),
                biomes.getOrThrow(BiomeTags.IS_PLAINS)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.DUCK.get(), 6, 2, 5))));

        // Desert/Savanna animals
        context.register(ADD_MEERKAT_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(biomes.getOrThrow(BiomeTags.IS_SAVANNA)),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.MEERKAT.get(), 5, 2, 4))));

        context.register(ADD_OSTRICH_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(biomes.getOrThrow(BiomeTags.IS_SAVANNA)),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.OSTRICH.get(), 4, 2, 4))));

        // Mountain/Taiga animals
        context.register(ADD_MARMOT_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),
                biomes.getOrThrow(BiomeTags.IS_TAIGA)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.MARMOT.get(), 5, 2, 4))));

        // Night/Special animals
        context.register(ADD_OWL_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                biomes.getOrThrow(BiomeTags.IS_TAIGA)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.OWL.get(), 4, 1, 2))));
        
        // Reptiles - snakes spawn in warm biomes
        context.register(ADD_SNAKE_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
            HolderSet.direct(
                biomes.getOrThrow(BiomeTags.IS_JUNGLE),
                biomes.getOrThrow(BiomeTags.IS_SAVANNA),
                biomes.getOrThrow(BiomeTags.IS_SWAMP)
            ),
            List.of(new MobSpawnSettings.SpawnerData(ModEntities.SNAKE.get(), 4, 1, 3))));
    }
}
