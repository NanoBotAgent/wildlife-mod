package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.RaccoonModel;
import com.wildlife.mod.entity.RaccoonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RaccoonRenderer extends MobRenderer<RaccoonEntity, RaccoonModel<RaccoonEntity>> {
    private static final ResourceLocation TEXTURE = 
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/raccoon/raccoon.png");

    public RaccoonRenderer(EntityRendererProvider.Context context) {
        super(context, new RaccoonModel<>(context.bakeLayer(ModModelLayers.RACCOON)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(RaccoonEntity entity) {
        return TEXTURE;
    }
}
