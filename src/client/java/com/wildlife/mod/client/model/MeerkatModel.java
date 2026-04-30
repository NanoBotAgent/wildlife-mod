package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Meerkat model — sentinel standing, digging with front claws.
 * Real meerkat: stands on hind legs watching for danger, zigzag running,
 * digging with front claws, sunbathing with belly exposed.
 */
public class MeerkatModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, head, tail;
    private final ModelPart rightArm, rightClaw;
    private final ModelPart leftArm, leftClaw;
    private final ModelPart rightLeg, rightFoot;
    private final ModelPart leftLeg, leftFoot;

    public MeerkatModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.tail = body.getChild("tail");
        this.rightArm = body.getChild("right_arm"); this.rightClaw = rightArm.getChild("right_claw");
        this.leftArm = body.getChild("left_arm"); this.leftClaw = leftArm.getChild("left_claw");
        this.rightLeg = body.getChild("right_leg"); this.rightFoot = rightLeg.getChild("right_foot");
        this.leftLeg = body.getChild("left_leg"); this.leftFoot = leftLeg.getChild("left_foot");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Upright body — meerkat often stands bipedally
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-2.5f, -6f, -3f, 5f, 8f, 8f),
            PartPose.offsetAndRotation(0, 14f, 2f, 0.3f, 0, 0));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2f, -2.5f, -3f, 4f, 3.5f, 3.5f)
                .texOffs(14, 16).addBox(-1f, -0.5f, -5f, 2f, 1.5f, 2f),
            PartPose.offset(0, -5f, -2.5f));

        // Front arms — used for digging, small claws
        PartDefinition rA = body.addOrReplaceChild("right_arm",
            CubeListBuilder.create().texOffs(26, 0).addBox(-1f, 0, -1f, 2f, 5f, 2f),
            PartPose.offset(-2f, -3f, -2f));
        rA.addOrReplaceChild("right_claw",
            CubeListBuilder.create().texOffs(26, 7).addBox(-0.5f, 0, 0.5f, 1.5f, 3f, 2f),
            PartPose.offset(0, 5f, -1f));

        PartDefinition lA = body.addOrReplaceChild("left_arm",
            CubeListBuilder.create().texOffs(26, 0).addBox(-1f, 0, -1f, 2f, 5f, 2f),
            PartPose.offset(2f, -3f, -2f));
        lA.addOrReplaceChild("left_claw",
            CubeListBuilder.create().texOffs(26, 7).addBox(-1f, 0, 0.5f, 1.5f, 3f, 2f),
            PartPose.offset(0, 5f, -1f));

        // Hind legs — support sentinel stance
        PartDefinition rL = body.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(36, 0).addBox(-1f, 0, -1f, 2f, 6f, 2f),
            PartPose.offset(-1.5f, 2f, 2f));
        rL.addOrReplaceChild("right_foot",
            CubeListBuilder.create().texOffs(36, 8).addBox(-1f, 0, 1f, 2f, 3f, 3f),
            PartPose.offset(0, 6f, -1.5f));

        PartDefinition lL = body.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(36, 0).addBox(-1f, 0, -1f, 2f, 6f, 2f),
            PartPose.offset(1.5f, 2f, 2f));
        lL.addOrReplaceChild("left_foot",
            CubeListBuilder.create().texOffs(36, 8).addBox(-1f, 0, 1f, 2f, 3f, 3f),
            PartPose.offset(0, 6f, -1.5f));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 23).addBox(-1f, -1f, 0, 2f, 2f, 6f),
            PartPose.offset(0, -3f, 5f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Bipedal running — hind legs power movement
        float sp = 0.55f;
        this.rightLeg.xRot = (float)(Math.sin(wa*sp)*0.4f*ls);
        this.leftLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls);
        this.rightFoot.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.25f*ls));
        this.leftFoot.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.25f*ls));
        // Front arms dangle while running
        this.rightArm.xRot = -0.3f + (float)(Math.sin(wa*sp)*0.15f*ls);
        this.leftArm.xRot = -0.3f + (float)(Math.sin(wa*sp+Math.PI)*0.15f*ls);
        this.body.xRot = 0.3f + (float)(Math.sin(wa*sp)*0.05f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.06f*ls);
        this.tail.yRot = (float)(Math.sin(age*0.5f)*0.15f);

        if (ls < 0.01f) {
            // Sentinel stance — standing tall, looking around for predators
            this.body.xRot = 0.15f;
            this.body.y = 14f + (float)(Math.sin(age*0.06f)*0.3f);
            // Head scanning side to side — meerkat rotates head constantly
            this.head.yRot = (float)(Math.sin(age*0.08f)*0.4f);
            this.head.xRot = (float)(Math.sin(age*0.06f)*0.1f);
            // Arms hang at sides
            this.rightArm.xRot = -0.2f;
            this.leftArm.xRot = -0.2f;
            this.rightClaw.xRot = 0;
            this.leftClaw.xRot = 0;
            this.tail.yRot = (float)(Math.sin(age*0.25f)*0.1f);
        } else {
            this.head.yRot = 0;
        }
    }
}