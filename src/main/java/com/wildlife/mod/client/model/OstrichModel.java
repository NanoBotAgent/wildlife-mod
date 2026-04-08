package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.OstrichEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class OstrichModel<T extends OstrichEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public OstrichModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.neck = root.getChild("neck");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.tail = root.getChild("tail");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Large round body
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F),
            PartPose.offset(0.0F, 14.0F, 0.0F));
        
        // Long neck
        PartDefinition neck = root.addOrReplaceChild("neck",
            CubeListBuilder.create()
                .texOffs(0, 20)
                .addBox(-1.5F, -8.0F, -1.5F, 3.0F, 8.0F, 3.0F),
            PartPose.offset(0.0F, 10.0F, -8.0F));
        
        // Head
        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(12, 20)
                .addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 4.0F),
            PartPose.offset(0.0F, -8.0F, 0.0F));
        
        // Beak
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(26, 20)
                .addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F),
            PartPose.offset(0.0F, 0.5F, -3.0F));
        
        // Eyes
        head.addOrReplaceChild("left_eye",
            CubeListBuilder.create()
                .texOffs(38, 20)
                .addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F),
            PartPose.offset(1.5F, -0.5F, -2.0F));
        
        head.addOrReplaceChild("right_eye",
            CubeListBuilder.create()
                .texOffs(38, 20)
                .addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F),
            PartPose.offset(-1.5F, -0.5F, -2.0F));
        
        // Wings (small, decorative)
        root.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 31)
                .addBox(0.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F),
            PartPose.offset(5.0F, 14.0F, 0.0F));
        
        root.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 31)
                .addBox(-1.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F),
            PartPose.offset(-5.0F, 14.0F, 0.0F));
        
        // Tail feathers
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(14, 31)
                .addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 5.0F),
            PartPose.offset(0.0F, 14.0F, 5.0F));
        
        // Long legs
        root.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(26, 31)
                .addBox(0.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F),
            PartPose.offset(2.0F, 14.0F, 0.0F));
        
        root.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(26, 31)
                .addBox(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F),
            PartPose.offset(-2.0F, 14.0F, 0.0F));
        
        return LayerDefinition.create(mesh, 64, 48);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        // Running animation
        float legSwing = entity.getLegSwing(0);
        this.leftLeg.xRot = legSwing;
        this.rightLeg.xRot = -legSwing;
        
        // Neck bobbing
        float neckBob = entity.getNeckBob(0);
        this.neck.xRot = neckBob;
        
        // Wing flapping when running
        if (entity.isRunning()) {
            this.leftWing.zRot = Mth.sin(ageInTicks * 0.5F) * 0.3F;
            this.rightWing.zRot = -Mth.sin(ageInTicks * 0.5F) * 0.3F;
        }
        
        // Head down animation
        if (entity.isHeadDown()) {
            this.neck.xRot = 0.5F;
            this.head.xRot = 0.3F;
        }
    }
}
