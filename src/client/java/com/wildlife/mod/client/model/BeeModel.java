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
 * Bee model - small flying insect with striped body and translucent wings.
 */
public class BeeModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart stinger;

    public BeeModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.stinger = body.getChild("stinger");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.5F, -2.5F, -3.0F, 5.0F, 4.0F, 6.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 21.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-1.5F, -2.0F, -3.0F, 3.0F, 3.0F, 2.0F),
            PartPose.offset(0.0F, -1.5F, -3.0F));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(22, 0)
                .addBox(-4.0F, -2.0F, -1.0F, 4.0F, 3.0F, 2.0F),
            PartPose.offset(-2.5F, -2.5F, -1.0F));

        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(22, 0)
                .addBox(0.0F, -2.0F, -1.0F, 4.0F, 3.0F, 2.0F),
            PartPose.offset(2.5F, -2.5F, -1.0F));

        body.addOrReplaceChild("stinger",
            CubeListBuilder.create()
                .texOffs(0, 15)
                .addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, 3.0F));

        return LayerDefinition.create(mesh, 32, 16);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        // Fast wing buzz
        this.rightWing.zRot = (float)(Math.sin(state.ageInTicks * 8.0F) * 0.5F) + 0.3F;
        this.leftWing.zRot = (float)(Math.sin(state.ageInTicks * 8.0F + Math.PI) * 0.5F) - 0.3F;

        this.body.yRot = (float)(Math.sin(state.ageInTicks * 0.5F) * 0.1F);
    }
}
