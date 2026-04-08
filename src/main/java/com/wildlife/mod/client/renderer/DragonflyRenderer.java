package com.wildlife.mod.client.renderer;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.DragonflyModel;
import com.wildlife.mod.entity.DragonflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DragonflyRenderer extends MobRenderer<DragonflyEntity, DragonflyModel<DragonflyEntity>> {
    private static final ResourceLocation[] TEXTURES = {
        WildlifeMod.loc("textures/entity/dragonfly/dragonfly_blue.png"),
        WildlifeMod.loc("textures/entity/dragonfly/dragonfly_green.png"),
        WildlifeMod.loc("textures/entity/dragonfly/dragonfly_red.png")
    };
    
    public DragonflyRenderer(EntityRendererProvider.Context context) {
        super(context, new DragonflyModel<>(context.bakeLayer(DragonflyModel.LAYER_LOCATION)), 0.1F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(DragonflyEntity entity) {
        return TEXTURES[entity.getId() % TEXTURES.length];
    }
}
