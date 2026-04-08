package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.MarmotEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MarmotModel<T extends MarmotEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;

    public MarmotModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.tail = root.getChild("tail");
        this.frontLeftLeg = root.getChild("front_left_leg");
        this.frontRightLeg = root.getChild("front_right_leg");
        this.backLeftLeg = root.getChild("back_left_leg");
        this.backRightLeg = root.getChild("back_right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Round chubby body
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 8.0F),
            PartPose.offset(0.0F, 16.0F, 0.0F));
        
        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 14)
                .addBox(-2.0F, -2.0F, -3.0F, 4.0F, 3.0F, 4.0F),
            PartPose.offset(0.0F, 15.0F, -7.0F));
        
        // Snout
        head.addOrReplaceChild("snout",
            CubeListBuilder.create()
                .texOffs(16, 14)
                .addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.5F, 2.0F),
            PartPose.offset(0.0F, 0.5F, -3.0F));
        
        // Ears
        head.addOrReplaceChild("left_ear",
            CubeListBuilder.create()
                .texOffs(24, 14)
                .addBox(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F),
            PartPose.offset(2.0F, -1.5F, 0.0F));
        
        head.addOrReplaceChild("right_ear",
            CubeListBuilder.create()
                .texOffs(24, 14)
                .addBox(-1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F),
            PartPose.offset(-2.0F, -1.5F, 0.0F));
        
        // Legs
        root.addOrReplaceChild("front_left_leg",
            CubeListBuilder.create()
                .texOffs(0, 21)
                .addBox(0.0F, 0.0F, -1.0F, 1.5F, 4.0F, 1.5F),
            PartPose.offset(2.0F, 20.0F, -3.0F));
        
        root.addOrReplaceChild("front_right_leg",
            CubeListBuilder.create()
                .texOffs(0, 21)
                .addBox(-1.5F, 0.0F, -1.0F, 1.5F, 4.0F, 1.5F),
            PartPose.offset(-2.0F, 20.0F, -3.0F));
        
        root.addOrReplaceChild("back_left_leg",
            CubeListBuilder.create()
                .texOffs(0, 21)
                .addBox(0.0F, 0.0F, -1.0F, 1.5F, 4.0F, 1.5F),
            PartPose.offset(2.0F, 20.0F, 3.0F));
        
        root.addOrReplaceChild("back_right_leg",
            CubeListBuilder.create()
                .texOffs(0, 21)
                .addBox(-1.5F, 0.0F, -1.0F, 1.5F, 4.0F, 1.5F),
            PartPose.offset(-2.0F, 20.0F, 3.0F));
        
        // Fluffy tail
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(6, 21)
                .addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F),
            PartPose.offset(0.0F, 17.0F, 4.0F));
        
        return LayerDefinition.create(mesh, 64, 32);
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
        
        // Standing pose
        if (entity.isStanding()) {
            this.body.xRot = -0.4F;
            this.head.xRot = 0.3F;
        } else {
            this.body.xRot = 0.0F;
        }
        
        // Tail wag
        this.tail.yRot = entity.getTailWag(0);
    }
}
