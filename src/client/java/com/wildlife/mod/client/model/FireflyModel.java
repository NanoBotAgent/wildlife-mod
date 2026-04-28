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
 * Firefly model - tiny beetle with glowing abdomen.
 */
public class FireflyModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart abdomen;

    public FireflyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.abdomen = body.getChild("abdomen");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 23.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 5)
                .addBox(-0.5F, -1.0F, -1.5F, 1.0F, 1.0F, 1.0F),
            PartPose.offset(0.0F, 0.0F, -1.5F));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(12, 0)
                .addBox(-2.0F, -1.5F, -0.5F, 2.0F, 2.0F, 1.5F),
            PartPose.offset(-1.0F, -1.0F, 0.0F));

        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(12, 0)
                .addBox(0.0F, -1.5F, -0.5F, 2.0F, 2.0F, 1.5F),
            PartPose.offset(1.0F, -1.0F, 0.0F));

        body.addOrReplaceChild("abdomen",
            CubeListBuilder.create()
                .texOffs(0, 7)
                .addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F), // glowing part
            PartPose.offset(0.0F, 0.0F, 1.5F));

        return LayerDefinition.create(mesh, 16, 16);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        this.rightWing.zRot = (float)(Math.sin(state.ageInTicks * 6.0F) * 0.4F) + 0.2F;
        this.leftWing.zRot = (float)(Math.sin(state.ageInTicks * 6.0F + Math.PI) * 0.4F) - 0.2F;

        this.body.yRot = (float)(Math.sin(state.ageInTicks * 0.3F) * 0.1F);
    }
}
