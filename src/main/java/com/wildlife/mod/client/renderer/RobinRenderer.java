package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.RobinModel;
import com.wildlife.mod.entity.RobinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RobinRenderer extends MobRenderer<RobinEntity, RobinModel<RobinEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/bird/robin.png");
    
    public RobinRenderer(EntityRendererProvider.Context context) {
        super(context, new RobinModel<>(context.bakeLayer(RobinModel.LAYER_LOCATION)), 0.2F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(RobinEntity entity) {
        return TEXTURE;
    }
}
