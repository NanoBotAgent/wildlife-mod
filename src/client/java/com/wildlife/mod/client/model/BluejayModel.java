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
 * Bluejay model - medium songbird with a pointed crest and sturdy beak.
 */
public class BluejayModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart tail;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public BluejayModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.tail = body.getChild("tail");
        this.rightLeg = body.getChild("right_leg");
        this.leftLeg = body.getChild("left_leg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -3.0F, -4.0F, 6.0F, 5.0F, 8.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 18.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 13)
                .addBox(-2.5F, -3.0F, -4.0F, 5.0F, 4.0F, 4.0F)
                .texOffs(18, 13)
                .addBox(-1.0F, -1.0F, -6.0F, 2.0F, 1.0F, 2.0F) // beak
                .texOffs(26, 13)
                .addBox(-1.0F, -5.0F, -2.0F, 2.0F, 2.0F, 2.0F), // pointed crest
            PartPose.offset(0.0F, -3.0F, -4.0F));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(-1.0F, -1.0F, 0.0F, 1.0F, 4.0F, 7.0F),
            PartPose.offset(-3.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(0.0F, -1.0F, 0.0F, 1.0F, 4.0F, 7.0F),
            PartPose.offset(3.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 21)
                .addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 5.0F),
            PartPose.offset(0.0F, -1.0F, 4.0F));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(16, 21)
                .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(-1.5F, 2.0F, 0.0F));

        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(16, 21)
                .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(1.5F, 2.0F, 0.0F));

        return LayerDefinition.create(mesh, 48, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimation.positiveScale(1.0F);
        float limbSwing = state.walkAnimation.speed();

        this.rightWing.zRot = (float)(Math.sin(state.ageInTicks * 3.0F) * 0.3F) + 0.1F;
        this.leftWing.zRot = (float)(Math.sin(state.ageInTicks * 3.0F + Math.PI) * 0.3F) - 0.1F;

        this.rightLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.4F * limbSwing);
        this.leftLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.4F * limbSwing);

        this.head.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.1F * limbSwing);
        this.tail.xRot = (float)(Math.sin(state.ageInTicks * 0.5F) * 0.05F);
    }
}
