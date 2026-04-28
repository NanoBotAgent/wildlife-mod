package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Penguin model - flightless bird with upright posture, flippers, and waddle walk.
 */
public class PenguinModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightFlipper;
    private final ModelPart leftFlipper;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;

    public PenguinModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightFlipper = body.getChild("right_flipper");
        this.leftFlipper = body.getChild("left_flipper");
        this.rightLeg = body.getChild("right_leg");
        this.leftLeg = body.getChild("left_leg");
        this.tail = body.getChild("tail");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -7.0F, -2.5F, 6.0F, 9.0F, 5.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 13.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 14)
                .addBox(-2.0F, -3.0F, -2.5F, 4.0F, 3.0F, 3.0F)
                .texOffs(18, 14)
                .addBox(-1.0F, -0.5F, -4.5F, 2.0F, 1.0F, 2.0F), // beak
            PartPose.offset(0.0F, -7.0F, -2.5F));

        body.addOrReplaceChild("right_flipper",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 3.0F),
            PartPose.offset(-3.0F, -6.0F, 0.0F));

        body.addOrReplaceChild("left_flipper",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(0.0F, 0.0F, -1.0F, 1.0F, 6.0F, 3.0F),
            PartPose.offset(3.0F, -6.0F, 0.0F));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(40, 9)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F),
            PartPose.offset(-1.5F, 2.0F, 0.0F));

        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(40, 9)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F),
            PartPose.offset(1.5F, 2.0F, 0.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 20)
                .addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, 2.5F));

        return LayerDefinition.create(mesh, 48, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimation.positiveScale(1.0F);
        float limbSwing = state.walkAnimation.speed();

        // Penguin waddle
        this.rightLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.3F * limbSwing);
        this.leftLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.3F * limbSwing);

        this.rightFlipper.zRot = (float)(Math.sin(walkAnim * 0.6F) * 0.3F * limbSwing) + 0.1F;
        this.leftFlipper.zRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.3F * limbSwing) - 0.1F;

        this.head.yRot = (float)(Math.sin(walkAnim * 0.3F) * 0.1F * limbSwing);
    }
}
