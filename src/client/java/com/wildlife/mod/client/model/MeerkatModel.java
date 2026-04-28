package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.CubeDeformation;
import net.minecraft.client.model.geom.CubeListBuilder;
import net.minecraft.client.model.geom.MeshDefinition;
import net.minecraft.client.model.geom.PartDefinition;
import net.minecraft.client.model.geom.LayerDefinition;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Meerkat model - slender upright body with long tail and pointed snout.
 */
public class MeerkatModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;

    public MeerkatModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightArm = body.getChild("right_arm");
        this.leftArm = body.getChild("left_arm");
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
                .addBox(-2.5F, -6.0F, -2.5F, 5.0F, 8.0F, 5.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 14.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 13)
                .addBox(-2.0F, -3.0F, -3.0F, 4.0F, 3.0F, 4.0F)
                .texOffs(20, 13)
                .addBox(-1.0F, -0.5F, -5.0F, 2.0F, 1.5F, 2.0F) // snout
                .texOffs(0, 20)
                .addBox(-1.5F, -4.5F, -1.0F, 1.0F, 1.5F, 1.0F) // left ear
                .texOffs(0, 20)
                .addBox(0.5F, -4.5F, -1.0F, 1.0F, 1.5F, 1.0F), // right ear
            PartPose.offset(0.0F, -6.0F, -2.5F));

        body.addOrReplaceChild("right_arm",
            CubeListBuilder.create()
                .texOffs(30, 0)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(-3.5F, -5.0F, 0.0F));

        body.addOrReplaceChild("left_arm",
            CubeListBuilder.create()
                .texOffs(30, 0)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(3.5F, -5.0F, 0.0F));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(30, 7)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(-1.5F, 2.0F, 0.0F));

        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(30, 7)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(1.5F, 2.0F, 0.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 23)
                .addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 7.0F),
            PartPose.offset(0.0F, -4.0F, 2.5F));

        return LayerDefinition.create(mesh, 48, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimation.positiveScale(1.0F);
        float limbSwing = state.walkAnimation.speed();

        this.rightArm.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.4F * limbSwing) - 0.2F;
        this.leftArm.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.4F * limbSwing) - 0.2F;
        this.rightLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.5F * limbSwing);
        this.leftLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.5F * limbSwing);

        this.tail.yRot = (float)(Math.sin(state.ageInTicks * 0.5F) * 0.3F);
        this.head.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.08F * limbSwing);
    }
}
