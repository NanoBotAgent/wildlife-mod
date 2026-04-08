package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.BeeModel;
import com.wildlife.mod.entity.BeeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BeeRenderer extends MobRenderer<BeeEntity, BeeModel<BeeEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/bee/bee.png");
    
    public BeeRenderer(EntityRendererProvider.Context context) {
        super(context, new BeeModel<>(context.bakeLayer(BeeModel.LAYER_LOCATION)), 0.1F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(BeeEntity entity) {
        return TEXTURE;
    }
}
