package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.SnakeModel;
import com.wildlife.mod.client.model.WildlifeModelLayers;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;
import com.wildlife.mod.entity.SnakeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

/**
 * Snake renderer - handles variant textures and hissing animation state.
 */
public class SnakeRenderer extends MobRenderer<SnakeEntity, WildlifeRenderState, SnakeModel> {
    private static final Identifier[] TEXTURES = {
        WildlifeMod.id("textures/entity/snake_0.png"), // normal
        WildlifeMod.id("textures/entity/snake_1.png"), // venomous
        WildlifeMod.id("textures/entity/snake_2.png")  // aggressive
    };

    public SnakeRenderer(EntityRendererProvider.Context context) {
        super(context, new SnakeModel(context.bakeLayer(WildlifeModelLayers.SNAKE)), 0.3F);
    }

    @Override
    public WildlifeRenderState createRenderState() {
        return new WildlifeRenderState();
    }

    @Override
    public void extractRenderState(SnakeEntity entity, WildlifeRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
    }

    @Override
    public Identifier getTextureLocation(WildlifeRenderState state) {
        // Default to normal texture; variant selection would need custom render state
        return TEXTURES[0];
    }
}
