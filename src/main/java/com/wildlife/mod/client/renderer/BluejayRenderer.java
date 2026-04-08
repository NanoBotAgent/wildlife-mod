package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.BluejayModel;
import com.wildlife.mod.entity.BluejayEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BluejayRenderer extends MobRenderer<BluejayEntity, BluejayModel<BluejayEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/bird/bluejay.png");
    
    public BluejayRenderer(EntityRendererProvider.Context context) {
        super(context, new BluejayModel<>(context.bakeLayer(BluejayModel.LAYER_LOCATION)), 0.2F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(BluejayEntity entity) {
        return TEXTURE;
    }
}
