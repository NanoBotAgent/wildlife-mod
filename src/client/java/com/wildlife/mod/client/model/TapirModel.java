package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Tapir model — research-backed locomotion and idle behaviors.
 */
public class TapirModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightFrontUpperLeg, rightFrontLowerLeg;
    private final ModelPart leftFrontUpperLeg, leftFrontLowerLeg;
    private final ModelPart rightHindUpperLeg, rightHindLowerLeg;
    private final ModelPart leftHindUpperLeg, leftHindLowerLeg;
    private final ModelPart rightEar, leftEar;

    public TapirModel(ModelPart root) {
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
                .addBox(-4.5f, -9f, -9.0f, 9f, 9f, 18f),
            PartPose.offset(0, 11f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(0, 20)
                .addBox(-3.0f, -5f, -2.5f, 6f, 5f, 5f),
            PartPose.offsetAndRotation(0, -8f, -8.0f, 0.35f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(13, 0).addBox(-2.5f, -5f, -6f, 5f, 5f, 6f)
                .texOffs(13, 7).addBox(-1.5f, -0.5f, -9f, 3f, 3f, 3f),
            PartPose.offset(0, -4f, -1.5f));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -2f, -1f, 2f, 2f, 2f),
            PartPose.offsetAndRotation(-2f, -4f, -3f, 0, 0, -0.15f));
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -2f, -1f, 2f, 2f, 2f),
            PartPose.offsetAndRotation(2f, -4f, -3f, 0, 0, 0.15f));

        PartDefinition rfU = body.addOrReplaceChild("right_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0, -2.0f, 4f, 7f, 4f),
            PartPose.offset(-3f, 0f, -6f));
        rfU.addOrReplaceChild("right_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 9).addBox(-1.5f, 0, -1.5f, 3f, 5f, 3f),
            PartPose.offset(0, 7f, 0));

        PartDefinition lfU = body.addOrReplaceChild("left_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0, -2.0f, 4f, 7f, 4f),
            PartPose.offset(3f, 0f, -6f));
        lfU.addOrReplaceChild("left_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 9).addBox(-1.5f, 0, -1.5f, 3f, 5f, 3f),
            PartPose.offset(0, 7f, 0));

        PartDefinition rhU = body.addOrReplaceChild("right_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 16).addBox(-2.0f, 0, -2.0f, 4f, 7f, 4f),
            PartPose.offset(-3f, 0f, 6f));
        rhU.addOrReplaceChild("right_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 25).addBox(-1.5f, 0, -1.5f, 3f, 5f, 3f),
            PartPose.offset(0, 7f, 0));

        PartDefinition lhU = body.addOrReplaceChild("left_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 16).addBox(-2.0f, 0, -2.0f, 4f, 7f, 4f),
            PartPose.offset(3f, 0f, 6f));
        lhU.addOrReplaceChild("left_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 25).addBox(-1.5f, 0, -1.5f, 3f, 5f, 3f),
            PartPose.offset(0, 7f, 0));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 22).addBox(-1.5f, -1.5f, 0, 3f, 3f, 4f),
            PartPose.offset(0, -7f, 8.0f));


        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        // Slow deliberate walk — snout close to ground for foraging
        float sp = 0.4f;
        this.rightFrontUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.4f*ls);
        this.leftFrontUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls);
        this.rightHindUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.35f*ls);
        this.leftHindUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.35f*ls);
        this.rightFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.3f*ls));
        this.leftFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.3f*ls));
        this.rightHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.3f*ls));
        this.leftHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.3f*ls));
        // Heavy body sway
        this.body.zRot = (float)(Math.sin(wa*sp)*0.03f*ls);
        // Neck and head — proboscis sweeps ground
        this.neck.xRot = 0.35f + (float)(Math.sin(wa*sp)*0.05f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.04f*ls);
        this.head.yRot = (float)(Math.sin(age*0.3f)*0.1f*ls);
        this.tail.yRot = (float)(Math.sin(age*0.3f)*0.1f);
        this.rightEar.zRot = -0.15f + (float)(Math.sin(age*0.4f)*0.08f);
        this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.35f+1f)*0.08f);

        if (ls < 0.01f) {
            // Sniffing and nose exploration — proboscis moves side-to-side
            this.head.yRot = (float)(Math.sin(age*0.12f)*0.25f);
            this.head.xRot = (float)(Math.sin(age*0.08f)*0.15f);
            this.neck.xRot = 0.35f + (float)(Math.sin(age*0.06f)*0.15f);
            // Browsing — head lowers to feed
            float browseCycle = (float)(Math.sin(age*0.04f)*0.5f+0.5f);
            this.neck.xRot = 0.35f + browseCycle*0.5f;
            this.head.xRot = browseCycle*0.2f;
            // Head shake — occasional quick shake
            float shake = (float)(Math.max(0, Math.sin(age*0.5f)-0.95f)*20f);
            this.head.yRot += shake*0.1f;
            this.rightEar.zRot = -0.15f + (float)(Math.sin(age*0.3f)*0.1f);
            this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.3f+1f)*0.1f);

        }
    }
}