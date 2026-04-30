package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Bee model — rapid wing beats, hovering, waggle dance.
 * Real bee: hovering flight, waggle dance for communication,
 * pollen collection, wing trembling for heat generation.
 */
public class BeeModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart rightWing, leftWing;
    private final ModelPart rightAntenna, leftAntenna;
    private final ModelPart stinger;

    public BeeModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.rightAntenna = body.getChild("right_antenna");
        this.leftAntenna = body.getChild("left_antenna");
        this.stinger = body.getChild("stinger");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1.5f, -1.5f, -3f, 3f, 3f, 7f),
            PartPose.offset(0, 21f, 0));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create().texOffs(0, 10).addBox(-5f, 0, -2f, 5f, 3f, 2f),
            PartPose.offset(-1.5f, -1.5f, 0));
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create().texOffs(0, 10).addBox(0, 0, -2f, 5f, 3f, 2f),
            PartPose.offset(1.5f, -1.5f, 0));

        body.addOrReplaceChild("right_antenna",
            CubeListBuilder.create().texOffs(14, 10).addBox(-0.5f, -3f, 0, 1f, 3f, 0),
            PartPose.offsetAndRotation(-0.5f, -1.5f, -3f, 0.2f, 0, -0.2f));
        body.addOrReplaceChild("left_antenna",
            CubeListBuilder.create().texOffs(14, 10).addBox(-0.5f, -3f, 0, 1f, 3f, 0),
            PartPose.offsetAndRotation(0.5f, -1.5f, -3f, 0.2f, 0, 0.2f));

        body.addOrReplaceChild("stinger",
            CubeListBuilder.create().texOffs(20, 0).addBox(-0.5f, -0.5f, 0, 1f, 1f, 2f),
            PartPose.offset(0, 0, 4f));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float age = state.ageInTicks;
        float ls = state.walkAnimationSpeed;

        // Rapid wing beats — bees beat wings ~230 times/second (abstracted)
        float speed = ls > 0.01f ? 5f : 1.5f;
        this.rightWing.zRot = (float)(Math.sin(age*speed)*0.6f);
        this.leftWing.zRot = (float)(Math.sin(age*speed+Math.PI)*0.6f);

        // Hovering body adjustments
        this.body.y = 21f + (float)(Math.sin(age*speed*1.5f)*0.3f*ls);

        // Antennae scanning
        this.rightAntenna.zRot = 0.2f + (float)(Math.sin(age*3f)*0.1f);
        this.leftAntenna.zRot = -0.2f + (float)(Math.sin(age*3f+Math.PI)*0.1f);

        if (ls < 0.01f) {
            // Resting: slow wing tremble, grooming
            this.rightWing.zRot = (float)(Math.sin(age*0.8f)*0.08f) + 0.1f;
            this.leftWing.zRot = (float)(Math.sin(age*0.8f+Math.PI)*0.08f) - 0.1f;
            this.body.zRot = (float)(Math.sin(age*0.3f)*0.05f);
            // Waggle dance — side-to-side body oscillation
            float waggle = (float)(Math.max(0, Math.sin(age*0.08f)-0.7f)*3.33f);
            this.body.yRot = waggle*0.15f;
        } else {
            this.body.yRot = 0;
        }
    }
}