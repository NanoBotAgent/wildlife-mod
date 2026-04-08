package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.ToucanEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ToucanModel<T extends ToucanEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart beak;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public ToucanModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.beak = root.getChild("beak");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.tail = root.getChild("tail");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Compact body
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.5F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F),
            PartPose.offset(0.0F, 17.0F, 0.0F));
        
        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 11)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 3.0F),
            PartPose.offset(0.0F, 16.0F, -5.0F));
        
        // Large colorful beak
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(14, 11)
                .addBox(-1.5F, -1.0F, -6.0F, 3.0F, 2.0F, 6.0F),
            PartPose.offset(0.0F, 0.5F, -2.0F));
        
        // Wings
        root.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 17)
                .addBox(0.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F),
            PartPose.offset(2.5F, 17.0F, 0.0F));
        
        root.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 17)
                .addBox(-1.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F),
            PartPose.offset(-2.5F, 17.0F, 0.0F));
        
        // Long tail
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(10, 17)
                .addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 6.0F),
            PartPose.offset(0.0F, 17.0F, 3.0F));
        
        // Legs
        root.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(22, 17)
                .addBox(0.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(1.0F, 21.0F, 0.0F));
        
        root.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(22, 17)
                .addBox(-1.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(-1.0F, 21.0F, 0.0F));
        
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        // Wing flapping
        float wingFlap = entity.getWingFlap(0);
        this.leftWing.zRot = wingFlap;
        this.rightWing.zRot = -wingFlap;
        
        // Leg animation
        float legSwing = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.leftLeg.xRot = legSwing;
        this.rightLeg.xRot = -legSwing;
        
        // Beak open animation
        float beakOpen = entity.getBeakOpen(0);
        this.beak.yRot += beakOpen * 0.1F;
    }
}
