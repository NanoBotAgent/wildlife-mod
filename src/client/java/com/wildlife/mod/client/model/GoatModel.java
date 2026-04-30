package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Goat model — research-backed locomotion and idle behaviors.
 */
public class GoatModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightFrontUpperLeg, rightFrontLowerLeg;
    private final ModelPart leftFrontUpperLeg, leftFrontLowerLeg;
    private final ModelPart rightHindUpperLeg, rightHindLowerLeg;
    private final ModelPart leftHindUpperLeg, leftHindLowerLeg;
    private final ModelPart rightEar, leftEar;
    private final ModelPart rightHorn;
    private final ModelPart leftHorn;

    public GoatModel(ModelPart root) {
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
        this.rightHorn = head.getChild("right_horn");
        this.leftHorn = head.getChild("left_horn");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-3.5f, -8f, -7.0f, 7f, 8f, 14f),
            PartPose.offset(0, 12f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(0, 17)
                .addBox(-2.0f, -5f, -2.0f, 4f, 5f, 4f),
            PartPose.offsetAndRotation(0, -7f, -6.0f, 0.35f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(11, 0).addBox(-2.5f, -5f, -5f, 5f, 5f, 5f)
                .texOffs(11, 7).addBox(-1.5f, 0.0f, -7f, 3f, 2f, 2f),
            PartPose.offset(0, -4f, -1.0f));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -3f, -1f, 2f, 3f, 2f),
            PartPose.offsetAndRotation(-2f, -4f, -3f, 0, 0, -0.15f));
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -3f, -1f, 2f, 3f, 2f),
            PartPose.offsetAndRotation(2f, -4f, -3f, 0, 0, 0.15f));

        PartDefinition rfU = body.addOrReplaceChild("right_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(-2f, 0f, -5f));
        rfU.addOrReplaceChild("right_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 9).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        PartDefinition lfU = body.addOrReplaceChild("left_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(2f, 0f, -5f));
        lfU.addOrReplaceChild("left_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 9).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        PartDefinition rhU = body.addOrReplaceChild("right_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 16).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(-2f, 0f, 5f));
        rhU.addOrReplaceChild("right_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 25).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        PartDefinition lhU = body.addOrReplaceChild("left_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 16).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(2f, 0f, 5f));
        lhU.addOrReplaceChild("left_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 25).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 19).addBox(-1.0f, -1.0f, 0, 2f, 2f, 4f),
            PartPose.offset(0, -6f, 6.0f));

        head.addOrReplaceChild("right_horn",
            CubeListBuilder.create().texOffs(36,0).addBox(-1f,-4f,-1f,1f,4f,1f).texOffs(38,0).addBox(-2f,-5f,0,2f,1f,1f),
            PartPose.offsetAndRotation(-2.5f,-4f,-2f,0,0,-0.15f));
        head.addOrReplaceChild("left_horn",
            CubeListBuilder.create().texOffs(36,0).addBox(0,-4f,-1f,1f,4f,1f).texOffs(38,0).addBox(0,-5f,0,2f,1f,1f),
            PartPose.offsetAndRotation(2.5f,-4f,-2f,0,0,0.15f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        // Sure-footed diagonal gait — goats climb steep terrain
        float sp = 0.6f;
        this.rightFrontUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.5f*ls);
        this.leftFrontUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.5f*ls);
        this.rightHindUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.45f*ls);
        this.leftHindUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.45f*ls);
        this.rightFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.35f*ls));
        this.leftFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.35f*ls));
        this.rightHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls));
        this.leftHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.4f*ls));
        // Body rises slightly with each step
        this.body.y = 12f + (float)(Math.abs(Math.sin(wa*sp*2))*0.3f*ls);
        this.neck.xRot = 0.35f + (float)(Math.sin(wa*sp)*0.08f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.06f*ls);
        this.tail.yRot = (float)(Math.sin(age*0.5f)*0.15f*ls);
        this.rightEar.zRot = -0.15f + (float)(Math.sin(age*0.5f)*0.1f);
        this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.55f+1f)*0.1f);

        if (ls < 0.01f) {
            // Headbutting — goat rears head back then thrusts forward
            float headbutt = (float)(Math.max(0, Math.sin(age*0.1f)-0.8f)*5f);
            this.head.xRot = -0.5f*headbutt;
            this.neck.xRot = 0.35f + 0.3f*headbutt;
            // Looking around — turning head
            this.head.yRot = (float)(Math.sin(age*0.06f)*0.3f);
            // Horns sway
            this.rightHorn.zRot = -0.15f + (float)(Math.sin(age*0.08f)*0.05f);
            this.leftHorn.zRot = 0.15f + (float)(Math.sin(age*0.08f+1f)*0.05f);
            this.rightEar.zRot = -0.15f + (float)(Math.sin(age*0.3f)*0.12f);
            this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.35f+1f)*0.12f);
            this.tail.yRot = (float)(Math.sin(age*0.2f)*0.1f);

        }
    }
}