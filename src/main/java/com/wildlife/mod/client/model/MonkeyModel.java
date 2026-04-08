package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.MonkeyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MonkeyModel<T extends MonkeyEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart tail;

    public MonkeyModel(ModelPart root) {
        super(root, false, 6.0F, 3.0F, 2.0F, 2.0F, 18);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -5.0F, -2.0F, 6.0F, 6.0F, 4.0F),
            PartPose.offset(0.0F, 14.0F, 0.0F));
        
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F),
            PartPose.offset(0.0F, 9.0F, -2.0F));
        
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(24, 10)
                .addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(3.0F, -1.0F, 0.0F));
        
        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(24, 10)
                .addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(-3.0F, -1.0F, 0.0F));
        
        head.addOrReplaceChild("snout",
            CubeListBuilder.create()
                .texOffs(24, 14)
                .addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -3.0F));
        
        root.addOrReplaceChild("left_arm",
            CubeListBuilder.create()
                .texOffs(0, 22)
                .addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(3.0F, 10.0F, 0.0F));
        
        root.addOrReplaceChild("right_arm",
            CubeListBuilder.create()
                .texOffs(0, 22)
                .addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F),
            PartPose.offset(-3.0F, 10.0F, 0.0F));
        
        root.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(8, 22)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(2.0F, 19.0F, 0.0F));
        
        root.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(8, 22)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
            PartPose.offset(-2.0F, 19.0F, 0.0F));
        
        PartDefinition tail = root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(16, 22)
                .addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F),
            PartPose.offset(0.0F, 12.0F, 2.0F));
        
        tail.addOrReplaceChild("tail_tip",
            CubeListBuilder.create()
                .texOffs(20, 22)
                .addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F),
            PartPose.offset(0.0F, 2.0F, 6.0F));
        
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        if (entity.isClimbing()) {
            float climbAnim = Mth.sin(ageInTicks * 0.3F) * 0.5F;
            this.leftArm.xRot = climbAnim;
            this.rightArm.xRot = -climbAnim;
            this.leftLeg.xRot = -climbAnim * 0.5F;
            this.rightLeg.xRot = climbAnim * 0.5F;
        } else if (entity.isSitting()) {
            this.leftArm.xRot = -0.5F;
            this.rightArm.xRot = -0.5F;
            this.leftLeg.xRot = -1.2F;
            this.rightLeg.xRot = -1.2F;
        } else {
            float walkAnim = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
            
            this.leftArm.xRot = walkAnim;
            this.rightArm.xRot = -walkAnim;
            this.leftLeg.xRot = -walkAnim * 0.7F;
            this.rightLeg.xRot = walkAnim * 0.7F;
        }
        
        float tailSwing = entity.getTailSwing(ageInTicks);
        this.tail.yRot = tailSwing;
        this.tail.xRot = Mth.sin(ageInTicks * 0.15F) * 0.1F;
    }
}
