package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.WildlifeModelLayers;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

/**
 * Generic renderer for wildlife entities that don't need custom render state.
 * Uses WildlifeRenderState (extends LivingEntityRenderState) and the entity's
 * corresponding model from WildlifeModelLayers.
 *
 * @param <E> the entity type
 * @param <M> the model type
 */
public class WildlifeMobRenderer<E extends Mob, M extends EntityModel<WildlifeRenderState>> extends MobRenderer<E, WildlifeRenderState, M> {
    private final Identifier texture;

    public WildlifeMobRenderer(EntityRendererProvider.Context context, M model, float shadowRadius, String entityName) {
        super(context, model, shadowRadius);
        this.texture = WildlifeMod.id("textures/entity/" + entityName + ".png");
    }

    @Override
    public WildlifeRenderState createRenderState() {
        return new WildlifeRenderState();
    }

    @Override
    public void extractRenderState(E entity, WildlifeRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
    }

    @Override
    public Identifier getTextureLocation(WildlifeRenderState state) {
        return texture;
    }
}
