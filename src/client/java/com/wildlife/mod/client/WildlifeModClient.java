package com.wildlife.mod.client;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.entity.WildlifeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class WildlifeModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register placeholder entity renderers
        // These render nothing visible but allow the mod to compile
        // Proper models and renderers will be added later
        EntityRendererRegistry.register(WildlifeEntities.DEER, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.BOAR, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.FOX, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.RACCOON, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.BADGER, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.MONKEY, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.TAPIR, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.TOUCAN, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.MEERKAT, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.OSTRICH, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.OTTER, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.BEAVER, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.DUCK, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.MARMOT, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.GOAT, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.PENGUIN, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.OWL, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.SNAKE, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.BUTTERFLY, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.BEE, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.LADYBUG, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.DRAGONFLY, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.FIREFLY, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.SPARROW, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.ROBIN, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.CROW, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.CARDINAL, PlaceholderRenderer::new);
        EntityRendererRegistry.register(WildlifeEntities.BLUEJAY, PlaceholderRenderer::new);

        WildlifeMod.LOGGER.info("Wildlife Mod client initialized!");
    }

    /**
     * Minimal placeholder renderer for MC 26.1.1's new render state pipeline.
     * Entities will be invisible until proper models/renderers are implemented.
     */
    public static class PlaceholderRenderer extends EntityRenderer<Entity, EntityRenderState> {
        public PlaceholderRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public EntityRenderState createRenderState() {
            return new EntityRenderState();
        }

        @Override
        public void extractRenderState(Entity entity, EntityRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
        }

        @Override
        public Identifier getTextureLocation(EntityRenderState state) {
            return WildlifeMod.id("textures/entity/placeholder.png");
        }
    }
}
