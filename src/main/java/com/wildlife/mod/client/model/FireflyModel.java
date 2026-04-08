package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.FireflyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FireflyModel<T extends FireflyEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart abdomen;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    
    public FireflyModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.abdomen = body.getChild("abdomen");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Body - small, dark
        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
            PartPose.offset(0.0F, 22.0F, 0.0F));
        
        // Head - tiny
        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 4)
                .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F),
            PartPose.offset(0.0F, 0.0F, -2.0F));
        
        // Abdomen - glowing part
        body.addOrReplaceChild("abdomen",
            CubeListBuilder.create()
                .texOffs(6, 0)
                .addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 3.0F),
            PartPose.offset(0.0F, 0.0F, 1.0F));
        
        // Wings - translucent
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 7)
                .addBox(0.0F, 0.0F, -1.0F, 3.0F, 1.0F, 3.0F),
            PartPose.offset(1.0F, -1.0F, 0.0F));
        
        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 7)
                .addBox(-3.0F, 0.0F, -1.0F, 3.0F, 1.0F, 3.0F),
            PartPose.offset(-1.0F, -1.0F, 0.0F));
        
        return LayerDefinition.create(mesh, 16, 16);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Wing flapping
        float wingAngle = entity.getWingAngle(ageInTicks - (float)entity.tickCount);
        this.leftWing.zRot = wingAngle;
        this.rightWing.zRot = -wingAngle;
        
        // Gentle bobbing
        float bob = Mth.sin(ageInTicks * 0.2F) * 0.05F;
        this.body.y = 22.0F + bob * 2;
        
        // Abdomen glow pulse is handled in renderer
    }
}
