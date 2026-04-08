package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.MarmotModel;
import com.wildlife.mod.entity.MarmotEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MarmotRenderer extends MobRenderer<MarmotEntity, MarmotModel<MarmotEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/marmot/marmot.png");

    public MarmotRenderer(EntityRendererProvider.Context context) {
        super(context, new MarmotModel<>(context.bakeLayer(ModModelLayers.MARMOT)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(MarmotEntity entity) {
        return TEXTURE;
    }
}
