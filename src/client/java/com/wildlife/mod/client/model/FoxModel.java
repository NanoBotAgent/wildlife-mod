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
 * Fox model - small quadruped with pointed snout, large ears, and bushy tail.
 */
public class FoxModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart tail;

    public FoxModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightFrontLeg = body.getChild("right_front_leg");
        this.leftFrontLeg = body.getChild("left_front_leg");
        this.rightHindLeg = body.getChild("right_hind_leg");
        this.leftHindLeg = body.getChild("left_hind_leg");
        this.tail = body.getChild("tail");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -4.0F, -8.0F, 6.0F, 5.0F, 14.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 16.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(-2.5F, -3.0F, -5.0F, 5.0F, 4.0F, 5.0F)
                .texOffs(24, 19)
                .addBox(-1.5F, -0.5F, -7.0F, 3.0F, 2.0F, 2.0F) // snout
                .texOffs(0, 28)
                .addBox(-2.0F, -5.5F, -3.0F, 1.0F, 2.5F, 1.0F) // left ear
                .texOffs(0, 28)
                .addBox(1.0F, -5.5F, -3.0F, 1.0F, 2.5F, 1.0F), // right ear
            PartPose.offset(0.0F, -1.0F, -8.0F));

        body.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(40, 0)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(-2.0F, 1.0F, -5.0F));

        body.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(40, 0)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(2.0F, 1.0F, -5.0F));

        body.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(40, 8)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(-2.0F, 1.0F, 5.0F));

        body.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(40, 8)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(2.0F, 1.0F, 5.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 32)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 8.0F),
            PartPose.offset(0.0F, -2.0F, 6.0F));

        return LayerDefinition.create(mesh, 48, 48);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimation.positiveScale(1.0F);
        float limbSwing = state.walkAnimation.speed();

        this.rightFrontLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.5F * limbSwing);
        this.leftFrontLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.5F * limbSwing);
        this.rightHindLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.5F * limbSwing);
        this.leftHindLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.5F * limbSwing);

        this.tail.yRot = (float)(Math.sin(state.ageInTicks * 0.5F) * 0.3F);
        this.head.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.08F * limbSwing);
    }
}
