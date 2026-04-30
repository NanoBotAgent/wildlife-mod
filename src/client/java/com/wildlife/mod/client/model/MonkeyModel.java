package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Monkey model — brachiating climber, grooming, sitting upright.
 * Real monkey: quadrupedal walk on ground, hand-over-hand climbing,
 * sitting upright grooming self/others, scratching head, eating with hands.
 */
public class MonkeyModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, head, tail;
    private final ModelPart rightArm, rightForearm;
    private final ModelPart leftArm, leftForearm;
    private final ModelPart rightLeg, rightShin;
    private final ModelPart leftLeg, leftShin;

    public MonkeyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.tail = body.getChild("tail");
        this.rightArm = body.getChild("right_arm"); this.rightForearm = rightArm.getChild("right_forearm");
        this.leftArm = body.getChild("left_arm"); this.leftForearm = leftArm.getChild("left_forearm");
        this.rightLeg = body.getChild("right_leg"); this.rightShin = rightLeg.getChild("right_shin");
        this.leftLeg = body.getChild("left_leg"); this.leftShin = leftLeg.getChild("left_shin");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-3.5f, -5f, -5f, 7f, 8f, 10f),
            PartPose.offset(0, 13f, 0));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 18).addBox(-3f, -3.5f, -4f, 6f, 5f, 5f)
                .texOffs(22, 18).addBox(-1.5f, -0.5f, -5.5f, 3f, 2f, 1.5f),
            PartPose.offset(0, -4.5f, -4.5f));

        // Long arms for brachiating
        PartDefinition rA = body.addOrReplaceChild("right_arm",
            CubeListBuilder.create().texOffs(34, 0).addBox(-1.5f, 0, -1.5f, 3f, 6f, 3f),
            PartPose.offset(-4f, -3f, -3f));
        rA.addOrReplaceChild("right_forearm",
            CubeListBuilder.create().texOffs(34, 9).addBox(-1f, 0, -1f, 2f, 5f, 2f),
            PartPose.offset(0, 6f, 0));

        PartDefinition lA = body.addOrReplaceChild("left_arm",
            CubeListBuilder.create().texOffs(34, 0).addBox(-1.5f, 0, -1.5f, 3f, 6f, 3f),
            PartPose.offset(4f, -3f, -3f));
        lA.addOrReplaceChild("left_forearm",
            CubeListBuilder.create().texOffs(34, 9).addBox(-1f, 0, -1f, 2f, 5f, 2f),
            PartPose.offset(0, 6f, 0));

        // Shorter legs
        PartDefinition rL = body.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(46, 0).addBox(-1.5f, 0, -1.5f, 3f, 5f, 3f),
            PartPose.offset(-2.5f, 3f, 2f));
        rL.addOrReplaceChild("right_shin",
            CubeListBuilder.create().texOffs(46, 8).addBox(-1f, 0, 0.5f, 2f, 4f, 2f),
            PartPose.offset(0, 5f, -1f));

        PartDefinition lL = body.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(46, 0).addBox(-1.5f, 0, -1.5f, 3f, 5f, 3f),
            PartPose.offset(2.5f, 3f, 2f));
        lL.addOrReplaceChild("left_shin",
            CubeListBuilder.create().texOffs(46, 8).addBox(-1f, 0, 0.5f, 2f, 4f, 2f),
            PartPose.offset(0, 5f, -1f));

        // Prehensile tail — curls and grasps
        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 28).addBox(-1f, -1f, 0, 2f, 2f, 10f),
            PartPose.offset(0, -2f, 5f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Quadrupedal walk — arms and legs move diagonally
        float sp = 0.6f;
        this.rightLeg.xRot = (float)(Math.sin(wa*sp)*0.45f*ls);
        this.leftLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.45f*ls);
        this.rightShin.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.3f*ls));
        this.leftShin.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.3f*ls));
        // Arms swing opposite to legs
        this.rightArm.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.35f*ls) - 0.3f;
        this.leftArm.xRot = (float)(Math.sin(wa*sp)*0.35f*ls) - 0.3f;
        this.rightForearm.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.25f*ls));
        this.leftForearm.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.25f*ls));
        // Head bobs
        this.head.xRot = (float)(Math.sin(wa*sp)*0.06f*ls);
        // Tail waves and curls
        this.tail.yRot = (float)(Math.sin(age*0.5f)*0.3f);
        this.tail.zRot = (float)(Math.sin(age*0.4f)*0.15f);

        if (ls < 0.01f) {
            // Sitting upright grooming — picking through fur
            float groomCycle = (float)(Math.sin(age*0.06f)*0.5f+0.5f);
            this.rightArm.xRot = -1.5f + groomCycle*0.8f;
            this.leftArm.xRot = -1.5f + (1-groomCycle)*0.8f;
            this.rightArm.zRot = -0.3f + groomCycle*0.5f;
            this.leftArm.zRot = 0.3f - (1-groomCycle)*0.5f;
            this.rightForearm.xRot = -0.5f;
            this.leftForearm.xRot = -0.5f;
            // Head turns while examining fur
            this.head.zRot = (float)(Math.sin(age*0.08f)*0.2f);
            this.head.yRot = (float)(Math.sin(age*0.06f)*0.15f);
            this.tail.yRot = (float)(Math.sin(age*0.2f)*0.15f);
        } else {
            this.rightArm.zRot = 0; this.leftArm.zRot = 0;
            this.head.zRot = 0;
        }
    }
}