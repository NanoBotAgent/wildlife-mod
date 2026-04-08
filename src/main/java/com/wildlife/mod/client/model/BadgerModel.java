package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.BadgerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BadgerModel<T extends BadgerEntity> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart snout;

    public BadgerModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.snout = this.head.getChild("snout");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Head (flat, wide)
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -3.0F, -4.0F, 8.0F, 4.0F, 5.0F),
            PartPose.offset(0.0F, 17.0F, -6.0F));

        // Snout (elongated)
        head.addOrReplaceChild("snout",
            CubeListBuilder.create()
                .texOffs(26, 0)
                .addBox(-2.0F, -1.5F, -3.0F, 4.0F, 3.0F, 4.0F),
            PartPose.offset(0.0F, 0.0F, -4.0F));

        // Body (low, wide)
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 9)
                .addBox(-5.0F, -4.0F, -5.0F, 10.0F, 6.0F, 14.0F),
            PartPose.offset(0.0F, 19.0F, 0.0F));

        // Tail (short, fluffy)
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(28, 9)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 5.0F),
            PartPose.offset(0.0F, 18.0F, 9.0F));

        // Legs (short, stocky)
        root.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F),
            PartPose.offset(4.0F, 20.0F, -4.0F));

        root.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F),
            PartPose.offset(-4.0F, 20.0F, -4.0F));

        root.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F),
            PartPose.offset(4.0F, 20.0F, 5.0F));

        root.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F),
            PartPose.offset(-4.0F, 20.0F, 5.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        
        // Head rotation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        // Walking animation (waddling)
        float walkSpeed = limbSwingAmount * 0.6F;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.4F) * walkSpeed;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.4F + (float)Math.PI) * walkSpeed;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.4F + (float)Math.PI) * walkSpeed;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.4F) * walkSpeed;

        // Body waddle
        this.body.zRot = Mth.cos(limbSwing * 0.4F) * walkSpeed * 0.2F;

        // Digging animation
        if (entity.isDigging()) {
            float digAnim = entity.getDigAnim(ageInTicks);
            this.head.xRot = 0.8F + digAnim;
            this.leftFrontLeg.xRot = -1.5F + digAnim;
            this.rightFrontLeg.xRot = -1.5F - digAnim;
            this.body.y = 19.0F + Mth.abs(digAnim) * 0.5F;
        }

        // Aggressive snarl
        float snarlAnim = entity.getSnarlAnim();
        this.snout.y = -1.5F + snarlAnim * 0.5F;
        this.head.y = 17.0F - snarlAnim * 0.5F;
    }
}
