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
 * Ostrich model - tall flightless bird with long neck, long legs, and small wings.
 */
public class OstrichModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;

    public OstrichModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.neck = body.getChild("neck");
        this.head = neck.getChild("head");
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
                .addBox(-5.0F, -6.0F, -6.0F, 10.0F, 7.0F, 12.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(-1.5F, -12.0F, -1.5F, 3.0F, 12.0F, 3.0F),
            PartPose.offset(0.0F, -6.0F, -5.0F));

        neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 34)
                .addBox(-1.5F, -3.0F, -3.0F, 3.0F, 3.0F, 4.0F)
                .texOffs(14, 34)
                .addBox(-1.0F, -0.5F, -5.0F, 2.0F, 1.0F, 2.0F), // beak
            PartPose.offset(0.0F, -12.0F, -1.5F));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1.0F, 0.0F, -4.0F, 1.0F, 5.0F, 8.0F),
            PartPose.offset(-5.0F, -5.0F, -2.0F));

        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(0.0F, 0.0F, -4.0F, 1.0F, 5.0F, 8.0F),
            PartPose.offset(5.0F, -5.0F, -2.0F));

        body.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(44, 0)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 14.0F, 3.0F),
            PartPose.offset(-3.0F, 1.0F, -2.0F));

        body.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(44, 0)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 14.0F, 3.0F),
            PartPose.offset(3.0F, 1.0F, -2.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 41)
                .addBox(-3.0F, -3.0F, 0.0F, 6.0F, 4.0F, 5.0F),
            PartPose.offset(0.0F, -4.0F, 6.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float walkAnim = state.walkAnimation.positiveScale(1.0F);
        float limbSwing = state.walkAnimation.speed();

        this.rightLeg.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.6F * limbSwing);
        this.leftLeg.xRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.6F * limbSwing);

        this.rightWing.zRot = (float)(Math.sin(walkAnim * 0.6F) * 0.1F * limbSwing);
        this.leftWing.zRot = (float)(Math.sin(walkAnim * 0.6F + Math.PI) * 0.1F * limbSwing);

        this.neck.xRot = (float)(Math.sin(walkAnim * 0.6F) * 0.05F * limbSwing);
        this.head.xRot = (float)(Math.sin(state.ageInTicks * 0.2F) * 0.05F);
        this.tail.yRot = (float)(Math.sin(state.ageInTicks * 0.3F) * 0.15F);
    }
}
