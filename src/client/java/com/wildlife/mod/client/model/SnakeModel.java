package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Snake model - limbless reptile with segmented body and head.
 */
public class SnakeModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;

    public SnakeModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.tail = body.getChild("tail");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.0F, -1.5F, -10.0F, 4.0F, 2.0F, 16.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 22.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 18)
                .addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 4.0F) // head
                .texOffs(16, 18)
                .addBox(-1.0F, -0.5F, -6.0F, 2.0F, 1.0F, 2.0F), // snout
            PartPose.offset(0.0F, -0.5F, -10.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 24)
                .addBox(-1.0F, -1.0F, 0.0F, 2.0F, 1.5F, 8.0F),
            PartPose.offset(0.0F, 0.0F, 6.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimationPos;
        float limbSwing = state.walkAnimationSpeed;

        // Snake slither - sinusoidal body wave
        this.body.yRot = (float)(Math.sin(walkAnim * 0.8F) * 0.3F * limbSwing);
        this.head.yRot = (float)(Math.sin(walkAnim * 0.8F + 0.5F) * 0.2F * limbSwing);
        this.tail.yRot = (float)(Math.sin(walkAnim * 0.8F - 0.5F) * 0.4F * limbSwing);

        // Subtle idle sway
        if (limbSwing < 0.01F) {
            this.body.yRot = (float)(Math.sin(state.ageInTicks * 0.3F) * 0.05F);
            this.tail.yRot = (float)(Math.sin(state.ageInTicks * 0.3F + 1.0F) * 0.1F);
        }
    }
}
