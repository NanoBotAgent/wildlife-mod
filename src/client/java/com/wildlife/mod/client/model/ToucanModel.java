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
 * Toucan model - bird with large colorful beak, small body, and short wings.
 */
public class ToucanModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;

    public ToucanModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
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
                .addBox(-3.0F, -4.0F, -3.0F, 6.0F, 5.0F, 6.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 16.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 11)
                .addBox(-2.0F, -3.0F, -3.0F, 4.0F, 4.0F, 3.0F)
                .texOffs(14, 11)
                .addBox(-1.5F, -1.0F, -8.0F, 3.0F, 2.0F, 5.0F), // large beak
            PartPose.offset(0.0F, -4.0F, -3.0F));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(-1.0F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F),
            PartPose.offset(-3.0F, -3.0F, 0.0F));

        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(0.0F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F),
            PartPose.offset(3.0F, -3.0F, 0.0F));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(40, 0)
                .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F),
            PartPose.offset(-1.5F, 1.0F, 0.0F));

        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(40, 0)
                .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F),
            PartPose.offset(1.5F, 1.0F, 0.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 18)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 3.0F, 5.0F),
            PartPose.offset(0.0F, -2.0F, 3.0F));

        return LayerDefinition.create(mesh, 48, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimation.positiveScale(1.0F);
        float limbSwing = state.walkAnimation.speed();

        this.rightLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.4F * limbSwing);
        this.leftLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.4F * limbSwing);

        this.rightWing.zRot = (float)(Math.sin(state.ageInTicks * 0.3F) * 0.05F);
        this.leftWing.zRot = (float)(Math.sin(state.ageInTicks * 0.3F + Math.PI) * 0.05F);

        this.head.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.08F * limbSwing);
    }
}
