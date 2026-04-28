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
 * Dragonfly model - elongated insect with long body and wide wings.
 */
public class DragonflyModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart tail;

    public DragonflyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.tail = body.getChild("tail");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE),
            PartPose.offset(0.0F, 22.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 6)
                .addBox(-1.5F, -1.5F, -2.0F, 3.0F, 2.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -2.0F));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(16, 0)
                .addBox(-5.0F, -2.0F, -1.0F, 5.0F, 3.0F, 2.0F),
            PartPose.offset(-1.0F, -1.0F, 0.0F));

        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(16, 0)
                .addBox(0.0F, -2.0F, -1.0F, 5.0F, 3.0F, 2.0F),
            PartPose.offset(1.0F, -1.0F, 0.0F));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F),
            PartPose.offset(0.0F, 0.0F, 2.0F));

        return LayerDefinition.create(mesh, 32, 16);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        // Fast wing buzz
        this.rightWing.zRot = (float)(Math.sin(state.ageInTicks * 7.0F) * 0.5F) + 0.2F;
        this.leftWing.zRot = (float)(Math.sin(state.ageInTicks * 7.0F + Math.PI) * 0.5F) - 0.2F;

        this.body.yRot = (float)(Math.sin(state.ageInTicks * 0.4F) * 0.08F);
        this.tail.yRot = (float)(Math.sin(state.ageInTicks * 0.6F) * 0.15F);
    }
}
