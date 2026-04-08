package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.FoxEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FoxModel<T extends FoxEntity> extends AgeableListModel<T> {
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

    public FoxModel(ModelPart root) {
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

        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -3.0F, -4.0F, 6.0F, 5.0F, 6.0F),
            PartPose.offset(0.0F, 16.0F, -6.0F));

        // Ears
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(24, 0)
                .addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(2.0F, -3.0F, -2.0F));

        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(24, 0)
                .addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(-2.0F, -3.0F, -2.0F));

        // Snout
        head.addOrReplaceChild("snout",
            CubeListBuilder.create()
                .texOffs(0, 11)
                .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 3.0F),
            PartPose.offset(0.0F, 1.0F, -4.0F));

        // Body
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(28, 11)
                .addBox(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 10.0F),
            PartPose.offsetAndRotation(0.0F, 18.0F, 2.0F, (float)Math.PI / 4, 0.0F, 0.0F));

        // Tail (fluffy)
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 8.0F),
            PartPose.offset(0.0F, 15.0F, 8.0F));

        // Legs
        root.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(2.0F, 18.0F, -4.0F));

        root.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(-2.0F, 18.0F, -4.0F));

        root.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(2.0F, 18.0F, 4.0F));

        root.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(-2.0F, 18.0F, 4.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        
        // Head rotation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        // Ear twitch
        float earTwist = entity.getEarTwist(ageInTicks);
        this.leftEar.zRot = earTwist;
        this.rightEar.zRot = -earTwist;

        // Walking animation
        float walkSpeed = limbSwingAmount * 0.8F;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.5F) * walkSpeed;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.5F + (float)Math.PI) * walkSpeed;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.5F + (float)Math.PI) * walkSpeed;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.5F) * walkSpeed;

        // Tail wag
        float tailWag = entity.getTailWag(ageInTicks);
        this.tail.yRot = tailWag;
        this.tail.xRot = 0.2F + Mth.abs(tailWag) * 0.2F;

        // Pounce animation
        if (entity.isPouncing()) {
            float pounce = entity.getPounceAnim(ageInTicks);
            this.body.xRot = (float)Math.PI / 4 - pounce * 0.3F;
            this.head.xRot = -pounce * 0.5F;
        }

        // Sitting
        if (entity.isSitting()) {
            this.body.xRot = (float)Math.PI / 2;
            this.head.y = 14.0F;
            this.tail.y = 12.0F;
        }
    }
}
