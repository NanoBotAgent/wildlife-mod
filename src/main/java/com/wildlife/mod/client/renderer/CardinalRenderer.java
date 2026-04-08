package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.CardinalModel;
import com.wildlife.mod.entity.CardinalEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CardinalRenderer extends MobRenderer<CardinalEntity, CardinalModel<CardinalEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/bird/cardinal.png");
    
    public CardinalRenderer(EntityRendererProvider.Context context) {
        super(context, new CardinalModel<>(context.bakeLayer(CardinalModel.LAYER_LOCATION)), 0.2F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(CardinalEntity entity) {
        return TEXTURE;
    }
}
