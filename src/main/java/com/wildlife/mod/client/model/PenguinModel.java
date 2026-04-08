package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.PenguinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PenguinModel<T extends PenguinEntity> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart beak;

    public PenguinModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.beak = this.head.getChild("beak");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Head (round, black/white pattern)
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 5.0F),
            PartPose.offset(0.0F, 15.0F, -4.0F));

        // Beak (orange)
        head.addOrReplaceChild("beak",
            CubeListBuilder.create()
                .texOffs(22, 0)
                .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, -3.0F));

        // Body (oval, white belly)
        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 11)
                .addBox(-4.0F, -6.0F, -3.0F, 8.0F, 12.0F, 6.0F),
            PartPose.offset(0.0F, 18.0F, 0.0F));

        // Wings (flippers)
        root.addOrReplaceChild("left_wing",
            CubeListBuilder.create()
                .texOffs(28, 11)
                .addBox(-1.0F, -5.0F, -2.0F, 2.0F, 10.0F, 4.0F),
            PartPose.offset(4.0F, 18.0F, 0.0F));

        root.addOrReplaceChild("right_wing",
            CubeListBuilder.create()
                .texOffs(28, 11)
                .addBox(-1.0F, -5.0F, -2.0F, 2.0F, 10.0F, 4.0F),
            PartPose.offset(-4.0F, 18.0F, 0.0F));

        // Legs (short, orange)
        root.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(40, 11)
                .addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 4.0F),
            PartPose.offset(2.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(40, 11)
                .addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 4.0F),
            PartPose.offset(-2.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        
        // Head rotation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        // Wing flap (faster when swimming)
        float wingFlap = entity.getWingFlap(ageInTicks);
        this.leftWing.zRot = wingFlap;
        this.rightWing.zRot = -wingFlap;

        // Waddle animation on land
        float waddle = entity.getWaddleAnim(ageInTicks);
        this.body.zRot = waddle;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 0.5F;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.5F + (float)Math.PI) * limbSwingAmount * 0.5F;

        // Sliding animation
        if (entity.isSliding()) {
            this.body.xRot = (float)Math.PI / 2;
            this.head.y = 12.0F;
            this.leftWing.xRot = -0.5F;
            this.rightWing.xRot = -0.5F;
        }

        // Swimming pose
        if (entity.isSwimming()) {
            this.body.xRot = 0.3F;
            this.head.xRot = -0.2F;
        }
    }
}
