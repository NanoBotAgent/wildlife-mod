package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.OwlModel;
import com.wildlife.mod.entity.OwlEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OwlRenderer extends MobRenderer<OwlEntity, OwlModel<OwlEntity>> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/owl/owl.png");
    private static final ResourceLocation TEXTURE_BARN = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/owl/owl_barn.png");
    private static final ResourceLocation TEXTURE_SNOWY = 
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/owl/owl_snowy.png");

    public OwlRenderer(EntityRendererProvider.Context context) {
        super(context, new OwlModel<>(context.bakeLayer(ModModelLayers.OWL)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(OwlEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> TEXTURE_BARN;
            case 2 -> TEXTURE_SNOWY;
            default -> TEXTURE;
        };
    }
}
