package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Owl model — round body, large forward-facing eyes, ear tufts, talons.
 * Real owl: silent flight, head rotation up to 270 degrees,
 * perching with talons, head bobbing when focused.
 */
public class OwlModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, head, rightWing, leftWing, tail;
    private final ModelPart rightLeg, leftLeg;
    private final ModelPart rightEarTuft, leftEarTuft;

    public OwlModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.tail = body.getChild("tail");
        this.rightLeg = body.getChild("right_leg");
        this.leftLeg = body.getChild("left_leg");
        this.rightEarTuft = head.getChild("right_ear_tuft");
        this.leftEarTuft = head.getChild("left_ear_tuft");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-4f, -7f, -4f, 8f, 8f, 7f),
            PartPose.offset(0, 16f, 0));

        PartDefinition head = body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 15).addBox(-3.5f, -3.5f, -3f, 7f, 5f, 5f)
                .texOffs(22, 15).addBox(-1f, -0.5f, -5f, 2f, 1f, 2f),
            PartPose.offset(0, -6.5f, -2f));

        head.addOrReplaceChild("right_ear_tuft",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -3f, 0, 1f, 3f, 1f),
            PartPose.offsetAndRotation(-2.5f, -3f, -2f, 0, 0, -0.2f));
        head.addOrReplaceChild("left_ear_tuft",
            CubeListBuilder.create().texOffs(0, 0).addBox(0, -3f, 0, 1f, 3f, 1f),
            PartPose.offsetAndRotation(2.5f, -3f, -2f, 0, 0, 0.2f));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create().texOffs(30, 0).addBox(-1f, -1f, -1f, 1f, 7f, 6f),
            PartPose.offset(-4f, -5f, -2f));
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create().texOffs(30, 0).addBox(0, -1f, -1f, 1f, 7f, 6f),
            PartPose.offset(4f, -5f, -2f));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 25).addBox(-2.5f, -2f, 0, 5f, 3f, 3f),
            PartPose.offset(0, -3f, 3f));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(44, 0).addBox(-1f, 0, -1f, 2f, 5f, 2f),
            PartPose.offset(-2f, 1f, 1f));
        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(44, 0).addBox(-1f, 0, -1f, 2f, 5f, 2f),
            PartPose.offset(2f, 1f, 1f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float age = state.ageInTicks;
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;

        // Slow, silent wing beats — owls fly almost silently
        this.rightWing.zRot = (float)(Math.sin(age*1.8f)*0.5f) + 0.2f;
        this.leftWing.zRot = (float)(Math.sin(age*1.8f+Math.PI)*0.5f) - 0.2f;

        // Perching legs
        this.rightLeg.xRot = (float)(Math.sin(wa*0.5f)*0.15f*ls);
        this.leftLeg.xRot = (float)(Math.sin(wa*0.5f+Math.PI)*0.15f*ls);

        // Head rotation — owls rotate head almost fully (up to 270°)
        this.head.yRot = (float)(Math.sin(age*0.06f)*0.6f);
        this.head.zRot = (float)(Math.sin(age*0.05f)*0.15f);

        this.tail.xRot = (float)(Math.sin(age*0.3f)*0.04f);

        if (ls < 0.01f) {
            // Wide head rotation when idle — scanning for prey
            this.head.yRot = (float)(Math.sin(age*0.04f)*0.8f);
            this.head.zRot = (float)(Math.sin(age*0.06f)*0.2f);
            // Slow wing adjustment
            this.rightWing.zRot = (float)(Math.sin(age*0.5f)*0.05f) + 0.2f;
            this.leftWing.zRot = (float)(Math.sin(age*0.5f+Math.PI)*0.05f) - 0.2f;
            // Ear tufts shift
            this.rightEarTuft.zRot = -0.2f + (float)(Math.sin(age*0.1f)*0.05f);
            this.leftEarTuft.zRot = 0.2f + (float)(Math.sin(age*0.1f+1f)*0.05f);
        }
    }
}