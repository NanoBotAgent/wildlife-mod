package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.MeerkatModel;
import com.wildlife.mod.entity.MeerkatEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MeerkatRenderer extends MobRenderer<MeerkatEntity, MeerkatModel<MeerkatEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/meerkat/meerkat.png");

    public MeerkatRenderer(EntityRendererProvider.Context context) {
        super(context, new MeerkatModel<>(context.bakeLayer(ModModelLayers.MEERKAT)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(MeerkatEntity entity) {
        return TEXTURE;
    }
}
