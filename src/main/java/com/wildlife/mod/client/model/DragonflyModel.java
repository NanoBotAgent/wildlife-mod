package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.DragonflyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class DragonflyModel<T extends DragonflyEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart leftFrontWing;
    private final ModelPart rightFrontWing;
    private final ModelPart leftBackWing;
    private final ModelPart rightBackWing;
    
    public DragonflyModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.tail = body.getChild("tail");
        this.leftFrontWing = body.getChild("left_front_wing");
        this.rightFrontWing = body.getChild("right_front_wing");
        this.leftBackWing = body.getChild("left_back_wing");
        this.rightBackWing = body.getChild("right_back_wing");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - long, thin
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F),
            PartPose.offset(0.0F, 21.0F, 0.0F));
        
        // Head - large eyes
        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 6)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -4.0F));
        
        // Tail - long, segmented
        body.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(12, 0)
                .addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F),
            PartPose.offset(0.0F, 0.0F, 2.0F));
        
        // Front wings - long, narrow
        body.addOrReplaceChild("left_front_wing",
            CubeListBuilder.create()
                .texOffs(0, 12)
                .addBox(0.0F, 0.0F, -3.0F, 8.0F, 1.0F, 6.0F),
            PartPose.offset(1.0F, -1.0F, 0.0F));
        
        body.addOrReplaceChild("right_front_wing",
            CubeListBuilder.create()
                .texOffs(0, 12)
                .addBox(-8.0F, 0.0F, -3.0F, 8.0F, 1.0F, 6.0F),
            PartPose.offset(-1.0F, -1.0F, 0.0F));
        
        // Back wings - slightly smaller
        body.addOrReplaceChild("left_back_wing",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(0.0F, 0.0F, -2.0F, 6.0F, 1.0F, 5.0F),
            PartPose.offset(1.0F, -1.0F, 2.0F));
        
        body.addOrReplaceChild("right_back_wing",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(-6.0F, 0.0F, -2.0F, 6.0F, 1.0F, 5.0F),
            PartPose.offset(-1.0F, -1.0F, 2.0F));
        
        return LayerDefinition.create(mesh, 32, 32);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping - very fast
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        
        this.leftFrontWing.zRot = wingAngle;
        this.rightFrontWing.zRot = -wingAngle;
        this.leftBackWing.zRot = -wingAngle * 0.8F;
        this.rightBackWing.zRot = wingAngle * 0.8F;
        
        // Body tilt based on movement
        float tilt = entity.getBodyTilt();
        this.body.xRot = tilt;
        
        // Tail sway
        this.tail.yRot = Mth.sin(ageInTicks * 0.5F) * 0.1F;
    }
}
