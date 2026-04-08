package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.ModModelLayers;
import com.wildlife.mod.client.model.GoatModel;
import com.wildlife.mod.entity.GoatEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoatRenderer extends MobRenderer<GoatEntity, GoatModel<GoatEntity>> {
    private static final ResourceLocation[] TEXTURES = {
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/goat/mountain_goat.png"),
        new ResourceLocation(WildlifeMod.MOD_ID, "textures/entity/goat/ibex.png")
    };

    public GoatRenderer(EntityRendererProvider.Context context) {
        super(context, new GoatModel<>(context.bakeLayer(ModModelLayers.GOAT)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(GoatEntity entity) {
        int variant = entity.getVariant() % TEXTURES.length;
        return TEXTURES[variant];
    }
}
