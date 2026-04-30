package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Boar model — research-backed locomotion and idle behaviors.
 */
public class BoarModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightFrontUpperLeg, rightFrontLowerLeg;
    private final ModelPart leftFrontUpperLeg, leftFrontLowerLeg;
    private final ModelPart rightHindUpperLeg, rightHindLowerLeg;
    private final ModelPart leftHindUpperLeg, leftHindLowerLeg;
    private final ModelPart rightEar, leftEar;

    public BoarModel(ModelPart root) {
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
                .addBox(-5.0f, -9f, -8.0f, 10f, 9f, 16f),
            PartPose.offset(0, 12f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(0, 19)
                .addBox(-4.0f, -4f, -2.0f, 8f, 4f, 4f),
            PartPose.offsetAndRotation(0, -8f, -7.0f, 0.25f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(14, 0).addBox(-3.5f, -5f, -6f, 7f, 5f, 6f)
                .texOffs(14, 7).addBox(-2.0f, -0.5f, -9f, 4f, 3f, 3f),
            PartPose.offset(0, -3f, -1.0f));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -2f, -1f, 2f, 2f, 2f),
            PartPose.offsetAndRotation(-3f, -4f, -3f, 0, 0, -0.15f));
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -2f, -1f, 2f, 2f, 2f),
            PartPose.offsetAndRotation(3f, -4f, -3f, 0, 0, 0.15f));

        PartDefinition rfU = body.addOrReplaceChild("right_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0, -2.0f, 4f, 6f, 4f),
            PartPose.offset(-4f, 0f, -5f));
        rfU.addOrReplaceChild("right_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 8).addBox(-1.5f, 0, -1.5f, 3f, 4f, 3f),
            PartPose.offset(0, 6f, 0));

        PartDefinition lfU = body.addOrReplaceChild("left_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0, -2.0f, 4f, 6f, 4f),
            PartPose.offset(4f, 0f, -5f));
        lfU.addOrReplaceChild("left_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 8).addBox(-1.5f, 0, -1.5f, 3f, 4f, 3f),
            PartPose.offset(0, 6f, 0));

        PartDefinition rhU = body.addOrReplaceChild("right_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 14).addBox(-2.0f, 0, -2.0f, 4f, 6f, 4f),
            PartPose.offset(-4f, 0f, 5f));
        rhU.addOrReplaceChild("right_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 22).addBox(-1.5f, 0, -1.5f, 3f, 4f, 3f),
            PartPose.offset(0, 6f, 0));

        PartDefinition lhU = body.addOrReplaceChild("left_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 14).addBox(-2.0f, 0, -2.0f, 4f, 6f, 4f),
            PartPose.offset(4f, 0f, 5f));
        lhU.addOrReplaceChild("left_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 22).addBox(-1.5f, 0, -1.5f, 3f, 4f, 3f),
            PartPose.offset(0, 6f, 0));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 21).addBox(-1.5f, -1.5f, 0, 3f, 3f, 3f),
            PartPose.offset(0, -7f, 7.0f));


        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        // Heavy deliberate walk — powerful shoulder drive
        float sp = 0.5f;
        this.rightFrontUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.45f*ls);
        this.leftFrontUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.45f*ls);
        this.rightHindUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls);
        this.leftHindUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.4f*ls);
        this.rightFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.3f*ls));
        this.leftFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.3f*ls));
        this.rightHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.35f*ls));
        this.leftHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.35f*ls));
        // Body lurch — heavy shoulder roll
        this.body.zRot = (float)(Math.sin(wa*sp)*0.04f*ls);
        this.body.xRot = (float)(Math.sin(wa*sp*2)*0.02f*ls);
        // Head bobs with shoulder drive
        this.neck.xRot = 0.25f + (float)(Math.sin(wa*sp)*0.06f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.08f*ls);
        // Tail short wag
        this.tail.yRot = (float)(Math.sin(age*0.6f)*0.2f*ls);
        // Ear twitch
        this.rightEar.zRot = -0.15f + (float)(Math.sin(age*0.5f)*0.1f);
        this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.6f+1f)*0.1f);

        if (ls < 0.01f) {
            // Rooting — snout pushes down to dig
            float rootCycle = (float)(Math.sin(age*0.08f)*0.5f+0.5f);
            this.neck.xRot = 0.25f + rootCycle*0.6f;
            this.head.xRot = rootCycle*0.4f;
            // Snout side-to-side while rooting
            this.head.yRot = (float)(Math.sin(age*0.15f)*0.2f);
            // Ear pivoting while listening
            this.rightEar.zRot = -0.15f + (float)(Math.sin(age*0.4f)*0.15f);
            this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.35f+1f)*0.15f);
            this.tail.yRot = (float)(Math.sin(age*0.25f)*0.1f);

        }
    }
}