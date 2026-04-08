package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.OstrichModel;
import com.wildlife.mod.entity.OstrichEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OstrichRenderer extends MobRenderer<OstrichEntity, OstrichModel<OstrichEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/ostrich/ostrich.png");

    public OstrichRenderer(EntityRendererProvider.Context context) {
        super(context, new OstrichModel<>(context.bakeLayer(ModModelLayers.OSTRICH)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(OstrichEntity entity) {
        return TEXTURE;
    }
}
