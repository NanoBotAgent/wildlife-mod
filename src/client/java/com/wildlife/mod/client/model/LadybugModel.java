package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Ladybug model — dome-shaped beetle, red with spots, six tiny legs.
 * Real ladybug: crawling with alternating tripod gait,
 * elytra split for wing deployment, playing dead when threatened.
 */
public class LadybugModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart rightElytra, leftElytra;
    private final ModelPart rLeg1, rLeg2, rLeg3, lLeg1, lLeg2, lLeg3;
    private final ModelPart rightAntenna, leftAntenna;

    public LadybugModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.rightElytra = body.getChild("right_elytra");
        this.leftElytra = body.getChild("left_elytra");
        this.rLeg1 = body.getChild("r_leg1"); this.rLeg2 = body.getChild("r_leg2"); this.rLeg3 = body.getChild("r_leg3");
        this.lLeg1 = body.getChild("l_leg1"); this.lLeg2 = body.getChild("l_leg2"); this.lLeg3 = body.getChild("l_leg3");
        this.rightAntenna = body.getChild("right_antenna");
        this.leftAntenna = body.getChild("left_antenna");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-2f, -1f, -2.5f, 4f, 2f, 5f),
            PartPose.offset(0, 22f, 0));

        body.addOrReplaceChild("right_elytra",
            CubeListBuilder.create().texOffs(0, 7).addBox(0, -3f, -2f, 2f, 3f, 4f),
            PartPose.offset(0, -0.5f, 0));
        body.addOrReplaceChild("left_elytra",
            CubeListBuilder.create().texOffs(0, 7).addBox(-2f, -3f, -2f, 2f, 3f, 4f),
            PartPose.offset(0, -0.5f, 0));

        // Six legs — alternating tripod gait
        body.addOrReplaceChild("r_leg1",
            CubeListBuilder.create().texOffs(12, 12).addBox(0, 0, -0.5f, 2f, 1f, 1f),
            PartPose.offsetAndRotation(2f, 0, -2f, 0, 0, -0.3f));
        body.addOrReplaceChild("r_leg2",
            CubeListBuilder.create().texOffs(12, 14).addBox(0, 0, -0.5f, 2f, 1f, 1f),
            PartPose.offsetAndRotation(2f, 0, 0, 0, 0, -0.3f));
        body.addOrReplaceChild("r_leg3",
            CubeListBuilder.create().texOffs(12, 16).addBox(0, 0, -0.5f, 2f, 1f, 1f),
            PartPose.offsetAndRotation(2f, 0, 2f, 0, 0, -0.3f));
        body.addOrReplaceChild("l_leg1",
            CubeListBuilder.create().texOffs(12, 12).addBox(-2f, 0, -0.5f, 2f, 1f, 1f),
            PartPose.offsetAndRotation(-2f, 0, -2f, 0, 0, 0.3f));
        body.addOrReplaceChild("l_leg2",
            CubeListBuilder.create().texOffs(12, 14).addBox(-2f, 0, -0.5f, 2f, 1f, 1f),
            PartPose.offsetAndRotation(-2f, 0, 0, 0, 0, 0.3f));
        body.addOrReplaceChild("l_leg3",
            CubeListBuilder.create().texOffs(12, 16).addBox(-2f, 0, -0.5f, 2f, 1f, 1f),
            PartPose.offsetAndRotation(-2f, 0, 2f, 0, 0, 0.3f));

        body.addOrReplaceChild("right_antenna",
            CubeListBuilder.create().texOffs(0, 0).addBox(0, -1.5f, 0, 1.5f, 1.5f, 0),
            PartPose.offsetAndRotation(1f, -1f, -2.5f, 0, 0, -0.3f));
        body.addOrReplaceChild("left_antenna",
            CubeListBuilder.create().texOffs(0, 0).addBox(-1.5f, -1.5f, 0, 1.5f, 1.5f, 0),
            PartPose.offsetAndRotation(-1f, -1f, -2.5f, 0, 0, 0.3f));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float age = state.ageInTicks;
        float ls = state.walkAnimationSpeed;

        // Alternating tripod gait — R1+L2+R3 move together, L1+R2+L3 move together
        this.rLeg1.zRot = -0.3f + (float)(Math.sin(age*0.8f)*0.15f*ls);
        this.lLeg2.zRot = 0.3f + (float)(Math.sin(age*0.8f+Math.PI)*0.15f*ls);
        this.rLeg3.zRot = -0.3f + (float)(Math.sin(age*0.8f)*0.15f*ls);
        this.lLeg1.zRot = 0.3f + (float)(Math.sin(age*0.8f+Math.PI)*0.15f*ls);
        this.rLeg2.zRot = -0.3f + (float)(Math.sin(age*0.8f+Math.PI)*0.15f*ls);
        this.lLeg3.zRot = 0.3f + (float)(Math.sin(age*0.8f)*0.15f*ls);

        // Antennae twitch
        this.rightAntenna.zRot = -0.3f + (float)(Math.sin(age*2f)*0.1f);
        this.leftAntenna.zRot = 0.3f + (float)(Math.sin(age*2f+Math.PI)*0.1f);

        // Elytra open slightly for flight
        float flight = Math.max(0, (float)Math.sin(age*3f));
        this.rightElytra.zRot = flight*0.3f;
        this.leftElytra.zRot = -flight*0.3f;

        if (ls < 0.01f) {
            // Still — subtle body movement
            this.body.y = 22f + (float)(Math.sin(age*0.3f)*0.1f);
            // Elytra closed when resting
            this.rightElytra.zRot = 0;
            this.leftElytra.zRot = 0;
        }
    }
}