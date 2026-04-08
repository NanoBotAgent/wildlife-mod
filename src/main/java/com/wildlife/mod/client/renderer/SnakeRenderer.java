package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.SnakeModel;
import com.wildlife.mod.entity.SnakeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnakeRenderer extends MobRenderer<SnakeEntity, SnakeModel<SnakeEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/snake/snake.png");
    private static final ResourceLocation TEXTURE_RATTLE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/snake/snake_rattle.png");
    private static final ResourceLocation TEXTURE_COBRA = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/snake/snake_cobra.png");

    public SnakeRenderer(EntityRendererProvider.Context context) {
        super(context, new SnakeModel<>(context.bakeLayer(ModModelLayers.SNAKE)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(SnakeEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> TEXTURE_RATTLE;
            case 2 -> TEXTURE_COBRA;
            default -> TEXTURE;
        };
    }
}
