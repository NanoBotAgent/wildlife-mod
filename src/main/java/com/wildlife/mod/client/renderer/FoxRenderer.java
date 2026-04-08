package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.FoxModel;
import com.wildlife.mod.entity.FoxEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FoxRenderer extends MobRenderer<FoxEntity, FoxModel<FoxEntity>> {
    private static final ResourceLocation[] TEXTURES = {
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/fox/red_fox.png"),
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/fox/arctic_fox.png"),
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/fox/fennec_fox.png")
    };

    public FoxRenderer(EntityRendererProvider.Context context) {
        super(context, new FoxModel<>(context.bakeLayer(ModModelLayers.FOX)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(FoxEntity entity) {
        int variant = entity.getVariant() % TEXTURES.length;
        return TEXTURES[variant];
    }
}
