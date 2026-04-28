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
 * Goat model - sturdy quadruped with horns and beard.
 */
public class GoatModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart tail;

    public GoatModel(ModelPart root) {
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
                .addBox(-4.0F, -6.0F, -10.0F, 8.0F, 7.0F, 16.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 13.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 23)
                .addBox(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 5.0F)
                .texOffs(22, 23)
                .addBox(-1.5F, -0.5F, -7.0F, 3.0F, 2.0F, 2.0F) // snout
                .texOffs(0, 33)
                .addBox(-3.5F, -6.0F, -2.0F, 1.0F, 3.0F, 1.0F) // left horn
                .texOffs(0, 33)
                .addBox(2.5F, -6.0F, -2.0F, 1.0F, 3.0F, 1.0F) // right horn
                .texOffs(0, 37)
                .addBox(-1.0F, 2.0F, -4.0F, 2.0F, 2.0F, 1.0F), // beard
            PartPose.offset(0.0F, -6.0F, -10.0F));

        body.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(48, 0)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F),
            PartPose.offset(-3.0F, 1.0F, -6.0F));

        body.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(48, 0)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F),
            PartPose.offset(3.0F, 1.0F, -6.0F));

        body.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(48, 14)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F),
            PartPose.offset(-3.0F, 1.0F, 5.0F));

        body.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(48, 14)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F),
            PartPose.offset(3.0F, 1.0F, 5.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 40)
                .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 3.0F),
            PartPose.offset(0.0F, -4.0F, 6.0F));

        return LayerDefinition.create(mesh, 64, 48);
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

        this.head.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.06F * limbSwing);
        this.tail.yRot = (float)(Math.sin(state.ageInTicks * 0.3F) * 0.15F);
    }
}
