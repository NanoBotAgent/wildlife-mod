package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.CrowModel;
import com.wildlife.mod.entity.CrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrowRenderer extends MobRenderer<CrowEntity, CrowModel<CrowEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/bird/crow.png");
    
    public CrowRenderer(EntityRendererProvider.Context context) {
        super(context, new CrowModel<>(context.bakeLayer(CrowModel.LAYER_LOCATION)), 0.25F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(CrowEntity entity) {
        return TEXTURE;
    }
}
