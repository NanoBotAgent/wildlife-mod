package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.GoatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GoatModel<T extends GoatEntity> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHorn;
    private final ModelPart rightHorn;
    private final ModelPart leftEar;
    private final ModelPart rightEar;

    public GoatModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHorn = this.head.getChild("left_horn");
        this.rightHorn = this.head.getChild("right_horn");
        this.leftEar = this.head.getChild("left_ear");
        this.rightEar = this.head.getChild("right_ear");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -4.0F, -5.0F, 6.0F, 6.0F, 7.0F),
            PartPose.offset(0.0F, 12.0F, -8.0F));

        // Horns (curved)
        head.addOrReplaceChild("left_horn",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(-1.0F, -4.0F, 0.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offsetAndRotation(2.0F, -4.0F, -3.0F, -0.5F, 0.0F, 0.0F));

        head.addOrReplaceChild("right_horn",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(-1.0F, -4.0F, 0.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offsetAndRotation(-2.0F, -4.0F, -3.0F, -0.5F, 0.0F, 0.0F));

        // Ears
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(36, 0)
                .addBox(-2.0F, -1.0F, 0.0F, 3.0F, 2.0F, 1.0F),
            PartPose.offset(3.0F, -2.0F, -2.0F));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(36, 0)
                .addBox(-1.0F, -1.0F, 0.0F, 3.0F, 2.0F, 1.0F),
            PartPose.offset(-3.0F, -2.0F, -2.0F));

        // Body (fuzzy)
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 13)
                .addBox(-4.0F, -6.0F, -5.0F, 8.0F, 10.0F, 12.0F),
            PartPose.offset(0.0F, 14.0F, 2.0F));

        // Tail (short)
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(40, 13)
                .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 3.0F),
            PartPose.offset(0.0F, 12.0F, 7.0F));

        // Legs (long, for climbing)
        root.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(20, 13)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
            PartPose.offset(3.0F, 14.0F, -5.0F));

        root.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(20, 13)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
            PartPose.offset(-3.0F, 14.0F, -5.0F));

        root.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(20, 13)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
            PartPose.offset(3.0F, 14.0F, 5.0F));

        root.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(20, 13)
                .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
            PartPose.offset(-3.0F, 14.0F, 5.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        
        // Head rotation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        // Ear flick
        float earFlick = entity.getEarFlick(ageInTicks);
        this.leftEar.zRot = earFlick;
        this.rightEar.zRot = -earFlick;

        // Walking animation
        float walkSpeed = limbSwingAmount * 0.8F;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.5F) * walkSpeed;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.5F + (float)Math.PI) * walkSpeed;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.5F + (float)Math.PI) * walkSpeed;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.5F) * walkSpeed;

        // Tail wag
        this.tail.yRot = Mth.cos(ageInTicks * 0.2F) * 0.2F;

        // Ram animation
        if (entity.isRamming()) {
            float ramAnim = entity.getRamAnim(ageInTicks);
            this.head.xRot = -0.5F + ramAnim;
            this.head.zRot = ramAnim * 0.3F;
            this.body.xRot = 0.2F;
        }

        // Jumping
        if (entity.isJumping()) {
            this.leftFrontLeg.xRot = -1.0F;
            this.rightFrontLeg.xRot = -1.0F;
            this.leftHindLeg.xRot = 1.0F;
            this.rightHindLeg.xRot = 1.0F;
        }
    }
}
