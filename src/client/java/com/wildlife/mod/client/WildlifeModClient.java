package com.wildlife.mod.client;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.entity.WildlifeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.Identifier;

public class WildlifeModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Register entity renderers
		// These will use placeholder renderers until proper models are created

		EntityRendererRegistry.register(WildlifeEntities.DEER, context -> new PlaceholderRenderer<>(context, "deer"));
		EntityRendererRegistry.register(WildlifeEntities.BOAR, context -> new PlaceholderRenderer<>(context, "boar"));
		EntityRendererRegistry.register(WildlifeEntities.FOX, context -> new PlaceholderRenderer<>(context, "fox"));
		EntityRendererRegistry.register(WildlifeEntities.RACCOON, context -> new PlaceholderRenderer<>(context, "raccoon"));
		EntityRendererRegistry.register(WildlifeEntities.BADGER, context -> new PlaceholderRenderer<>(context, "badger"));
		EntityRendererRegistry.register(WildlifeEntities.MONKEY, context -> new PlaceholderRenderer<>(context, "monkey"));
		EntityRendererRegistry.register(WildlifeEntities.TAPIR, context -> new PlaceholderRenderer<>(context, "tapir"));
		EntityRendererRegistry.register(WildlifeEntities.TOUCAN, context -> new PlaceholderRenderer<>(context, "toucan"));
		EntityRendererRegistry.register(WildlifeEntities.MEERKAT, context -> new PlaceholderRenderer<>(context, "meerkat"));
		EntityRendererRegistry.register(WildlifeEntities.OSTRICH, context -> new PlaceholderRenderer<>(context, "ostrich"));
		EntityRendererRegistry.register(WildlifeEntities.OTTER, context -> new PlaceholderRenderer<>(context, "otter"));
		EntityRendererRegistry.register(WildlifeEntities.BEAVER, context -> new PlaceholderRenderer<>(context, "beaver"));
		EntityRendererRegistry.register(WildlifeEntities.DUCK, context -> new PlaceholderRenderer<>(context, "duck"));
		EntityRendererRegistry.register(WildlifeEntities.MARMOT, context -> new PlaceholderRenderer<>(context, "marmot"));
		EntityRendererRegistry.register(WildlifeEntities.GOAT, context -> new PlaceholderRenderer<>(context, "goat"));
		EntityRendererRegistry.register(WildlifeEntities.PENGUIN, context -> new PlaceholderRenderer<>(context, "penguin"));
		EntityRendererRegistry.register(WildlifeEntities.OWL, context -> new PlaceholderRenderer<>(context, "owl"));
		EntityRendererRegistry.register(WildlifeEntities.SNAKE, context -> new PlaceholderRenderer<>(context, "snake"));
		EntityRendererRegistry.register(WildlifeEntities.BUTTERFLY, context -> new PlaceholderRenderer<>(context, "butterfly"));
		EntityRendererRegistry.register(WildlifeEntities.BEE, context -> new PlaceholderRenderer<>(context, "bee"));
		EntityRendererRegistry.register(WildlifeEntities.LADYBUG, context -> new PlaceholderRenderer<>(context, "ladybug"));
		EntityRendererRegistry.register(WildlifeEntities.DRAGONFLY, context -> new PlaceholderRenderer<>(context, "dragonfly"));
		EntityRendererRegistry.register(WildlifeEntities.FIREFLY, context -> new PlaceholderRenderer<>(context, "firefly"));
		EntityRendererRegistry.register(WildlifeEntities.SPARROW, context -> new PlaceholderRenderer<>(context, "sparrow"));
		EntityRendererRegistry.register(WildlifeEntities.ROBIN, context -> new PlaceholderRenderer<>(context, "robin"));
		EntityRendererRegistry.register(WildlifeEntities.CROW, context -> new PlaceholderRenderer<>(context, "crow"));
		EntityRendererRegistry.register(WildlifeEntities.CARDINAL, context -> new PlaceholderRenderer<>(context, "cardinal"));
		EntityRendererRegistry.register(WildlifeEntities.BLUEJAY, context -> new PlaceholderRenderer<>(context, "bluejay"));

		WildlifeMod.LOGGER.info("Wildlife Mod client initialized!");
	}

	/**
	 * Placeholder renderer until proper models are implemented
	 */
	private static class PlaceholderRenderer<T extends net.minecraft.world.entity.Entity> extends MobRenderer<T, EntityModel<T>> {
		private static final Identifier PLACEHOLDER_TEXTURE = WildlifeMod.id("textures/entity/placeholder.png");

		public PlaceholderRenderer(EntityRendererProvider.Context context, String entityName) {
			super(context, new PlaceholderModel<>(), 0.5F);
		}

		@Override
		public Identifier getTextureLocation(T entity) {
			return PLACEHOLDER_TEXTURE;
		}
	}

	/**
	 * Placeholder model - uses a simple cube
	 */
	private static class PlaceholderModel<T extends net.minecraft.world.entity.Entity> extends EntityModel<T> {
		public PlaceholderModel() {
			// Empty model for now
		}

		@Override
		public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		}

		@Override
		public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack poseStack, com.mojang.blaze3d.vertex.VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
			// Render nothing for now - will be replaced with proper models
		}
	}
}
