package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.DuckEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class DuckModel<T extends DuckEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public DuckModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.tail = root.getChild("tail");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Round body
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -3.0F, -4.0F, 6.0F, 5.0F, 8.0F),
            PartPose.offset(0.0F, 17.0F, 0.0F));
        
        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 13)
                .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 3.0F),
            PartPose.offset(0.0F, 16.0F, -6.0F));
        
        // Bill
        head.addOrReplaceChild("bill",
            CubeListBuilder.create()
                .texOffs(14, 13)
                .addBox(-1.5F, 0.0F, -2.0F, 3.0F, 1.0F, 2.0F),
            PartPose.offset(0.0F, 0.5F, -2.0F));
        
        // Wings
        root.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(0.0F, -2.0F, -2.0F, 1.0F, 4.0F, 5.0F),
            PartPose.offset(3.0F, 17.0F, 0.0F));
        
        root.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(-1.0F, -2.0F, -2.0F, 1.0F, 4.0F, 5.0F),
            PartPose.offset(-3.0F, 17.0F, 0.0F));
        
        // Tail
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(12, 19)
                .addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F),
            PartPose.offset(0.0F, 17.0F, 4.0F));
        
        // Legs
        root.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(18, 19)
                .addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(1.0F, 21.0F, 0.0F));
        
        root.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(18, 19)
                .addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(-1.0F, 21.0F, 0.0F));
        
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        // Wing flapping
        float wingFlap = entity.getWingFlap(0);
        this.leftWing.zRot = wingFlap;
        this.rightWing.zRot = -wingFlap;
        
        // Leg animation
        float legSwing = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.leftLeg.xRot = legSwing;
        this.rightLeg.xRot = -legSwing;
        
        // Quack animation
        float quack = entity.getQuackAnim(0);
        this.head.yRot += quack * 0.1F;
    }
}
