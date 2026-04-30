package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Beaver model — research-backed locomotion and idle behaviors.
 */
public class BeaverModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightFrontUpperLeg, rightFrontLowerLeg;
    private final ModelPart leftFrontUpperLeg, leftFrontLowerLeg;
    private final ModelPart rightHindUpperLeg, rightHindLowerLeg;
    private final ModelPart leftHindUpperLeg, leftHindLowerLeg;
    private final ModelPart rightEar, leftEar;

    public BeaverModel(ModelPart root) {
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
                .addBox(-3.5f, -6f, -7.0f, 7f, 6f, 14f),
            PartPose.offset(0, 15f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(0, 15)
                .addBox(-2.5f, -4f, -1.5f, 5f, 4f, 3f),
            PartPose.offsetAndRotation(0, -5f, -6.0f, 0.15f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(11, 0).addBox(-2.5f, -4f, -5f, 5f, 4f, 5f)
                .texOffs(11, 6).addBox(-1.5f, 0.0f, -7f, 3f, 2f, 2f),
            PartPose.offset(0, -3f, -0.5f));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1f, -1f, 2f, 1f, 2f),
            PartPose.offsetAndRotation(-2f, -3f, -3f, 0, 0, -0.15f));
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1f, -1f, 2f, 1f, 2f),
            PartPose.offsetAndRotation(2f, -3f, -3f, 0, 0, 0.15f));

        PartDefinition rfU = body.addOrReplaceChild("right_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.0f, 0, -1.0f, 2f, 4f, 2f),
            PartPose.offset(-3f, 0f, -4f));
        rfU.addOrReplaceChild("right_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 6).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 4f, 0));

        PartDefinition lfU = body.addOrReplaceChild("left_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.0f, 0, -1.0f, 2f, 4f, 2f),
            PartPose.offset(3f, 0f, -4f));
        lfU.addOrReplaceChild("left_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 6).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 4f, 0));

        PartDefinition rhU = body.addOrReplaceChild("right_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 11).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(-3f, 0f, 4f));
        rhU.addOrReplaceChild("right_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 18).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 5f, 0));

        PartDefinition lhU = body.addOrReplaceChild("left_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 11).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(3f, 0f, 4f));
        lhU.addOrReplaceChild("left_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 18).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(0, 5f, 0));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 17).addBox(-3.0f, -0.5f, 0, 6f, 1f, 7f),
            PartPose.offset(0, -2f, 6.0f));


        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        // Waddling gait — beavers are awkward on land
        float sp = 0.5f;
        this.rightFrontUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.35f*ls);
        this.leftFrontUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.35f*ls);
        this.rightHindUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.3f*ls);
        this.leftHindUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.3f*ls);
        this.rightFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.25f*ls));
        this.leftFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.25f*ls));
        this.rightHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.2f*ls));
        this.leftHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.2f*ls));
        // Body sways side to side — waddle
        this.body.zRot = (float)(Math.sin(wa*sp)*0.05f*ls);
        this.neck.xRot = 0.15f + (float)(Math.sin(wa*sp)*0.04f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.03f*ls);
        // Flat tail drags then slaps
        this.tail.xRot = (float)(Math.sin(wa*sp)*0.08f*ls);
        this.tail.yRot = (float)(Math.sin(age*0.3f)*0.1f);
        this.rightEar.zRot = -0.1f + (float)(Math.sin(age*0.3f)*0.05f);
        this.leftEar.zRot = 0.1f + (float)(Math.sin(age*0.35f+1f)*0.05f);

        if (ls < 0.01f) {
            // Grooming — beaver licks and cleans fur
            float groomCycle = (float)(Math.sin(age*0.06f)*0.5f+0.5f);
            this.neck.xRot = 0.15f + groomCycle*0.4f;
            this.head.xRot = groomCycle*0.2f;
            // Tail slap — beaver slaps tail on water as warning
            float slapCycle = (float)(Math.max(0, Math.sin(age*0.08f)-0.9f)*10f);
            this.tail.xRot = -0.5f*slapCycle;
            // Chewing — beaver constantly gnaws
            this.head.xRot += (float)(Math.sin(age*3f)*0.03f);
            this.rightEar.zRot = -0.1f + (float)(Math.sin(age*0.25f)*0.06f);
            this.leftEar.zRot = 0.1f + (float)(Math.sin(age*0.25f+1f)*0.06f);

        }
    }
}