package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.TapirEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class TapirModel<T extends TapirEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart trunk;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;

    public TapirModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.trunk = root.getChild("trunk");
        this.frontLeftLeg = root.getChild("front_left_leg");
        this.frontRightLeg = root.getChild("front_right_leg");
        this.backLeftLeg = root.getChild("back_left_leg");
        this.backRightLeg = root.getChild("back_right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Large rounded body
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-5.0F, -6.0F, -8.0F, 10.0F, 9.0F, 16.0F),
            PartPose.offset(0.0F, 12.0F, 0.0F));
        
        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 25)
                .addBox(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 6.0F),
            PartPose.offset(0.0F, 11.0F, -14.0F));
        
        // Trunk/snout
        head.addOrReplaceChild("trunk",
            CubeListBuilder.create()
                .texOffs(24, 25)
                .addBox(-1.5F, 0.0F, -4.0F, 3.0F, 3.0F, 5.0F),
            PartPose.offset(0.0F, 1.0F, -5.0F));
        
        // Ears
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(38, 25)
                .addBox(0.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(3.0F, -2.0F, 0.0F));
        
        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(38, 25)
                .addBox(-2.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F),
            PartPose.offset(-3.0F, -2.0F, 0.0F));
        
        // Legs
        root.addOrReplaceChild("front_left_leg",
            CubeListBuilder.create()
                .texOffs(0, 36)
                .addBox(0.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F),
            PartPose.offset(3.0F, 16.0F, -6.0F));
        
        root.addOrReplaceChild("front_right_leg",
            CubeListBuilder.create()
                .texOffs(0, 36)
                .addBox(-3.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F),
            PartPose.offset(-3.0F, 16.0F, -6.0F));
        
        root.addOrReplaceChild("back_left_leg",
            CubeListBuilder.create()
                .texOffs(0, 36)
                .addBox(0.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F),
            PartPose.offset(3.0F, 16.0F, 6.0F));
        
        root.addOrReplaceChild("back_right_leg",
            CubeListBuilder.create()
                .texOffs(0, 36)
                .addBox(-3.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F),
            PartPose.offset(-3.0F, 16.0F, 6.0F));
        
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
        
        // Ear flick
        float earFlick = entity.getEarFlick(0);
        this.head.getChild("left_ear").zRot = earFlick;
        this.head.getChild("right_ear").zRot = -earFlick;
    }
}
