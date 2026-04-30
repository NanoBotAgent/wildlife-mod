package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Penguin model — upright flightless bird, flippers, torpedo body.
 * Real penguin: waddling walk, tobogganing (sliding on belly),
 * underwater swimming using flippers, huddling for warmth.
 */
public class PenguinModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, head, rightFlipper, leftFlipper;
    private final ModelPart rightLeg, leftLeg;

    public PenguinModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightFlipper = body.getChild("right_flipper");
        this.leftFlipper = body.getChild("left_flipper");
        this.rightLeg = body.getChild("right_leg");
        this.leftLeg = body.getChild("left_leg");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-3.5f, -8f, -3f, 7f, 10f, 5f),
            PartPose.offset(0, 14f, 0));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 15).addBox(-2.5f, -3f, -2.5f, 5f, 4f, 4f)
                .texOffs(18, 15).addBox(-1.5f, -0.5f, -4.5f, 3f, 1.5f, 2f),
            PartPose.offset(0, -7f, -1f));

        body.addOrReplaceChild("right_flipper",
            CubeListBuilder.create().texOffs(30, 0).addBox(-1f, 0, -1f, 1f, 7f, 3f),
            PartPose.offset(-3.5f, -6f, 0));
        body.addOrReplaceChild("left_flipper",
            CubeListBuilder.create().texOffs(30, 0).addBox(0, 0, -1f, 1f, 7f, 3f),
            PartPose.offset(3.5f, -6f, 0));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(40, 0).addBox(-1.5f, 0, -1f, 3f, 2f, 3f),
            PartPose.offset(-1.5f, 2f, 0));
        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(40, 0).addBox(-1.5f, 0, -1f, 3f, 2f, 3f),
            PartPose.offset(1.5f, 2f, 0));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Penguin waddle — side-to-side body rock with short steps
        this.rightLeg.xRot = (float)(Math.sin(wa*0.5f)*0.2f*ls);
        this.leftLeg.xRot = (float)(Math.sin(wa*0.5f+Math.PI)*0.2f*ls);
        this.body.zRot = (float)(Math.sin(wa*0.5f)*0.06f*ls);

        // Flippers swing with walk
        this.rightFlipper.zRot = (float)(Math.sin(wa*0.5f)*0.15f*ls) + 0.1f;
        this.leftFlipper.zRot = (float)(Math.sin(wa*0.5f+Math.PI)*0.15f*ls) - 0.1f;

        // Head bobs slightly
        this.head.yRot = (float)(Math.sin(wa*0.5f)*0.08f*ls);
        this.head.xRot = (float)(Math.sin(wa*0.5f)*0.04f*ls);

        if (ls < 0.01f) {
            // Looking around — penguins are curious
            this.head.yRot = (float)(Math.sin(age*0.06f)*0.25f);
            this.head.zRot = (float)(Math.sin(age*0.05f)*0.1f);
            // Flipper twitch
            this.rightFlipper.zRot = 0.1f + (float)(Math.sin(age*0.25f)*0.05f);
            this.leftFlipper.zRot = -0.1f + (float)(Math.sin(age*0.25f+Math.PI)*0.05f);
        } else {
            this.head.zRot = 0;
        }
    }
}