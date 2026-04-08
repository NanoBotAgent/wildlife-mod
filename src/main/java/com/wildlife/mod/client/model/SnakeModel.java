package com.wildlife.mod.client.model;

import com.wildlife.mod.entity.SnakeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SnakeModel<T extends SnakeEntity> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart[] bodySegments;
    private final ModelPart tail;
    private final ModelPart tongue;
    private final ModelPart hood; // For cobra variant
    
    private static final int SEGMENT_COUNT = 8;

    public SnakeModel(ModelPart root) {
        super(root, false, 4.0F, 2.0F, 2.0F, 2.0F, 14);
        
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.tongue = this.head.getChild("tongue");
        this.hood = this.head.getChild("hood");
        
        // Initialize body segments
        this.bodySegments = new ModelPart[SEGMENT_COUNT];
        for (int i = 0; i < SEGMENT_COUNT; i++) {
            this.bodySegments[i] = root.getChild("segment_" + i);
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        // Head
        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.0F, -1.5F, -4.0F, 4.0F, 2.0F, 4.0F),
            PartPose.offset(0.0F, 21.5F, -8.0F));
        
        // Eyes
        head.addOrReplaceChild("left_eye",
            CubeListBuilder.create()
                .texOffs(16, 0)
                .addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F),
            PartPose.offset(2.0F, -0.5F, -2.0F));
        
        head.addOrReplaceChild("right_eye",
            CubeListBuilder.create()
                .texOffs(16, 0)
                .addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F),
            PartPose.offset(-2.0F, -0.5F, -2.0F));
        
        // Tongue (forked)
        head.addOrReplaceChild("tongue",
            CubeListBuilder.create()
                .texOffs(20, 0)
                .addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F),
            PartPose.offset(0.0F, 0.0F, -4.0F));
        
        // Hood (for cobra variant, usually hidden)
        head.addOrReplaceChild("hood",
            CubeListBuilder.create()
                .texOffs(28, 0)
                .addBox(-4.0F, -3.0F, -2.0F, 8.0F, 4.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, 0.0F));
        
        // Body segments - creates slithering wave
        for (int i = 0; i < SEGMENT_COUNT; i++) {
            float zOffset = -4.0F + (i * 2.0F);
            float scale = 1.0F - (i * 0.05F);
            
            root.addOrReplaceChild("segment_" + i,
                CubeListBuilder.create()
                    .texOffs(0, 6)
                    .addBox(-1.5F * scale, -1.0F * scale, 0.0F, 
                            3.0F * scale, 2.0F * scale, 2.0F),
                PartPose.offset(0.0F, 22.0F, zOffset));
        }
        
        // Tail (tapered)
        root.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 4.0F),
            PartPose.offset(0.0F, 22.5F, 12.0F));
        
        // Tail tip (rattle for rattlesnake variant)
        tail.addOrReplaceChild("rattle",
            CubeListBuilder.create()
                .texOffs(10, 10)
                .addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F),
            PartPose.offset(0.0F, 0.0F, 4.0F));
        
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    protected void setupAnim(T entity, float limbSwing, float limbSwingAmount, 
                             float ageInTicks, float netHeadYaw, float headPitch) {
        
        // Head rotation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        
        // Slithering animation - wave propagates through body segments
        float slitherSpeed = entity.isMoving() ? 0.4F : 0.1F;
        float slitherAmplitude = entity.isMoving() ? 0.3F : 0.05F;
        
        for (int i = 0; i < SEGMENT_COUNT; i++) {
            float phase = ageInTicks * slitherSpeed + (i * 0.5F);
            float wave = Mth.sin(phase) * slitherAmplitude * (1.0F + i * 0.1F);
            
            this.bodySegments[i].yRot = wave;
            this.bodySegments[i].xRot = Mth.cos(phase * 0.5F) * 0.05F;
        }
        
        // Tail follows the wave
        float tailPhase = ageInTicks * slitherSpeed + (SEGMENT_COUNT * 0.5F);
        this.tail.yRot = Mth.sin(tailPhase) * slitherAmplitude * 1.5F;
        
        // Tongue flicking animation
        float tongueFlick = entity.getHissAnim(0);
        this.tongue.zScale = 1.0F + tongueFlick * 0.5F;
        this.tongue.visible = tongueFlick > 0.1F || entity.isHissing();
        
        // Hood flare for cobra variant
        if (entity.getVariant() == 2) { // Cobra
            float hissAnim = entity.getHissAnim(0);
            this.hood.visible = hissAnim > 0.3F;
            this.hood.yScale = 0.5F + hissAnim * 0.5F;
        } else {
            this.hood.visible = false;
        }
        
        // Rattle animation for rattlesnake variant
        if (entity.getVariant() == 1) { // Rattlesnake
            if (entity.isHissing()) {
                ModelPart rattle = this.tail.getChild("rattle");
                rattle.yRot = Mth.sin(ageInTicks * 2.0F) * 0.5F;
            }
        }
    }
}
