package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.SparrowEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SparrowModel<T extends SparrowEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    
    public SparrowModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        this.tail = body.getChild("tail");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - small, round
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.0F, -2.0F, -3.0F, 4.0F, 3.0F, 5.0F),
            PartPose.offset(0.0F, 21.0F, 0.0F));
        
        // Head - round with beak
        PartDefinition head = body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 8)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 3.0F),
            PartPose.offset(0.0F, -1.0F, -4.0F));
        
        // Beak
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(14, 8)
                .addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -2.0F));
        
        // Wings
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(18, 0)
                .addBox(0.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F),
            PartPose.offset(2.0F, -1.0F, 0.0F));
        
        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(18, 0)
                .addBox(-2.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F),
            PartPose.offset(-2.0F, -1.0F, 0.0F));
        
        // Tail
        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 15)
                .addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 3.0F),
            PartPose.offset(0.0F, 0.0F, 2.0F));
        
        return LayerDefinition.create(mesh, 32, 32);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        this.leftWing.zRot = wingAngle;
        this.rightWing.zRot = -wingAngle;
        
        // Peck animation
        float peck = entity.getPeckAngle();
        this.head.xRot = peck;
        
        // Head look
        this.head.yRot = netHeadYaw * 0.017453292F;
    }
}
