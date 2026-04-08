package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.RobinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class RobinModel<T extends RobinEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart breast;
    
    public RobinModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        this.tail = body.getChild("tail");
        this.breast = body.getChild("breast");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - slightly larger than sparrow
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.5F, -2.0F, -3.0F, 5.0F, 3.0F, 6.0F),
            PartPose.offset(0.0F, 21.0F, 0.0F));
        
        // Head
        PartDefinition head = body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 9)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 3.0F),
            PartPose.offset(0.0F, -1.0F, -4.0F));
        
        // Beak - pointed
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(14, 9)
                .addBox(-0.5F, 0.0F, -2.5F, 1.0F, 1.0F, 2.5F),
            PartPose.offset(0.0F, 0.0F, -2.0F));
        
        // Red breast
        body.addOrReplaceChild("breast",
            CubeListBuilder.create()
                .texOffs(22, 0)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F),
            PartPose.offset(0.0F, 0.0F, -1.0F));
        
        // Wings
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
        
        // Tail
        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(14, 16)
                .addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 3.0F),
            PartPose.offset(0.0F, 0.0F, 3.0F));
        
        return LayerDefinition.create(mesh, 48, 32);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        this.leftWing.zRot = wingAngle;
        this.rightWing.zRot = -wingAngle;
        
        // Hop animation
        float hop = entity.getHopOffset();
        this.body.y = 21.0F + hop * 2;
        
        // Sing animation - head tilt
        if (entity.isSinging()) {
            this.head.xRot = Mth.sin(ageInTicks * 0.3F) * 0.1F;
        }
        
        // Head look
        this.head.yRot = netHeadYaw * 0.017453292F;
    }
}
