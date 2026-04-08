package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.BluejayEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BluejayModel<T extends BluejayEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart crest;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    
    public BluejayModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.crest = head.getChild("crest");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        this.tail = body.getChild("tail");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - blue/white
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.5F, -2.0F, -3.0F, 5.0F, 3.0F, 6.0F),
            PartPose.offset(0.0F, 21.0F, 0.0F));
        
        // Head - blue crest
        PartDefinition head = body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 9)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 3.0F),
            PartPose.offset(0.0F, -1.0F, -4.0F));
        
        // Crest - prominent bluejay feature
        head.addOrReplaceChild("crest",
            CubeListBuilder.create()
                .texOffs(14, 9)
                .addBox(-1.5F, -4.0F, -1.0F, 3.0F, 4.0F, 1.0F),
            PartPose.offset(0.0F, -2.0F, 0.0F));
        
        // Beak
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(22, 9)
                .addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -2.0F));
        
        // Wings - blue with white bars
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(0.0F, -1.0F, -2.0F, 2.0F, 2.0F, 5.0F),
            PartPose.offset(2.5F, -1.0F, 0.0F));
        
        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(-2.0F, -1.0F, -2.0F, 2.0F, 2.0F, 5.0F),
            PartPose.offset(-2.5F, -1.0F, 0.0F));
        
        // Tail - blue with white tips
        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(14, 16)
                .addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 4.0F),
            PartPose.offset(0.0F, 0.0F, 3.0F));
        
        return LayerDefinition.create(mesh, 32, 32);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        this.leftWing.zRot = wingAngle;
        this.rightWing.zRot = -wingAngle;
        
        // Crest animation
        float crestAngle = entity.getCrestAngle();
        this.crest.xRot = -crestAngle;
        
        // Call animation - head bob
        if (entity.isCalling()) {
            this.head.xRot = Mth.sin(ageInTicks * 0.5F) * 0.15F;
        }
        
        // Head look
        this.head.yRot = netHeadYaw * 0.017453292F;
    }
}
