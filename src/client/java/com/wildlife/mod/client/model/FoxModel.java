package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Fox model — research-backed locomotion and idle behaviors.
 */
public class FoxModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightFrontUpperLeg, rightFrontLowerLeg;
    private final ModelPart leftFrontUpperLeg, leftFrontLowerLeg;
    private final ModelPart rightHindUpperLeg, rightHindLowerLeg;
    private final ModelPart leftHindUpperLeg, leftHindLowerLeg;
    private final ModelPart rightEar, leftEar;

    public FoxModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.neck = body.getChild("neck");
        this.head = neck.getChild("head");
        this.rightFrontUpperLeg = body.getChild("right_front_upper_leg");
        this.rightFrontLowerLeg = rightFrontUpperLeg.getChild("right_front_lower_leg");
        this.leftFrontUpperLeg = body.getChild("left_front_upper_leg");
        this.leftFrontLowerLeg = leftFrontUpperLeg.getChild("left_front_lower_leg");
        this.rightHindUpperLeg = body.getChild("right_hind_upper_leg");
        this.rightHindLowerLeg = rightHindUpperLeg.getChild("right_hind_lower_leg");
        this.leftHindUpperLeg = body.getChild("left_hind_upper_leg");
        this.leftHindLowerLeg = leftHindUpperLeg.getChild("left_hind_lower_leg");
        this.tail = body.getChild("tail");
        this.rightEar = head.getChild("right_ear");
        this.leftEar = head.getChild("left_ear");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-3.0f, -6f, -7.0f, 6f, 6f, 14f),
            PartPose.offset(0, 14f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(0, 15)
                .addBox(-2.0f, -5f, -2.0f, 4f, 5f, 4f),
            PartPose.offsetAndRotation(0, -5f, -6.0f, 0.3f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(10, 0).addBox(-2.0f, -4f, -5f, 4f, 4f, 5f)
                .texOffs(10, 6).addBox(-1.0f, 0.0f, -7f, 2f, 2f, 2f),
            PartPose.offset(0, -4f, -1.0f));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -2f, -1f, 2f, 2f, 2f),
            PartPose.offsetAndRotation(-2f, -3f, -3f, 0, 0, -0.15f));
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -2f, -1f, 2f, 2f, 2f),
            PartPose.offsetAndRotation(2f, -3f, -3f, 0, 0, 0.15f));

        PartDefinition rfU = body.addOrReplaceChild("right_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.0f, 0, -1.0f, 2f, 6f, 2f),
            PartPose.offset(-2f, 0f, -5f));
        rfU.addOrReplaceChild("right_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 8).addBox(-0.5f, 0, -0.5f, 1f, 4f, 1f),
            PartPose.offset(0, 6f, 0));

        PartDefinition lfU = body.addOrReplaceChild("left_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.0f, 0, -1.0f, 2f, 6f, 2f),
            PartPose.offset(2f, 0f, -5f));
        lfU.addOrReplaceChild("left_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 8).addBox(-0.5f, 0, -0.5f, 1f, 4f, 1f),
            PartPose.offset(0, 6f, 0));

        PartDefinition rhU = body.addOrReplaceChild("right_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 14).addBox(-1.0f, 0, -1.0f, 2f, 6f, 2f),
            PartPose.offset(-2f, 0f, 5f));
        rhU.addOrReplaceChild("right_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 22).addBox(-0.5f, 0, -0.5f, 1f, 4f, 1f),
            PartPose.offset(0, 6f, 0));

        PartDefinition lhU = body.addOrReplaceChild("left_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 14).addBox(-1.0f, 0, -1.0f, 2f, 6f, 2f),
            PartPose.offset(2f, 0f, 5f));
        lhU.addOrReplaceChild("left_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 22).addBox(-0.5f, 0, -0.5f, 1f, 4f, 1f),
            PartPose.offset(0, 6f, 0));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 17).addBox(-1.5f, -1.5f, 0, 3f, 3f, 8f),
            PartPose.offset(0, -4f, 6.0f));


        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        // Elegant diagonal walk — precise foot placement, head nods with rear leg
        float sp = 0.65f;
        this.rightFrontUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.5f*ls);
        this.leftFrontUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.5f*ls);
        this.rightHindUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.45f*ls);
        this.leftHindUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.45f*ls);
        this.rightFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.35f*ls));
        this.leftFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.35f*ls));
        this.rightHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls));
        this.leftHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.4f*ls));
        // Head nod — characteristic fox trot bob
        this.neck.xRot = 0.3f + (float)(Math.sin(wa*sp)*0.1f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.08f*ls);
        // Tail held horizontal, slight sway
        this.tail.yRot = (float)(Math.sin(age*0.4f)*0.15f);
        this.tail.xRot = (float)(Math.sin(wa*sp)*0.05f*ls);
        // Ears alert — forward-facing
        this.rightEar.zRot = -0.2f + (float)(Math.sin(age*0.6f)*0.08f);
        this.leftEar.zRot = 0.2f + (float)(Math.sin(age*0.55f+1f)*0.08f);

        if (ls < 0.01f) {
            // Head tilt — foxes tilt head when listening/investigating
            this.head.zRot = (float)(Math.sin(age*0.06f)*0.25f);
            this.head.yRot = (float)(Math.sin(age*0.08f)*0.15f);
            // Ears forward when attentive, back when relaxed
            float earCycle = (float)(Math.sin(age*0.05f)*0.5f+0.5f);
            this.rightEar.zRot = -0.2f + earCycle*0.15f;
            this.leftEar.zRot = 0.2f - earCycle*0.15f;
            // Tail raised when alert, lowered when calm
            this.tail.xRot = (float)(Math.sin(age*0.1f)*0.1f) + 0.1f;
            this.tail.yRot = (float)(Math.sin(age*0.2f)*0.15f);
            // Occasional paw-licking groom
            float groomCycle = (float)(Math.max(0, Math.sin(age*0.03f)-0.7f)*3.33f);
            this.neck.xRot = 0.3f + groomCycle*0.5f;

        }
    }
}