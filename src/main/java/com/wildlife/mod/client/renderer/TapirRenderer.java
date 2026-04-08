package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.TapirModel;
import com.wildlife.mod.entity.TapirEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TapirRenderer extends MobRenderer<TapirEntity, TapirModel<TapirEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/tapir/tapir.png");

    public TapirRenderer(EntityRendererProvider.Context context) {
        super(context, new TapirModel<>(context.bakeLayer(ModModelLayers.TAPIR)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(TapirEntity entity) {
        return TEXTURE;
    }
}
