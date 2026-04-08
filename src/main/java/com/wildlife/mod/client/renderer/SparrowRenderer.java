package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.SparrowModel;
import com.wildlife.mod.entity.SparrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SparrowRenderer extends MobRenderer<SparrowEntity, SparrowModel<SparrowEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/bird/sparrow.png");
    
    public SparrowRenderer(EntityRendererProvider.Context context) {
        super(context, new SparrowModel<>(context.bakeLayer(SparrowModel.LAYER_LOCATION)), 0.2F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(SparrowEntity entity) {
        return TEXTURE;
    }
}
