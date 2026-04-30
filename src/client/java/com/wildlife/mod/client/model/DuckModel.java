package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Duck model — waterfowl with broad bill, webbed feet, waddling walk.
 * Real duck: waddling gait, dabbling (upending in water), preening feathers,
 * wing flapping on water surface.
 */
public class DuckModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightWing, leftWing;
    private final ModelPart rightLeg, leftLeg;

    public DuckModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.neck = body.getChild("neck");
        this.head = neck.getChild("head");
        this.tail = body.getChild("tail");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.rightLeg = body.getChild("right_leg");
        this.leftLeg = body.getChild("left_leg");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-4f, -5f, -6f, 8f, 7f, 11f),
            PartPose.offset(0, 16f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(26, 0).addBox(-1.5f, -4f, -2f, 3f, 4f, 2.5f),
            PartPose.offsetAndRotation(0, -3f, -5.5f, 0.3f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 18).addBox(-2f, -3f, -3f, 4f, 3.5f, 3f)
                .texOffs(14, 18).addBox(-1.5f, -0.5f, -6f, 3f, 1.5f, 3f),
            PartPose.offset(0, -3f, -1.5f));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create().texOffs(36, 0).addBox(-1f, -1f, -1f, 1f, 5f, 7f),
            PartPose.offset(-4f, -3f, -3f));
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create().texOffs(36, 0).addBox(0, -1f, -1f, 1f, 5f, 7f),
            PartPose.offset(4f, -3f, -3f));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 25).addBox(-2.5f, -2f, 0, 5f, 3f, 4f),
            PartPose.offset(0, -3f, 5f));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(50, 0).addBox(-1f, 0, -1f, 2f, 4f, 2f),
            PartPose.offset(-2f, 2f, 1f));
        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(50, 0).addBox(-1f, 0, -1f, 2f, 4f, 2f),
            PartPose.offset(2f, 2f, 1f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Waddling walk — side-to-side body roll
        this.rightLeg.xRot = (float)(Math.sin(wa*0.55f)*0.3f*ls);
        this.leftLeg.xRot = (float)(Math.sin(wa*0.55f+Math.PI)*0.3f*ls);
        this.body.zRot = (float)(Math.sin(wa*0.55f)*0.08f*ls);

        // Wings flap occasionally
        this.rightWing.zRot = (float)(Math.sin(age*2f)*0.15f) + 0.1f;
        this.leftWing.zRot = (float)(Math.sin(age*2f+Math.PI)*0.15f) - 0.1f;

        this.neck.xRot = 0.3f + (float)(Math.sin(wa*0.55f)*0.05f*ls);
        this.head.xRot = (float)(Math.sin(wa*0.55f)*-0.04f*ls);
        this.tail.xRot = (float)(Math.sin(age*0.4f)*0.05f);

        if (ls < 0.01f) {
            // Preening — wing stretches, head tucks under wing
            float preenCycle = (float)(Math.sin(age*0.04f)*0.5f+0.5f);
            this.rightWing.zRot = 0.1f + preenCycle*0.5f;
            this.neck.xRot = 0.3f + preenCycle*0.5f;
            this.head.zRot = (float)(Math.sin(age*0.07f)*0.2f);
            this.head.xRot = preenCycle*0.3f;
            // Tail flick
            this.tail.xRot = (float)(Math.sin(age*0.3f)*0.1f);
        } else {
            this.head.zRot = 0;
        }
    }
}