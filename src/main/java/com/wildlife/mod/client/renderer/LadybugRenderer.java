package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.LadybugModel;
import com.wildlife.mod.entity.LadybugEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LadybugRenderer extends MobRenderer<LadybugEntity, LadybugModel<LadybugEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/ladybug/ladybug.png");
    
    public LadybugRenderer(EntityRendererProvider.Context context) {
        super(context, new LadybugModel<>(context.bakeLayer(LadybugModel.LAYER_LOCATION)), 0.1F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(LadybugEntity entity) {
        return TEXTURE;
    }
}
