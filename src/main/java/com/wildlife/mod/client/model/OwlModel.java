package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.OwlEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class OwlModel<T extends OwlEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public OwlModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 16);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.tail = root.getChild("tail");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Round body
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -4.0F, -3.0F, 8.0F, 8.0F, 6.0F),
            PartPose.offset(0.0F, 16.0F, 0.0F));
        
        // Large head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 14)
                .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 4.0F),
            PartPose.offset(0.0F, 14.0F, -5.0F));
        
        // Beak
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(20, 14)
                .addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.5F, 2.0F),
            PartPose.offset(0.0F, 1.0F, -3.0F));
        
        // Eyes
        head.addOrReplaceChild("left_eye",
            CubeListBuilder.create()
                .texOffs(26, 14)
                .addBox(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F),
            PartPose.offset(1.0F, -1.0F, -3.0F));
        
        head.addOrReplaceChild("right_eye",
            CubeListBuilder.create()
                .texOffs(26, 14)
                .addBox(-2.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F),
            PartPose.offset(-1.0F, -1.0F, -3.0F));
        
        // Ear tufts
        head.addOrReplaceChild("left_tuft",
            CubeListBuilder.create()
                .texOffs(32, 14)
                .addBox(0.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(2.0F, -3.0F, 0.0F));
        
        head.addOrReplaceChild("right_tuft",
            CubeListBuilder.create()
                .texOffs(32, 14)
                .addBox(-1.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(-2.0F, -3.0F, 0.0F));
        
        // Wings
        root.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 24)
                .addBox(0.0F, -3.0F, -2.0F, 1.0F, 6.0F, 5.0F),
            PartPose.offset(4.0F, 16.0F, 0.0F));
        
        root.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 24)
                .addBox(-1.0F, -3.0F, -2.0F, 1.0F, 6.0F, 5.0F),
            PartPose.offset(-4.0F, 16.0F, 0.0F));
        
        // Tail feathers
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(12, 24)
                .addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 4.0F),
            PartPose.offset(0.0F, 18.0F, 3.0F));
        
        // Legs
        root.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(24, 24)
                .addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(1.5F, 20.0F, 0.0F));
        
        root.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(24, 24)
                .addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(-1.5F, 20.0F, 0.0F));
        
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        // Wing flapping when flying
        float wingFlap = entity.getWingFlap(0);
        this.leftWing.zRot = wingFlap;
        this.rightWing.zRot = -wingFlap;
        
        // Head bobbing when perched
        if (entity.isPerched()) {
            this.head.yRot += Mth.sin(ageInTicks * 0.1F) * 0.1F;
        }
    }
}
