package com.wildlife.mod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.FireflyModel;
import com.wildlife.mod.entity.FireflyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FireflyRenderer extends MobRenderer<FireflyEntity, FireflyModel<FireflyEntity>> {
    private static final ResourceLocation TEXTURE = WildlifeMod.loc("textures/entity/firefly/firefly.png");
    private static final ResourceLocation GLOW_TEXTURE = WildlifeMod.loc("textures/entity/firefly/firefly_glow.png");
    
    public FireflyRenderer(EntityRendererProvider.Context context) {
        super(context, new FireflyModel<>(context.bakeLayer(FireflyModel.LAYER_LOCATION)), 0.05F);
    }
    
    @Override
    public void render(FireflyEntity entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, yaw, partialTick, poseStack, bufferSource, packedLight);
        
        // Add glow effect when glowing
        if (entity.isGlowing()) {
            // Glow rendering would go here with custom shader
        }
    }
    
    @Override
    public ResourceLocation getTextureLocation(FireflyEntity entity) {
        return TEXTURE;
    }
}
