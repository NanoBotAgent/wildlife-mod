package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Dragonfly model — elongated body, two independent wing pairs, compound eyes.
 * Real dragonfly: hovering, darting flight, perching with wings spread,
 * obelisk posture (pointing abdomen at sun for cooling).
 */
public class DragonflyModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart thorax, abdomen;
    private final ModelPart rightForeWing, rightHindWing;
    private final ModelPart leftForeWing, leftHindWing;
    private final ModelPart eyeRight, eyeLeft;

    public DragonflyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.thorax = body.getChild("thorax");
        this.abdomen = body.getChild("abdomen");
        this.rightForeWing = thorax.getChild("right_fore_wing");
        this.rightHindWing = thorax.getChild("right_hind_wing");
        this.leftForeWing = thorax.getChild("left_fore_wing");
        this.leftHindWing = thorax.getChild("left_hind_wing");
        this.eyeRight = body.getChild("eye_right");
        this.eyeLeft = body.getChild("eye_left");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1f, -2f, 2f, 2f, 3f),
            PartPose.offset(0, 22f, 0));

        body.addOrReplaceChild("eye_right",
            CubeListBuilder.create().texOffs(0, 5).addBox(-0.5f, -0.5f, 0, 1f, 1f, 1f),
            PartPose.offset(-1f, -1f, -2f));
        body.addOrReplaceChild("eye_left",
            CubeListBuilder.create().texOffs(0, 5).addBox(-0.5f, -0.5f, 0, 1f, 1f, 1f),
            PartPose.offset(1f, -1f, -2f));

        PartDefinition thorax = body.addOrReplaceChild("thorax",
            CubeListBuilder.create().texOffs(10, 0).addBox(-1.5f, -1.5f, 0, 3f, 3f, 3f),
            PartPose.offset(0, 0, 1f));

        thorax.addOrReplaceChild("right_fore_wing",
            CubeListBuilder.create().texOffs(10, 6).addBox(-5f, 0, -1f, 5f, 2f, 2f),
            PartPose.offset(-1.5f, -1.5f, 1f));
        thorax.addOrReplaceChild("right_hind_wing",
            CubeListBuilder.create().texOffs(10, 6).addBox(-4f, 0, -1f, 4f, 2f, 2f),
            PartPose.offset(-1.5f, -1f, 2.5f));
        thorax.addOrReplaceChild("left_fore_wing",
            CubeListBuilder.create().texOffs(10, 6).addBox(0, 0, -1f, 5f, 2f, 2f),
            PartPose.offset(1.5f, -1.5f, 1f));
        thorax.addOrReplaceChild("left_hind_wing",
            CubeListBuilder.create().texOffs(10, 6).addBox(0, 0, -1f, 4f, 2f, 2f),
            PartPose.offset(1.5f, -1f, 2.5f));

        body.addOrReplaceChild("abdomen",
            CubeListBuilder.create().texOffs(0, 10).addBox(-1f, -1f, 0, 2f, 2f, 7f),
            PartPose.offset(0, -0.5f, 3f));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float age = state.ageInTicks;
        float ls = state.walkAnimationSpeed;

        // Dragonflies beat each wing pair independently
        float speed = ls > 0.01f ? 4f : 2f;
        this.rightForeWing.zRot = (float)(Math.sin(age*speed)*0.5f);
        this.leftForeWing.zRot = (float)(Math.sin(age*speed+Math.PI)*0.5f);
        this.rightHindWing.zRot = (float)(Math.sin(age*speed+0.3f)*0.45f);
        this.leftHindWing.zRot = (float)(Math.sin(age*speed+Math.PI+0.3f)*0.45f);

        // Abdomen undulates in flight
        this.abdomen.yRot = (float)(Math.sin(age*2f)*0.1f*ls);

        if (ls < 0.01f) {
            // Perching: wings held spread, occasional adjustment
            this.rightForeWing.zRot = 0.3f + (float)(Math.sin(age*0.4f)*0.05f);
            this.leftForeWing.zRot = -0.3f + (float)(Math.sin(age*0.4f+Math.PI)*0.05f);
            this.rightHindWing.zRot = 0.25f + (float)(Math.sin(age*0.4f)*0.05f);
            this.leftHindWing.zRot = -0.25f + (float)(Math.sin(age*0.4f+Math.PI)*0.05f);
            this.abdomen.yRot = (float)(Math.sin(age*0.15f)*0.03f);
            // Obelisk posture — abdomen points upward for cooling
            float obelisk = (float)(Math.max(0, Math.sin(age*0.03f)-0.6f)*2.5f);
            this.abdomen.xRot = obelisk*0.3f;
        } else {
            this.abdomen.xRot = 0;
        }
    }
}