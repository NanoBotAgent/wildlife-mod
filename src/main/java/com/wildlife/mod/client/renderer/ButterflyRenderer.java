package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.ButterflyModel;
import com.wildlife.mod.entity.ButterflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ButterflyRenderer extends MobRenderer<ButterflyEntity, ButterflyModel<ButterflyEntity>> {
    private static final ResourceLocation[] TEXTURES = {
        WildlifeMod.loc("textures/entity/butterfly/butterfly_orange.png"),
        WildlifeMod.loc("textures/entity/butterfly/butterfly_teal.png"),
        WildlifeMod.loc("textures/entity/butterfly/butterfly_yellow.png"),
        WildlifeMod.loc("textures/entity/butterfly/butterfly_green.png"),
        WildlifeMod.loc("textures/entity/butterfly/butterfly_pink.png")
    };
    
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel<>(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.1F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(ButterflyEntity entity) {
        return TEXTURES[entity.getId() % TEXTURES.length];
    }
}
