package com.wildlife.mod.client;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.renderer.*;
import com.wildlife.mod.entity.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WildlifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Forest/Plains
        event.registerEntityRenderer(ModEntities.DEER.get(), DeerRenderer::new);
        event.registerEntityRenderer(ModEntities.BOAR.get(), BoarRenderer::new);
        
        // Jungle
        event.registerEntityRenderer(ModEntities.MONKEY.get(), MonkeyRenderer::new);
        event.registerEntityRenderer(ModEntities.TAPIR.get(), TapirRenderer::new);
        event.registerEntityRenderer(ModEntities.TOUCAN.get(), ToucanRenderer::new);
        
        // Water/Wetlands
        event.registerEntityRenderer(ModEntities.OTTER.get(), OtterRenderer::new);
        event.registerEntityRenderer(ModEntities.BEAVER.get(), BeaverRenderer::new);
        event.registerEntityRenderer(ModEntities.DUCK.get(), DuckRenderer::new);
        
        // Desert/Savanna
        event.registerEntityRenderer(ModEntities.MEERKAT.get(), MeerkatRenderer::new);
        event.registerEntityRenderer(ModEntities.OSTRICH.get(), OstrichRenderer::new);
        
        // Mountain
        event.registerEntityRenderer(ModEntities.MARMOT.get(), MarmotRenderer::new);

        // Night
        event.registerEntityRenderer(ModEntities.OWL.get(), OwlRenderer::new);
        
    // Reptiles
    event.registerEntityRenderer(ModEntities.SNAKE.get(), SnakeRenderer::new);
    
    // Forest/Woodland (new)
    event.registerEntityRenderer(ModEntities.FOX.get(), FoxRenderer::new);
    event.registerEntityRenderer(ModEntities.RACCOON.get(), RaccoonRenderer::new);
    event.registerEntityRenderer(ModEntities.BADGER.get(), BadgerRenderer::new);
    
    // Snow/Ice (new)
    event.registerEntityRenderer(ModEntities.PENGUIN.get(), PenguinRenderer::new);
    event.registerEntityRenderer(ModEntities.GOAT.get(), GoatRenderer::new);
    }
    }
