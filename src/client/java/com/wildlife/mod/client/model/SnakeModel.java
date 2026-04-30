package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Snake model — limbless reptile with serpentine movement.
 * Real snake: lateral undulation (S-curve), rectilinear (caterpillar),
 * concertina (coil-extend), tongue flicking for chemoreception.
 */
public class SnakeModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart head;
    private final ModelPart seg1, seg2, seg3, seg4, seg5;
    private final ModelPart tailTip;
    private final ModelPart tongue;

    public SnakeModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.seg1 = head.getChild("seg1");
        this.seg2 = seg1.getChild("seg2");
        this.seg3 = seg2.getChild("seg3");
        this.seg4 = seg3.getChild("seg4");
        this.seg5 = seg4.getChild("seg5");
        this.tailTip = seg5.getChild("tail_tip");
        this.tongue = head.getChild("tongue");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5f, -1.5f, -5f, 5f, 3f, 5f)
                .texOffs(0, 8).addBox(-1.5f, -1f, -7f, 3f, 2f, 2f),
            PartPose.offset(0, 21f, -10f));

        head.addOrReplaceChild("tongue",
            CubeListBuilder.create().texOffs(10, 8).addBox(-0.5f, 0, -3f, 1f, 0, 3f),
            PartPose.offset(0, 0.5f, -7f));

        PartDefinition s1 = head.addOrReplaceChild("seg1",
            CubeListBuilder.create().texOffs(0, 12).addBox(-2f, -1.5f, 0, 4f, 3f, 5f),
            PartPose.offset(0, 0, 0));
        PartDefinition s2 = s1.addOrReplaceChild("seg2",
            CubeListBuilder.create().texOffs(0, 12).addBox(-2f, -1.5f, 0, 4f, 3f, 5f),
            PartPose.offset(0, 0, 5));
        PartDefinition s3 = s2.addOrReplaceChild("seg3",
            CubeListBuilder.create().texOffs(0, 12).addBox(-2f, -1.5f, 0, 4f, 3f, 5f),
            PartPose.offset(0, 0, 5));
        PartDefinition s4 = s3.addOrReplaceChild("seg4",
            CubeListBuilder.create().texOffs(0, 12).addBox(-1.5f, -1.5f, 0, 3f, 3f, 5f),
            PartPose.offset(0, 0, 5));
        PartDefinition s5 = s4.addOrReplaceChild("seg5",
            CubeListBuilder.create().texOffs(0, 12).addBox(-1f, -1f, 0, 2f, 2f, 5f),
            PartPose.offset(0, 0, 5));
        s5.addOrReplaceChild("tail_tip",
            CubeListBuilder.create().texOffs(0, 17).addBox(-0.5f, -0.5f, 0, 1f, 1f, 4f),
            PartPose.offset(0, 0, 5));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Lateral undulation — S-curve propagates from head to tail
        // Each segment has increasing amplitude and phase delay
        float sp = 0.7f;
        this.head.yRot = (float)(Math.sin(wa*sp)*0.15f*ls);
        this.seg1.yRot = (float)(Math.sin(wa*sp-0.4f)*0.2f*ls);
        this.seg2.yRot = (float)(Math.sin(wa*sp-0.8f)*0.28f*ls);
        this.seg3.yRot = (float)(Math.sin(wa*sp-1.2f)*0.35f*ls);
        this.seg4.yRot = (float)(Math.sin(wa*sp-1.6f)*0.3f*ls);
        this.seg5.yRot = (float)(Math.sin(wa*sp-2.0f)*0.22f*ls);
        this.tailTip.yRot = (float)(Math.sin(wa*sp-2.4f)*0.15f*ls);

        // Subtle body lift during slither
        this.head.xRot = (float)(Math.abs(Math.sin(wa*sp))*0.04f*ls);

        // Tongue flicking — snake flicks tongue to smell
        float flick = (float)(Math.sin(age*2.5f)*0.5f+0.5f);
        this.tongue.z = flick > 0.75f ? -7f + flick*2f : -7f;

        if (ls < 0.01f) {
            // Coiled idle — slow sway, regular tongue flicks
            this.head.yRot = (float)(Math.sin(age*0.12f)*0.12f);
            this.seg1.yRot = (float)(Math.sin(age*0.12f-0.2f)*0.1f);
            this.seg2.yRot = (float)(Math.sin(age*0.12f-0.4f)*0.08f);
            this.seg3.yRot = (float)(Math.sin(age*0.12f-0.6f)*0.06f);
            this.seg4.yRot = (float)(Math.sin(age*0.12f-0.8f)*0.04f);
            this.seg5.yRot = (float)(Math.sin(age*0.12f-1.0f)*0.03f);
            this.tailTip.yRot = (float)(Math.sin(age*0.12f-1.2f)*0.02f);
        }
    }
}