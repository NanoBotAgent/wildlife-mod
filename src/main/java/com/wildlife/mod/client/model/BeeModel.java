package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.BeeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BeeModel<T extends BeeEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart abdomen;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart stinger;
    
    public BeeModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.abdomen = body.getChild("abdomen");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        this.stinger = abdomen.getChild("stinger");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - thorax
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F),
            PartPose.offset(0.0F, 21.0F, 0.0F));
        
        // Head
        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 8)
                .addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -3.0F));
        
        // Abdomen - striped
        body.addOrReplaceChild("abdomen",
            CubeListBuilder.create()
                .texOffs(16, 0)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 5.0F),
            PartPose.offset(0.0F, 0.0F, 2.0F));
        
        // Stinger
        abdomen.addOrReplaceChild("stinger",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F),
            PartPose.offset(0.0F, 1.0F, 5.0F));
        
        // Wings - translucent
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 13)
                .addBox(0.0F, -1.0F, -1.0F, 4.0F, 1.0F, 4.0F),
            PartPose.offset(2.0F, -2.0F, 0.0F));
        
        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 13)
                .addBox(-4.0F, -1.0F, -1.0F, 4.0F, 1.0F, 4.0F),
            PartPose.offset(-2.0F, -2.0F, 0.0F));
        
        return LayerDefinition.create(mesh, 32, 32);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping - fast
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        this.leftWing.zRot = wingAngle;
        this.rightWing.zRot = -wingAngle;
        
        // Bobbing
        float bob = entity.getBobOffset();
        this.body.y = 21.0F + bob * 2;
        
        // Abdomen sway
        this.abdomen.xRot = Mth.sin(ageInTicks * 0.3F) * 0.1F;
    }
}
