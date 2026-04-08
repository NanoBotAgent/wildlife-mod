package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.BoarEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BoarModel<T extends BoarEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart snout;
    private final ModelPart leftTusk;
    private final ModelPart rightTusk;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;
    private final ModelPart tail;

    public BoarModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.snout = head.getChild("snout");
        this.leftTusk = head.getChild("left_tusk");
        this.rightTusk = head.getChild("right_tusk");
        this.frontLeftLeg = root.getChild("front_left_leg");
        this.frontRightLeg = root.getChild("front_right_leg");
        this.backLeftLeg = root.getChild("back_left_leg");
        this.backRightLeg = root.getChild("back_right_leg");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - stocky and low
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-5.0F, -6.0F, -10.0F, 10.0F, 8.0F, 18.0F),
            PartPose.offset(0.0F, 11.0F, 0.0F));
        
        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 26)
                .addBox(-3.0F, -3.0F, -6.0F, 6.0F, 5.0F, 6.0F),
            PartPose.offset(0.0F, 10.0F, -16.0F));
        
        // Snout
        head.addOrReplaceChild("snout",
            CubeListBuilder.create()
                .texOffs(24, 26)
                .addBox(-2.0F, 0.0F, -4.0F, 4.0F, 3.0F, 4.0F),
            PartPose.offset(0.0F, 0.0F, -6.0F));
        
        // Tusks
        head.addOrReplaceChild("left_tusk",
            CubeListBuilder.create()
                .texOffs(36, 26)
                .addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(2.0F, 2.0F, -8.0F));
        
        head.addOrReplaceChild("right_tusk",
            CubeListBuilder.create()
                .texOffs(36, 26)
                .addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(-2.0F, 2.0F, -8.0F));
        
        // Ears
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(40, 26)
                .addBox(0.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(3.0F, -2.0F, 0.0F));
        
        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(40, 26)
                .addBox(-2.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(-3.0F, -2.0F, 0.0F));
        
        // Legs
        root.addOrReplaceChild("front_left_leg",
            CubeListBuilder.create()
                .texOffs(16, 37)
                .addBox(0.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
            PartPose.offset(3.0F, 18.0F, -8.0F));
        
        root.addOrReplaceChild("front_right_leg",
            CubeListBuilder.create()
                .texOffs(16, 37)
                .addBox(-3.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
            PartPose.offset(-3.0F, 18.0F, -8.0F));
        
        root.addOrReplaceChild("back_left_leg",
            CubeListBuilder.create()
                .texOffs(16, 37)
                .addBox(0.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
            PartPose.offset(3.0F, 18.0F, 6.0F));
        
        root.addOrReplaceChild("back_right_leg",
            CubeListBuilder.create()
                .texOffs(16, 37)
                .addBox(-3.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
            PartPose.offset(-3.0F, 18.0F, 6.0F));
        
        // Tail
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(44, 26)
                .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 2.0F),
            PartPose.offset(0.0F, 9.0F, 8.0F));
        
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        float legSwing = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        
        this.frontLeftLeg.xRot = legSwing;
        this.frontRightLeg.xRot = -legSwing;
        this.backLeftLeg.xRot = -legSwing;
        this.backRightLeg.xRot = legSwing;
        
        this.tail.yRot = Mth.sin(ageInTicks * 0.2F) * 0.3F;
    }
}
