package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.ToucanModel;
import com.wildlife.mod.entity.ToucanEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ToucanRenderer extends MobRenderer<ToucanEntity, ToucanModel<ToucanEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/toucan/toucan.png");
    private static final ResourceLocation TEXTURE_KEEL = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/toucan/toucan_keel.png");
    private static final ResourceLocation TEXTURE_ARACARI = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/toucan/toucan_aracari.png");

    public ToucanRenderer(EntityRendererProvider.Context context) {
        super(context, new ToucanModel<>(context.bakeLayer(ModModelLayers.TOUCAN)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(ToucanEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> TEXTURE_KEEL;
            case 2 -> TEXTURE_ARACARI;
            default -> TEXTURE;
        };
    }
}
