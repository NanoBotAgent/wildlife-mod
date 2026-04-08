package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.BadgerModel;
import com.wildlife.mod.entity.BadgerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BadgerRenderer extends MobRenderer<BadgerEntity, BadgerModel<BadgerEntity>> {
    private static final ResourceLocation TEXTURE = 
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/badger/badger.png");

    public BadgerRenderer(EntityRendererProvider.Context context) {
        super(context, new BadgerModel<>(context.bakeLayer(ModModelLayers.BADGER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(BadgerEntity entity) {
        return TEXTURE;
    }
}
