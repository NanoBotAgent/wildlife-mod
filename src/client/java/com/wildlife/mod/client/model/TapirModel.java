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
 * Tapir model - large stocky quadruped with short prehensile trunk.
 */
public class TapirModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart tail;

    public TapirModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightFrontLeg = body.getChild("right_front_leg");
        this.leftFrontLeg = body.getChild("left_front_leg");
        this.rightHindLeg = body.getChild("right_hind_leg");
        this.leftHindLeg = body.getChild("left_hind_leg");
        this.tail = body.getChild("tail");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-6.0F, -7.0F, -12.0F, 12.0F, 9.0F, 20.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 12.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 29)
                .addBox(-4.0F, -4.0F, -7.0F, 8.0F, 6.0F, 7.0F)
                .texOffs(30, 29)
                .addBox(-1.5F, -1.0F, -10.0F, 3.0F, 2.0F, 3.0F) // trunk
                .texOffs(0, 42)
                .addBox(-3.0F, -6.0F, -4.0F, 1.0F, 2.0F, 1.0F) // left ear
                .texOffs(0, 42)
                .addBox(2.0F, -6.0F, -4.0F, 1.0F, 2.0F, 1.0F), // right ear
            PartPose.offset(0.0F, -3.0F, -12.0F));

        body.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(44, 0)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F),
            PartPose.offset(-4.0F, 2.0F, -7.0F));

        body.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(44, 0)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F),
            PartPose.offset(4.0F, 2.0F, -7.0F));

        body.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(44, 14)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F),
            PartPose.offset(-4.0F, 2.0F, 6.0F));

        body.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(44, 14)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F),
            PartPose.offset(4.0F, 2.0F, 6.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 46)
                .addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F),
            PartPose.offset(0.0F, -5.0F, 8.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimationPos;
        float limbSwing = state.walkAnimationSpeed;

        this.rightFrontLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.5F * limbSwing);
        this.leftFrontLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.5F * limbSwing);
        this.rightHindLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.5F * limbSwing);
        this.leftHindLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.5F * limbSwing);

        this.head.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.04F * limbSwing);
    }
}
