package com.wildlife.mod.entity;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "wildlife", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSpawnPlacements {

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        // Forest/Plains animals
        event.register(ModEntities.DEER.get(), 
            SpawnPlacements.Type.ON_GROUND, 
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) -> 
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.BOAR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Jungle animals
        event.register(ModEntities.MONKEY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.TAPIR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.TOUCAN.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Water/Wetland animals
        event.register(ModEntities.OTTER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() ||
                serverLevel.getBlockState(pos).liquid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.BEAVER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() ||
                serverLevel.getBlockState(pos).liquid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.DUCK.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() ||
                serverLevel.getBlockState(pos).liquid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Desert/Savanna animals
        event.register(ModEntities.MEERKAT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.OSTRICH.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Mountain/Taiga animals
        event.register(ModEntities.MARMOT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Night/Special animals
        event.register(ModEntities.OWL.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Reptiles
        event.register(ModEntities.SNAKE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid() &&
                serverLevel.getRawBrightness(pos, 0) > 8,
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Forest/Woodland animals (new)
        event.register(ModEntities.FOX.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.RACCOON.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.BADGER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        // Snow/Ice animals (new)
        event.register(ModEntities.PENGUIN.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid(),
            SpawnPlacementRegisterEvent.Operation.AND);
        
        event.register(ModEntities.GOAT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, serverLevel, spawnType, pos, random) ->
                serverLevel.getBlockState(pos.below()).isSolid(),
            SpawnPlacementRegisterEvent.Operation.AND);
    }
    }
