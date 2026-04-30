package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Toucan model — large colorful beak, hopping between branches.
 * Real toucan: hops between branches, large bill used for reaching fruit,
 * head tilting while examining food, wing fluttering.
 */
public class ToucanModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, head, rightWing, leftWing, tail;
    private final ModelPart rightLeg, leftLeg;

    public ToucanModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.tail = body.getChild("tail");
        this.rightLeg = body.getChild("right_leg");
        this.leftLeg = body.getChild("left_leg");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-2.5f, -4f, -3f, 5f, 5f, 7f),
            PartPose.offset(0, 18f, 0));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 12).addBox(-2f, -2.5f, -3f, 4f, 3.5f, 3f)
                .texOffs(14, 12).addBox(-1.5f, -0.5f, -8f, 3f, 1.5f, 5f),
            PartPose.offset(0, -3.5f, -2.5f));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create().texOffs(24, 0).addBox(-1f, -1f, -1f, 1f, 4f, 5f),
            PartPose.offset(-2.5f, -3f, -1f));
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create().texOffs(24, 0).addBox(0, -1f, -1f, 1f, 4f, 5f),
            PartPose.offset(2.5f, -3f, -1f));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 19).addBox(-2f, -1.5f, 0, 4f, 3f, 4f),
            PartPose.offset(0, -2f, 4f));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(36, 0).addBox(-1f, 0, -1f, 2f, 3f, 2f),
            PartPose.offset(-1.5f, 1f, 1f));
        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(36, 0).addBox(-1f, 0, -1f, 2f, 3f, 2f),
            PartPose.offset(1.5f, 1f, 1f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Hopping gait — toucans hop between branches
        this.rightLeg.xRot = (float)(Math.sin(wa*0.6f)*0.3f*ls);
        this.leftLeg.xRot = (float)(Math.sin(wa*0.6f+Math.PI)*0.3f*ls);
        this.body.y = 18f + (float)(Math.abs(Math.sin(wa*1.2f))*0.6f*ls);

        // Wing flutter
        this.rightWing.zRot = (float)(Math.sin(age*3f)*0.15f) + 0.1f;
        this.leftWing.zRot = (float)(Math.sin(age*3f+Math.PI)*0.15f) - 0.1f;

        this.head.xRot = (float)(Math.sin(wa*0.6f)*-0.08f*ls);
        this.tail.xRot = (float)(Math.abs(Math.sin(wa*1.2f))*0.12f*ls);

        if (ls < 0.01f) {
            // Head tilting — examining food with that massive bill
            this.head.zRot = (float)(Math.sin(age*0.07f)*0.2f);
            this.head.yRot = (float)(Math.sin(age*0.05f)*0.15f);
            this.head.xRot = (float)(Math.sin(age*0.06f)*0.1f);
            this.rightWing.zRot = 0.1f + (float)(Math.sin(age*0.5f)*0.05f);
            this.leftWing.zRot = -0.1f + (float)(Math.sin(age*0.5f+Math.PI)*0.05f);
            this.tail.xRot = (float)(Math.sin(age*0.2f)*0.05f);
        } else {
            this.head.zRot = 0;
        }
    }
}