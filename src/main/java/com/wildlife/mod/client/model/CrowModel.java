package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.CrowEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CrowModel<T extends CrowEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    
    public CrowModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        this.tail = body.getChild("tail");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - larger, sleek
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 7.0F),
            PartPose.offset(0.0F, 20.0F, 0.0F));
        
        // Head - large, intelligent look
        PartDefinition head = body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 11)
                .addBox(-2.5F, -2.5F, -2.0F, 5.0F, 5.0F, 3.0F),
            PartPose.offset(0.0F, -1.0F, -5.0F));
        
        // Beak - strong, pointed
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(16, 11)
                .addBox(-0.5F, 0.0F, -3.0F, 1.0F, 1.5F, 3.0F),
            PartPose.offset(0.0F, 0.0F, -2.0F));
        
        // Wings - long for gliding
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(0.0F, -1.0F, -3.0F, 3.0F, 2.0F, 7.0F),
            PartPose.offset(3.0F, -1.0F, 0.0F));
        
        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(-3.0F, -1.0F, -3.0F, 3.0F, 2.0F, 7.0F),
            PartPose.offset(-3.0F, -1.0F, 0.0F));
        
        // Tail - fan-shaped
        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(20, 19)
                .addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 4.0F),
            PartPose.offset(0.0F, 0.0F, 3.0F));
        
        return LayerDefinition.create(mesh, 48, 32);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping - slower, more powerful
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        
        if (entity.isGliding()) {
            // Wings spread for gliding
            this.leftWing.zRot = 0.3F;
            this.rightWing.zRot = -0.3F;
        } else {
            this.leftWing.zRot = wingAngle;
            this.rightWing.zRot = -wingAngle;
        }
        
        // Head look
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }
}
