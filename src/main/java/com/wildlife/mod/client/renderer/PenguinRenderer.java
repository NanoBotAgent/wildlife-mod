package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.PenguinModel;
import com.wildlife.mod.entity.PenguinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PenguinRenderer extends MobRenderer<PenguinEntity, PenguinModel<PenguinEntity>> {
    private static final ResourceLocation TEXTURE = 
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/penguin/penguin.png");

    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel<>(context.bakeLayer(ModModelLayers.PENGUIN)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(PenguinEntity entity) {
        return TEXTURE;
    }
}
