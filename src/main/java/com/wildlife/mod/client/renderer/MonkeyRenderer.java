package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.MonkeyModel;
import com.wildlife.mod.entity.MonkeyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MonkeyRenderer extends MobRenderer<MonkeyEntity, MonkeyModel<MonkeyEntity>> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/monkey/monkey_brown.png"),
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/monkey/monkey_black.png"),
        ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "textures/entity/monkey/monkey_golden.png")
    };

    public MonkeyRenderer(EntityRendererProvider.Context context) {
        super(context, new MonkeyModel<>(context.bakeLayer(com.wildlife.mod.client.ModModelLayers.MONKEY)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(MonkeyEntity entity) {
        int variant = entity.getVariant();
        if (variant < 0 || variant >= TEXTURES.length) {
            variant = 0;
        }
        return TEXTURES[variant];
    }
}
