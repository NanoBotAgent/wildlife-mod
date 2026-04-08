package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.ButterflyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ButterflyModel<T extends ButterflyEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart leftAntenna;
    private final ModelPart rightAntenna;
    
    public ButterflyModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        this.leftAntenna = body.getChild("left_antenna");
        this.rightAntenna = body.getChild("right_antenna");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - very small, 2 pixels long
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F),
            PartPose.offset(0.0F, 22.0F, 0.0F));
        
        // Wings - large, colorful
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 6)
                .addBox(0.0F, -1.0F, -4.0F, 6.0F, 1.0F, 8.0F),
            PartPose.offset(1.0F, 0.0F, 0.0F));
        
        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 6)
                .addBox(-6.0F, -1.0F, -4.0F, 6.0F, 1.0F, 8.0F),
            PartPose.offset(-1.0F, 0.0F, 0.0F));
        
        // Antennas - thin
        body.addOrReplaceChild("left_antenna",
            CubeListBuilder.create()
                .texOffs(20, 0)
                .addBox(0.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(0.5F, -1.0F, -2.0F));
        
        body.addOrReplaceChild("right_antenna",
            CubeListBuilder.create()
                .texOffs(20, 0)
                .addBox(0.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(-1.5F, -1.0F, -2.0F));
        
        return LayerDefinition.create(mesh, 32, 32);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        this.leftWing.zRot = wingAngle;
        this.rightWing.zRot = -wingAngle;
        
        // Antenna sway
        this.leftAntenna.xRot = Mth.sin(ageInTicks * 0.2F) * 0.1F;
        this.rightAntenna.xRot = Mth.sin(ageInTicks * 0.2F + 0.5F) * 0.1F;
    }
}
