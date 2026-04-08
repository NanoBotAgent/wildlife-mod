package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.OtterModel;
import com.wildlife.mod.entity.OtterEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OtterRenderer extends MobRenderer<OtterEntity, OtterModel<OtterEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/otter/otter.png");

    public OtterRenderer(EntityRendererProvider.Context context) {
        super(context, new OtterModel<>(context.bakeLayer(ModModelLayers.OTTER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(OtterEntity entity) {
        return TEXTURE;
    }
}
