package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.RaccoonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class RaccoonModel<T extends RaccoonEntity> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart snout;

    public RaccoonModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftEar = this.head.getChild("left_ear");
        this.rightEar = this.head.getChild("right_ear");
        this.snout = this.head.getChild("snout");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Head (rounded)
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 5.0F, 5.0F),
            PartPose.offset(0.0F, 16.0F, -5.0F));

        // Ears
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(22, 0)
                .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F),
            PartPose.offset(2.0F, -3.0F, -1.0F));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(22, 0)
                .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F),
            PartPose.offset(-2.0F, -3.0F, -1.0F));

        // Snout (with mask pattern)
        head.addOrReplaceChild("snout",
            CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 3.0F),
            PartPose.offset(0.0F, 1.0F, -3.0F));

        // Body (chunky)
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(-4.0F, -5.0F, -4.0F, 8.0F, 7.0F, 12.0F),
            PartPose.offset(0.0F, 18.0F, 0.0F));

        // Tail (striped, long)
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 15)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 10.0F),
            PartPose.offset(0.0F, 16.0F, 8.0F));

        // Legs
        root.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(20, 15)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(3.0F, 19.0F, -3.0F));

        root.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(20, 15)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(-3.0F, 19.0F, -3.0F));

        root.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(20, 15)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(3.0F, 19.0F, 5.0F));

        root.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(20, 15)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(-3.0F, 19.0F, 5.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        
        // Head rotation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        // Walking animation
        float walkSpeed = limbSwingAmount * 0.7F;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.5F) * walkSpeed;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.5F + (float)Math.PI) * walkSpeed;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.5F + (float)Math.PI) * walkSpeed;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.5F) * walkSpeed;

        // Tail ring animation
        float tailRing = entity.getTailRing(ageInTicks);
        this.tail.yRot = tailRing;
        this.tail.xRot = 0.3F;

        // Washing animation
        if (entity.isWashing()) {
            float washAnim = entity.getWashAnim(ageInTicks);
            this.head.xRot = 0.5F + washAnim;
            this.leftFrontLeg.xRot = -1.0F + washAnim * 0.5F;
            this.rightFrontLeg.xRot = -1.0F - washAnim * 0.5F;
        }

        // Standing on hind legs
        if (entity.isStanding()) {
            float standAnim = entity.getStandAnim();
            this.body.y = 18.0F - standAnim * 4.0F;
            this.head.y = 16.0F - standAnim * 6.0F;
            this.leftFrontLeg.xRot = -1.5F * standAnim;
            this.rightFrontLeg.xRot = -1.5F * standAnim;
        }
    }
}
