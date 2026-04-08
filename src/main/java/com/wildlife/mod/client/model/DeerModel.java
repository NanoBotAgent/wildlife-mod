package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.DeerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class DeerModel<T extends DeerEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftAntler;
    private final ModelPart rightAntler;
    private final ModelPart tail;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;

    public DeerModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leftAntler = head.getChild("left_antler");
        this.rightAntler = head.getChild("right_antler");
        this.tail = root.getChild("tail");
        this.frontLeftLeg = root.getChild("front_left_leg");
        this.frontRightLeg = root.getChild("front_right_leg");
        this.backLeftLeg = root.getChild("back_left_leg");
        this.backRightLeg = root.getChild("back_right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        PartDefinition body = root.addOrReplaceChild("body", 
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -6.0F, -8.0F, 8.0F, 8.0F, 16.0F),
            PartPose.offset(0.0F, 12.0F, 0.0F));
        
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 24)
                .addBox(-2.5F, -4.0F, -4.0F, 5.0F, 5.0F, 6.0F),
            PartPose.offset(0.0F, 8.0F, -12.0F));
        
        head.addOrReplaceChild("snout",
            CubeListBuilder.create()
                .texOffs(22, 24)
                .addBox(-1.5F, 0.0F, -7.0F, 3.0F, 3.0F, 3.0F),
            PartPose.offset(0.0F, 0.0F, -4.0F));
        
        PartDefinition leftAntler = head.addOrReplaceChild("left_antler",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F),
            PartPose.offset(2.0F, -4.0F, -2.0F));
        
        leftAntler.addOrReplaceChild("branch1",
            CubeListBuilder.create()
                .texOffs(32, 10)
                .addBox(0.0F, -3.0F, 0.0F, 1.0F, 4.0F, 1.0F),
            PartPose.offset(-2.0F, -4.0F, 0.0F));
        
        PartDefinition rightAntler = head.addOrReplaceChild("right_antler",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F),
            PartPose.offset(-2.0F, -4.0F, -2.0F));
        
        rightAntler.addOrReplaceChild("branch1",
            CubeListBuilder.create()
                .texOffs(32, 10)
                .addBox(-1.0F, -3.0F, 0.0F, 1.0F, 4.0F, 1.0F),
            PartPose.offset(2.0F, -4.0F, 0.0F));
        
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(40, 0)
                .addBox(0.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(2.5F, -3.0F, 0.0F));
        
        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(40, 0)
                .addBox(-2.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(-2.5F, -3.0F, 0.0F));
        
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(44, 0)
                .addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 2.0F),
            PartPose.offset(0.0F, 10.0F, 8.0F));
        
        root.addOrReplaceChild("front_left_leg",
            CubeListBuilder.create()
                .texOffs(16, 35)
                .addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
            PartPose.offset(2.0F, 16.0F, -6.0F));
        
        root.addOrReplaceChild("front_right_leg",
            CubeListBuilder.create()
                .texOffs(16, 35)
                .addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
            PartPose.offset(-2.0F, 16.0F, -6.0F));
        
        root.addOrReplaceChild("back_left_leg",
            CubeListBuilder.create()
                .texOffs(16, 35)
                .addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
            PartPose.offset(2.0F, 16.0F, 4.0F));
        
        root.addOrReplaceChild("back_right_leg",
            CubeListBuilder.create()
                .texOffs(16, 35)
                .addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
            PartPose.offset(-2.0F, 16.0F, 4.0F));
        
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        if (entity.isGrazing()) {
            this.head.xRot = 0.5F;
        }
        
        float legSwing = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        
        this.frontLeftLeg.xRot = legSwing;
        this.frontRightLeg.xRot = -legSwing;
        this.backLeftLeg.xRot = -legSwing;
        this.backRightLeg.xRot = legSwing;
        
        this.tail.yRot = Mth.sin(ageInTicks * 0.1F) * 0.2F;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        
        if (!entity.hasAntlers()) {
            this.leftAntler.visible = false;
            this.rightAntler.visible = false;
        }
    }
}
