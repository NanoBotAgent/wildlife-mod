package com.wildlife.mod.client;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.*;
import com.wildlife.mod.client.model.WildlifeModelLayers;
import com.wildlife.mod.client.renderer.WildlifeMobRenderer;
import com.wildlife.mod.client.renderer.SnakeRenderer;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;
import com.wildlife.mod.entity.WildlifeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WildlifeModClient implements ClientModInitializer {

 @Override
 public void onInitializeClient() {
 // Register model layers
 WildlifeModelLayers.registerModelLayers();

 // Forest & Plains
 EntityRendererRegistry.register(WildlifeEntities.DEER,
 ctx -> new WildlifeMobRenderer<>(ctx, new DeerModel(ctx.bakeLayer(WildlifeModelLayers.DEER)), 0.5F, "deer"));
 EntityRendererRegistry.register(WildlifeEntities.BOAR,
 ctx -> new WildlifeMobRenderer<>(ctx, new BoarModel(ctx.bakeLayer(WildlifeModelLayers.BOAR)), 0.5F, "boar"));
 // Fox, Bee, Goat removed — already exist in vanilla MC 26.1.2
 EntityRendererRegistry.register(WildlifeEntities.RACCOON,
 ctx -> new WildlifeMobRenderer<>(ctx, new RaccoonModel(ctx.bakeLayer(WildlifeModelLayers.RACCOON)), 0.4F, "raccoon"));
 EntityRendererRegistry.register(WildlifeEntities.BADGER,
 ctx -> new WildlifeMobRenderer<>(ctx, new BadgerModel(ctx.bakeLayer(WildlifeModelLayers.BADGER)), 0.35F, "badger"));

 // Jungle
 EntityRendererRegistry.register(WildlifeEntities.MONKEY,
 ctx -> new WildlifeMobRenderer<>(ctx, new MonkeyModel(ctx.bakeLayer(WildlifeModelLayers.MONKEY)), 0.4F, "monkey"));
 EntityRendererRegistry.register(WildlifeEntities.TAPIR,
 ctx -> new WildlifeMobRenderer<>(ctx, new TapirModel(ctx.bakeLayer(WildlifeModelLayers.TAPIR)), 0.6F, "tapir"));
 EntityRendererRegistry.register(WildlifeEntities.TOUCAN,
 ctx -> new WildlifeMobRenderer<>(ctx, new ToucanModel(ctx.bakeLayer(WildlifeModelLayers.TOUCAN)), 0.3F, "toucan"));

 // Desert & Savanna
 EntityRendererRegistry.register(WildlifeEntities.MEERKAT,
 ctx -> new WildlifeMobRenderer<>(ctx, new MeerkatModel(ctx.bakeLayer(WildlifeModelLayers.MEERKAT)), 0.3F, "meerkat"));
 EntityRendererRegistry.register(WildlifeEntities.OSTRICH,
 ctx -> new WildlifeMobRenderer<>(ctx, new OstrichModel(ctx.bakeLayer(WildlifeModelLayers.OSTRICH)), 0.7F, "ostrich"));

 // Water & Wetlands
 EntityRendererRegistry.register(WildlifeEntities.OTTER,
 ctx -> new WildlifeMobRenderer<>(ctx, new OtterModel(ctx.bakeLayer(WildlifeModelLayers.OTTER)), 0.3F, "otter"));
 EntityRendererRegistry.register(WildlifeEntities.BEAVER,
 ctx -> new WildlifeMobRenderer<>(ctx, new BeaverModel(ctx.bakeLayer(WildlifeModelLayers.BEAVER)), 0.4F, "beaver"));
 EntityRendererRegistry.register(WildlifeEntities.DUCK,
 ctx -> new WildlifeMobRenderer<>(ctx, new DuckModel(ctx.bakeLayer(WildlifeModelLayers.DUCK)), 0.3F, "duck"));

 // Mountains & Taiga
 EntityRendererRegistry.register(WildlifeEntities.MARMOT,
 ctx -> new WildlifeMobRenderer<>(ctx, new MarmotModel(ctx.bakeLayer(WildlifeModelLayers.MARMOT)), 0.3F, "marmot"));

 // Snow & Ice
 EntityRendererRegistry.register(WildlifeEntities.PENGUIN,
 ctx -> new WildlifeMobRenderer<>(ctx, new PenguinModel(ctx.bakeLayer(WildlifeModelLayers.PENGUIN)), 0.4F, "penguin"));

 // Night
 EntityRendererRegistry.register(WildlifeEntities.OWL,
 ctx -> new WildlifeMobRenderer<>(ctx, new OwlModel(ctx.bakeLayer(WildlifeModelLayers.OWL)), 0.3F, "owl"));

 // Reptiles
 EntityRendererRegistry.register(WildlifeEntities.SNAKE, SnakeRenderer::new);

 // Insects
 EntityRendererRegistry.register(WildlifeEntities.BUTTERFLY,
 ctx -> new WildlifeMobRenderer<>(ctx, new ButterflyModel(ctx.bakeLayer(WildlifeModelLayers.BUTTERFLY)), 0.1F, "butterfly"));
 EntityRendererRegistry.register(WildlifeEntities.LADYBUG,
 ctx -> new WildlifeMobRenderer<>(ctx, new LadybugModel(ctx.bakeLayer(WildlifeModelLayers.LADYBUG)), 0.1F, "ladybug"));
 EntityRendererRegistry.register(WildlifeEntities.DRAGONFLY,
 ctx -> new WildlifeMobRenderer<>(ctx, new DragonflyModel(ctx.bakeLayer(WildlifeModelLayers.DRAGONFLY)), 0.1F, "dragonfly"));
 EntityRendererRegistry.register(WildlifeEntities.FIREFLY,
 ctx -> new WildlifeMobRenderer<>(ctx, new FireflyModel(ctx.bakeLayer(WildlifeModelLayers.FIREFLY)), 0.05F, "firefly"));

 // Birds
 EntityRendererRegistry.register(WildlifeEntities.SPARROW,
 ctx -> new WildlifeMobRenderer<>(ctx, new SparrowModel(ctx.bakeLayer(WildlifeModelLayers.SPARROW)), 0.15F, "sparrow"));
 EntityRendererRegistry.register(WildlifeEntities.ROBIN,
 ctx -> new WildlifeMobRenderer<>(ctx, new RobinModel(ctx.bakeLayer(WildlifeModelLayers.ROBIN)), 0.15F, "robin"));
 EntityRendererRegistry.register(WildlifeEntities.CROW,
 ctx -> new WildlifeMobRenderer<>(ctx, new CrowModel(ctx.bakeLayer(WildlifeModelLayers.CROW)), 0.2F, "crow"));
 EntityRendererRegistry.register(WildlifeEntities.CARDINAL,
 ctx -> new WildlifeMobRenderer<>(ctx, new CardinalModel(ctx.bakeLayer(WildlifeModelLayers.CARDINAL)), 0.15F, "cardinal"));
 EntityRendererRegistry.register(WildlifeEntities.BLUEJAY,
 ctx -> new WildlifeMobRenderer<>(ctx, new BluejayModel(ctx.bakeLayer(WildlifeModelLayers.BLUEJAY)), 0.15F, "bluejay"));

 WildlifeMod.LOGGER.info("Wildlife Mod client initialized with proper renderers!");
 }
}
