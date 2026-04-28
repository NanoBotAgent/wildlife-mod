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
 * Ladybug model - tiny round beetle with spotted wing covers.
 */
public class LadybugModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightWingCover;
    private final ModelPart leftWingCover;

    public LadybugModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWingCover = body.getChild("right_wing_cover");
        this.leftWingCover = body.getChild("left_wing_cover");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 22.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 7)
                .addBox(-1.0F, -1.5F, -2.0F, 2.0F, 2.0F, 1.5F),
            PartPose.offset(0.0F, -1.0F, -2.0F));

        body.addOrReplaceChild("right_wing_cover",
            CubeListBuilder.create()
                .texOffs(16, 0)
                .addBox(-2.0F, -1.5F, -1.0F, 2.0F, 2.0F, 3.0F),
            PartPose.offset(0.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("left_wing_cover",
            CubeListBuilder.create()
                .texOffs(16, 0)
                .addBox(0.0F, -1.5F, -1.0F, 2.0F, 2.0F, 3.0F),
            PartPose.offset(0.0F, -2.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 16);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        // Subtle idle movement
        this.body.yRot = (float)(Math.sin(state.ageInTicks * 0.3F) * 0.05F);
    }
}
