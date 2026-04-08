package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.LadybugEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class LadybugModel<T extends LadybugEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    
    public LadybugModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - round, red with spots
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F),
            PartPose.offset(0.0F, 22.0F, 0.0F));
        
        // Head - small, black
        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 6)
                .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -3.0F));
        
        // Wing covers - dome shaped
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(12, 0)
                .addBox(0.0F, -1.0F, -2.0F, 2.0F, 1.0F, 4.0F),
            PartPose.offset(0.0F, -1.0F, 0.0F));
        
        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(12, 0)
                .addBox(-2.0F, -1.0F, -2.0F, 2.0F, 1.0F, 4.0F),
            PartPose.offset(0.0F, -1.0F, 0.0F));
        
        return LayerDefinition.create(mesh, 32, 16);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing opening when flying
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        if (entity.isFlying()) {
            this.leftWing.zRot = wingAngle * 0.5F;
            this.rightWing.zRot = -wingAngle * 0.5F;
            this.leftWing.yRot = 0.3F;
            this.rightWing.yRot = -0.3F;
        } else {
            this.leftWing.zRot = 0;
            this.rightWing.zRot = 0;
            this.leftWing.yRot = 0;
            this.rightWing.yRot = 0;
        }
        
        // Head bob when walking
        this.head.y = Mth.sin(ageInTicks * 0.5F) * 0.1F;
    }
}
