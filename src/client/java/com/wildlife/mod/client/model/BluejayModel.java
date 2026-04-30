package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Blue Jay model.
 */
public class BluejayModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, head, rightWing, leftWing, tail;
    private final ModelPart rightLeg, leftLeg;

    public BluejayModel(ModelPart root) {
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
            CubeListBuilder.create().texOffs(0, 0).addBox(-2.0f, -3f, -3f, 4f, 3f, 6f),
            PartPose.offset(0, 19f, 0));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 5).addBox(-1.5f, -3f, -3f, 3f, 3f, 3f)
                .texOffs(5, 5).addBox(-0.5f, -1f, -4f, 1f, 1f, 1f),
            PartPose.offset(0, -2f, -3f));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create().texOffs(12, 0).addBox(-1f, 0, -1f, 1f, 4f, 5f),
            PartPose.offset(-3f, -2f, -1f));
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create().texOffs(12, 0).addBox(0, 0, -1f, 1f, 4f, 5f),
            PartPose.offset(3f, -2f, -1f));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 10).addBox(-1.5f, -1.0f, 0, 3f, 2f, 4f),
            PartPose.offset(0, -1f, 2f));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(17, 0).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(-1f, 0f, 0f));
        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(17, 0).addBox(-0.5f, 0, -0.5f, 1f, 3f, 1f),
            PartPose.offset(1f, 0f, 0f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Hopping walk — small birds hop rather than walk
        this.rightLeg.xRot = (float)(Math.sin(wa*0.6f)*0.35f*ls);
        this.leftLeg.xRot = (float)(Math.sin(wa*0.6f+Math.PI)*0.35f*ls);
        this.body.y = 19f + (float)(Math.abs(Math.sin(wa*1.2f))*0.5f*ls);

        // Wing flutter
        this.rightWing.zRot = (float)(Math.sin(age*4.0f)*0.2f) + 0.1f;
        this.leftWing.zRot = (float)(Math.sin(age*4.0f+Math.PI)*0.2f) - 0.1f;

        // Head bob — birds stabilize head visually while body moves
        this.head.xRot = (float)(Math.sin(wa*0.6f)*-0.1f*ls);
        this.tail.xRot = (float)(Math.abs(Math.sin(wa*1.2f))*0.1f*ls);

        if (ls < 0.01f) {
            // Idle: head tilts, tail flicks, wing adjustments
            this.head.zRot = (float)(Math.sin(age*0.08f)*0.15f);
            this.head.yRot = (float)(Math.sin(age*0.06f)*0.2f);
            this.tail.xRot = (float)(Math.sin(age*0.3f)*0.08f);
            this.rightWing.zRot = (float)(Math.sin(age*0.5f)*0.05f) + 0.1f;
            this.leftWing.zRot = (float)(Math.sin(age*0.5f+Math.PI)*0.05f) - 0.1f;
        } else {
            this.head.zRot = 0;
            this.head.yRot = 0;
        }
    }
}