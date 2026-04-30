package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Butterfly model — erratic fluttering flight, basking with wings spread.
 * Real butterfly: erratic non-linear flight for predator evasion,
 * basking with wings open perpendicular to sun, wings held vertical at rest,
 * antenna curling, proboscis for feeding.
 */
public class ButterflyModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart rightUpperWing, rightLowerWing;
    private final ModelPart leftUpperWing, leftLowerWing;
    private final ModelPart rightAntenna, leftAntenna;

    public ButterflyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.rightUpperWing = body.getChild("right_upper_wing");
        this.rightLowerWing = body.getChild("right_lower_wing");
        this.leftUpperWing = body.getChild("left_upper_wing");
        this.leftLowerWing = body.getChild("left_lower_wing");
        this.rightAntenna = body.getChild("right_antenna");
        this.leftAntenna = body.getChild("left_antenna");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1f, -3f, 2f, 2f, 6f),
            PartPose.offset(0, 21f, 0));

        body.addOrReplaceChild("right_upper_wing",
            CubeListBuilder.create().texOffs(0, 8).addBox(-7f, 0, -2f, 7f, 5f, 3f),
            PartPose.offset(-1f, -1f, 0));
        body.addOrReplaceChild("right_lower_wing",
            CubeListBuilder.create().texOffs(0, 16).addBox(-5f, 0, -1.5f, 5f, 4f, 2f),
            PartPose.offset(-1f, -0.5f, 2f));

        body.addOrReplaceChild("left_upper_wing",
            CubeListBuilder.create().texOffs(0, 8).addBox(0, 0, -2f, 7f, 5f, 3f),
            PartPose.offset(1f, -1f, 0));
        body.addOrReplaceChild("left_lower_wing",
            CubeListBuilder.create().texOffs(0, 16).addBox(0, 0, -1.5f, 5f, 4f, 2f),
            PartPose.offset(1f, -0.5f, 2f));

        body.addOrReplaceChild("right_antenna",
            CubeListBuilder.create().texOffs(20, 0).addBox(-0.5f, -3f, 0, 1f, 3f, 0),
            PartPose.offsetAndRotation(-0.5f, -1f, -3f, 0.3f, 0, -0.3f));
        body.addOrReplaceChild("left_antenna",
            CubeListBuilder.create().texOffs(20, 0).addBox(-0.5f, -3f, 0, 1f, 3f, 0),
            PartPose.offsetAndRotation(0.5f, -1f, -3f, 0.3f, 0, 0.3f));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float age = state.ageInTicks;
        float ls = state.walkAnimationSpeed;

        // Flutter speed — faster in flight, slower at rest
        float speed = ls > 0.01f ? 4.5f : 2.0f;
        float amplitude = ls > 0.01f ? 0.7f : 0.15f;

        this.rightUpperWing.zRot = (float)(Math.sin(age*speed)*amplitude);
        this.leftUpperWing.zRot = (float)(Math.sin(age*speed+Math.PI)*amplitude);
        this.rightLowerWing.zRot = (float)(Math.sin(age*speed+0.2f)*amplitude*0.8f);
        this.leftLowerWing.zRot = (float)(Math.sin(age*speed+Math.PI+0.2f)*amplitude*0.8f);

        // Erratic body movement — bob and sway
        this.body.y = 21f + (float)(Math.sin(age*speed*2)*0.3f*ls);
        this.body.zRot = (float)(Math.sin(age*3f)*0.1f*ls);

        // Antennae curl
        this.rightAntenna.zRot = 0.3f + (float)(Math.sin(age*3f)*0.15f);
        this.leftAntenna.zRot = -0.3f + (float)(Math.sin(age*3f+Math.PI)*0.15f);

        if (ls < 0.01f) {
            // Basking — wings spread flat to absorb heat, occasional slow flap
            this.rightUpperWing.zRot = (float)(Math.sin(age*0.5f)*0.1f) + 0.3f;
            this.leftUpperWing.zRot = (float)(Math.sin(age*0.5f+Math.PI)*0.1f) - 0.3f;
            this.rightLowerWing.zRot = (float)(Math.sin(age*0.5f)*0.08f) + 0.2f;
            this.leftLowerWing.zRot = (float)(Math.sin(age*0.5f+Math.PI)*0.08f) - 0.2f;
            this.body.zRot = 0;
        }
    }
}