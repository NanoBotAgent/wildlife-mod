package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Otter model — research-backed locomotion and idle behaviors.
 */
public class OtterModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightFrontUpperLeg, rightFrontLowerLeg;
    private final ModelPart leftFrontUpperLeg, leftFrontLowerLeg;
    private final ModelPart rightHindUpperLeg, rightHindLowerLeg;
    private final ModelPart leftHindUpperLeg, leftHindLowerLeg;
    private final ModelPart rightEar, leftEar;

    public OtterModel(ModelPart root) {
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
                .addBox(-2.5f, -5f, -8.0f, 5f, 5f, 16f),
            PartPose.offset(0, 15f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(0, 15)
                .addBox(-2.0f, -3f, -1.5f, 4f, 3f, 3f),
            PartPose.offsetAndRotation(0, -4f, -7.0f, 0.15f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(9, 0).addBox(-2.0f, -3f, -4f, 4f, 3f, 4f)
                .texOffs(9, 5).addBox(-1.0f, 0.0f, -6f, 2f, 2f, 2f),
            PartPose.offset(0, -2f, -0.5f));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1f, -1f, 2f, 1f, 2f),
            PartPose.offsetAndRotation(-1f, -2f, -2f, 0, 0, -0.15f));
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1f, -1f, 2f, 1f, 2f),
            PartPose.offsetAndRotation(1f, -2f, -2f, 0, 0, 0.15f));

        PartDefinition rfU = body.addOrReplaceChild("right_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.0f, 0, -1.0f, 2f, 4f, 2f),
            PartPose.offset(-2f, 0f, -5f));
        rfU.addOrReplaceChild("right_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 6).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 4f, 0));

        PartDefinition lfU = body.addOrReplaceChild("left_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.0f, 0, -1.0f, 2f, 4f, 2f),
            PartPose.offset(2f, 0f, -5f));
        lfU.addOrReplaceChild("left_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 6).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 4f, 0));

        PartDefinition rhU = body.addOrReplaceChild("right_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 11).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(-2f, 0f, 5f));
        rhU.addOrReplaceChild("right_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 18).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 5f, 0));

        PartDefinition lhU = body.addOrReplaceChild("left_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 11).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(2f, 0f, 5f));
        lhU.addOrReplaceChild("left_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 18).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 5f, 0));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 17).addBox(-1.5f, -1.5f, 0, 3f, 3f, 8f),
            PartPose.offset(0, -3f, 7.0f));


        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        // Slender body, low gait — otter's short legs and long spine
        float sp = 0.55f;
        this.rightFrontUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.4f*ls);
        this.leftFrontUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls);
        this.rightHindUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.35f*ls);
        this.leftHindUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.35f*ls);
        this.rightFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.3f*ls));
        this.leftFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.3f*ls));
        this.rightHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.25f*ls));
        this.leftHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.25f*ls));
        // Body undulates — spine flexion like swimming motion
        this.body.yRot = (float)(Math.sin(wa*sp)*0.06f*ls);
        this.neck.xRot = 0.15f + (float)(Math.sin(wa*sp)*0.05f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.04f*ls);
        // Long thick tail waves
        this.tail.yRot = (float)(Math.sin(age*0.5f)*0.2f);
        this.rightEar.zRot = -0.1f + (float)(Math.sin(age*0.4f)*0.06f);
        this.leftEar.zRot = 0.1f + (float)(Math.sin(age*0.45f+1f)*0.06f);

        if (ls < 0.01f) {
            // Floating on back — otter's signature playful pose
            float floatCycle = (float)(Math.sin(age*0.05f)*0.5f+0.5f);
            this.body.xRot = 0.2f + floatCycle*0.4f;
            this.body.y = 15f - floatCycle*1f;
            // Paws up while floating
            this.rightFrontUpperLeg.xRot = -1.0f;
            this.leftFrontUpperLeg.xRot = -1.0f;
            this.rightFrontUpperLeg.zRot = -0.3f;
            this.leftFrontUpperLeg.zRot = 0.3f;
            // Head looks around while floating
            this.head.yRot = (float)(Math.sin(age*0.07f)*0.3f);
            this.head.zRot = (float)(Math.sin(age*0.06f)*0.1f);
            this.neck.xRot = 0.15f + floatCycle*0.3f;
            // Tail sculls gently
            this.tail.yRot = (float)(Math.sin(age*0.4f)*0.15f);

        }
    }
}