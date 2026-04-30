package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Deer model — research-backed locomotion and idle behaviors.
 */
public class DeerModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightFrontUpperLeg, rightFrontLowerLeg;
    private final ModelPart leftFrontUpperLeg, leftFrontLowerLeg;
    private final ModelPart rightHindUpperLeg, rightHindLowerLeg;
    private final ModelPart leftHindUpperLeg, leftHindLowerLeg;
    private final ModelPart rightEar, leftEar;
    private final ModelPart rightAntler;
    private final ModelPart leftAntler;

    public DeerModel(ModelPart root) {
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
        this.rightAntler = head.getChild("right_antler");
        this.leftAntler = head.getChild("left_antler");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0f, -8f, -9.0f, 8f, 8f, 18f),
            PartPose.offset(0, 14f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(0, 19)
                .addBox(-2.5f, -6f, -2.5f, 5f, 6f, 5f),
            PartPose.offsetAndRotation(0, -7f, -8.0f, 0.4f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(12, 0).addBox(-2.5f, -5f, -6f, 5f, 5f, 6f)
                .texOffs(12, 7).addBox(-1.5f, -0.5f, -8f, 3f, 3f, 2f),
            PartPose.offset(0, -5f, -1.5f));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -3f, -1f, 2f, 3f, 2f),
            PartPose.offsetAndRotation(-2f, -4f, -3f, 0, 0, -0.15f));
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -3f, -1f, 2f, 3f, 2f),
            PartPose.offsetAndRotation(2f, -4f, -3f, 0, 0, 0.15f));

        PartDefinition rfU = body.addOrReplaceChild("right_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(-3f, 0f, -6f));
        rfU.addOrReplaceChild("right_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 9).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        PartDefinition lfU = body.addOrReplaceChild("left_front_upper_leg",
            CubeListBuilder.create().texOffs(32, 0).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(3f, 0f, -6f));
        lfU.addOrReplaceChild("left_front_lower_leg",
            CubeListBuilder.create().texOffs(32, 9).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        PartDefinition rhU = body.addOrReplaceChild("right_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 16).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(-3f, 0f, 6f));
        rhU.addOrReplaceChild("right_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 25).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        PartDefinition lhU = body.addOrReplaceChild("left_hind_upper_leg",
            CubeListBuilder.create().texOffs(32, 16).addBox(-1.5f, 0, -1.5f, 3f, 7f, 3f),
            PartPose.offset(3f, 0f, 6f));
        lhU.addOrReplaceChild("left_hind_lower_leg",
            CubeListBuilder.create().texOffs(32, 25).addBox(-1.0f, 0, -1.0f, 2f, 5f, 2f),
            PartPose.offset(0, 7f, 0));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 21).addBox(-1.0f, -1.0f, 0, 2f, 2f, 4f),
            PartPose.offset(0, -6f, 8.0f));

        head.addOrReplaceChild("right_antler",
            CubeListBuilder.create().texOffs(36,0).addBox(-1f,-5f,-1f,1f,5f,1f).texOffs(40,0).addBox(-2f,-6f,0,2f,2f,1f),
            PartPose.offsetAndRotation(-2.5f,-4f,-2f,0,0,-0.2f));
        head.addOrReplaceChild("left_antler",
            CubeListBuilder.create().texOffs(36,0).addBox(0,-5f,-1f,1f,5f,1f).texOffs(40,0).addBox(0,-6f,0,2f,2f,1f),
            PartPose.offsetAndRotation(2.5f,-4f,-2f,0,0,0.2f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        // Diagonal gait: RF+LH move together, LF+RH move together
        float sp = 0.6f;
        this.rightFrontUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.55f*ls);
        this.leftFrontUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.55f*ls);
        this.rightHindUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.5f*ls);
        this.leftHindUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.5f*ls);
        // Knee bend — lower legs flex more at back of stride
        this.rightFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.4f*ls));
        this.leftFrontLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls));
        this.rightHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.45f*ls));
        this.leftHindLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.45f*ls));
        // Head bob — deer head moves down as body rises in stride
        this.neck.xRot = 0.4f + (float)(Math.sin(wa*sp)*0.08f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.06f*ls);
        // Tail gentle sway
        this.tail.yRot = (float)(Math.sin(age*0.5f)*0.15f*ls);
        // Ears swivel — independent movement
        this.rightEar.zRot = -0.15f + (float)(Math.sin(age*0.7f)*0.12f);
        this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.8f+1f)*0.12f);

        if (ls < 0.01f) {
            // Ear twitching — deer constantly swivel ears to pinpoint sounds
            this.rightEar.zRot = -0.15f + (float)(Math.sin(age*1.2f)*0.25f);
            this.leftEar.zRot = 0.15f + (float)(Math.sin(age*0.9f+2f)*0.25f);
            // Tail flick — deer flick tail when calm and feeding
            this.tail.yRot = (float)(Math.sin(age*0.3f)*0.15f);
            this.tail.xRot = (float)(Math.abs(Math.sin(age*0.5f))*0.2f);
            // Head lowers for grazing then raises to check surroundings
            float grazeCycle = (float)(Math.sin(age*0.04f)*0.5f+0.5f);
            this.neck.xRot = 0.4f + grazeCycle*0.8f;
            this.head.xRot = grazeCycle*0.3f;
            // Antlers slight sway
            this.rightAntler.zRot = -0.2f + (float)(Math.sin(age*0.1f)*0.03f);
            this.leftAntler.zRot = 0.2f + (float)(Math.sin(age*0.1f+1f)*0.03f);

        }
    }
}