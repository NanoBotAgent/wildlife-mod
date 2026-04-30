package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Firefly model — tiny beetle with bioluminescent abdomen.
 * Real firefly: slow flight with abdomen glowing, perching on vegetation,
 * flashing patterns for mating signals.
 */
public class FireflyModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart rightWing, leftWing;
    private final ModelPart abdomen;
    private final ModelPart rightAntenna, leftAntenna;

    public FireflyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.abdomen = body.getChild("abdomen");
        this.rightAntenna = body.getChild("right_antenna");
        this.leftAntenna = body.getChild("left_antenna");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1f, -2f, 2f, 2f, 4f),
            PartPose.offset(0, 23f, 0));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create().texOffs(8, 0).addBox(-3f, 0, -1f, 3f, 2f, 1.5f),
            PartPose.offset(-1f, -1f, 1f));
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create().texOffs(8, 0).addBox(0, 0, -1f, 3f, 2f, 1.5f),
            PartPose.offset(1f, -1f, 1f));

        body.addOrReplaceChild("abdomen",
            CubeListBuilder.create().texOffs(0, 6).addBox(-1.5f, -1.5f, 0, 3f, 3f, 3f),
            PartPose.offset(0, 0, 1f));

        body.addOrReplaceChild("right_antenna",
            CubeListBuilder.create().texOffs(0, 0).addBox(0, -1.5f, 0, 1f, 1.5f, 0),
            PartPose.offsetAndRotation(0.5f, -1f, -2f, 0.2f, 0, -0.2f));
        body.addOrReplaceChild("left_antenna",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1f, -1.5f, 0, 1f, 1.5f, 0),
            PartPose.offsetAndRotation(-0.5f, -1f, -2f, 0.2f, 0, 0.2f));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float age = state.ageInTicks;
        float ls = state.walkAnimationSpeed;

        // Slow gentle wing beats — fireflies fly lazily
        float speed = ls > 0.01f ? 3f : 1.5f;
        this.rightWing.zRot = (float)(Math.sin(age*speed)*0.4f);
        this.leftWing.zRot = (float)(Math.sin(age*speed+Math.PI)*0.4f);

        // Gentle bobbing
        this.body.y = 23f + (float)(Math.sin(age*speed)*0.4f*ls);

        // Antennae scan
        this.rightAntenna.zRot = -0.2f + (float)(Math.sin(age*2f)*0.1f);
        this.leftAntenna.zRot = 0.2f + (float)(Math.sin(age*2f+Math.PI)*0.1f);

        if (ls < 0.01f) {
            // Perching: wings folded, abdomen pulses (bioluminescent flash)
            this.rightWing.zRot = 0.4f;
            this.leftWing.zRot = -0.4f;
            // Abdomen positioning for flash display
            float flash = (float)(Math.abs(Math.sin(age*1.5f)));
            this.abdomen.z = 0.5f + flash*0.3f;
        } else {
            this.abdomen.z = 0;
        }
    }
}