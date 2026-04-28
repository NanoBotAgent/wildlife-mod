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
 * Butterfly model - tiny insect with wide spread wings and thin body.
 */
public class ButterflyModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart antennae;

    public ButterflyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.antennae = body.getChild("antennae");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 22.0F, 0.0F));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 5)
                .addBox(-5.0F, -4.0F, -1.5F, 5.0F, 5.0F, 3.0F),
            PartPose.offset(-0.5F, -0.5F, 0.0F));

        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 5)
                .addBox(0.0F, -4.0F, -1.5F, 5.0F, 5.0F, 3.0F),
            PartPose.offset(0.5F, -0.5F, 0.0F));

        body.addOrReplaceChild("antennae",
            CubeListBuilder.create()
                .texOffs(16, 5)
                .addBox(-1.0F, -2.0F, -3.0F, 0.0F, 2.0F, 1.0F)
                .texOffs(16, 5)
                .addBox(1.0F, -2.0F, -3.0F, 0.0F, 2.0F, 1.0F),
            PartPose.offset(0.0F, -0.5F, -2.0F));

        return LayerDefinition.create(mesh, 32, 16);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        // Gentle wing flap
        this.rightWing.zRot = (float)(Math.sin(state.ageInTicks * 3.0F) * 0.4F) + 0.2F;
        this.leftWing.zRot = (float)(Math.sin(state.ageInTicks * 3.0F + Math.PI) * 0.4F) - 0.2F;
    }
}
