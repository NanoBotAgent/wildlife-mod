package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.BeaverModel;
import com.wildlife.mod.entity.BeaverEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BeaverRenderer extends MobRenderer<BeaverEntity, BeaverModel<BeaverEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/beaver/beaver.png");

    public BeaverRenderer(EntityRendererProvider.Context context) {
        super(context, new BeaverModel<>(context.bakeLayer(ModModelLayers.BEAVER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(BeaverEntity entity) {
        return TEXTURE;
    }
}
