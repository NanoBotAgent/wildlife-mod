package com.wildlife.mod.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.wildlife.mod.client.renderstate.WildlifeRenderState;

/**
 * Ostrich model — fastest bird on land, long neck, powerful legs.
 * Real ostrich: running stride up to 5m, head bobs while walking,
 * wing display during courtship, kicks forward when threatened.
 */
public class OstrichModel extends EntityModel<WildlifeRenderState> {
    private final ModelPart body, neck, head, tail;
    private final ModelPart rightWing, leftWing;
    private final ModelPart rightUpperLeg, rightLowerLeg;
    private final ModelPart leftUpperLeg, leftLowerLeg;

    public OstrichModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.neck = body.getChild("neck");
        this.head = neck.getChild("head");
        this.tail = body.getChild("tail");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.rightUpperLeg = body.getChild("right_upper_leg"); this.rightLowerLeg = rightUpperLeg.getChild("right_lower_leg");
        this.leftUpperLeg = body.getChild("left_upper_leg"); this.leftLowerLeg = leftUpperLeg.getChild("left_lower_leg");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-5f, -6f, -8f, 10f, 9f, 14f),
            PartPose.offset(0, 12f, 0));

        PartDefinition neck = body.addOrReplaceChild("neck",
            CubeListBuilder.create().texOffs(34, 0).addBox(-2f, -12f, -2f, 4f, 12f, 4f),
            PartPose.offsetAndRotation(0, -5f, -7f, 0.5f, 0, 0));

        PartDefinition head = neck.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 23).addBox(-2f, -3f, -3.5f, 4f, 3.5f, 3.5f)
                .texOffs(14, 23).addBox(-1.5f, -1f, -5.5f, 3f, 2f, 2f),
            PartPose.offset(0, -11f, 0));

        body.addOrReplaceChild("right_wing",
            CubeListBuilder.create().texOffs(50, 0).addBox(-1f, -1f, -1f, 1f, 7f, 5f),
            PartPose.offset(-5f, -4f, -4f));
        body.addOrReplaceChild("left_wing",
            CubeListBuilder.create().texOffs(50, 0).addBox(0, -1f, -1f, 1f, 7f, 5f),
            PartPose.offset(5f, -4f, -4f));

        body.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 30).addBox(-3f, -2f, 0, 6f, 4f, 4f),
            PartPose.offset(0, -3f, 6f));

        PartDefinition rU = body.addOrReplaceChild("right_upper_leg",
            CubeListBuilder.create().texOffs(34, 16).addBox(-2f, 0, -2f, 4f, 10f, 4f),
            PartPose.offset(-3f, 3f, 2f));
        rU.addOrReplaceChild("right_lower_leg",
            CubeListBuilder.create().texOffs(34, 30).addBox(-1.5f, 0, 0, 3f, 10f, 3f),
            PartPose.offset(0, 10f, -1f));

        PartDefinition lU = body.addOrReplaceChild("left_upper_leg",
            CubeListBuilder.create().texOffs(34, 16).addBox(-2f, 0, -2f, 4f, 10f, 4f),
            PartPose.offset(3f, 3f, 2f));
        lU.addOrReplaceChild("left_lower_leg",
            CubeListBuilder.create().texOffs(34, 30).addBox(-1.5f, 0, 0, 3f, 10f, 3f),
            PartPose.offset(0, 10f, -1f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(WildlifeRenderState state) {
        super.setupAnim(state);
        float wa = state.walkAnimationPos;
        float ls = state.walkAnimationSpeed;
        float age = state.ageInTicks;

        // Powerful running stride — ostrich has longest legs of any bird
        float sp = 0.5f;
        this.rightUpperLeg.xRot = (float)(Math.sin(wa*sp)*0.6f*ls);
        this.leftUpperLeg.xRot = (float)(Math.sin(wa*sp+Math.PI)*0.6f*ls);
        this.rightLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp)*0.4f*ls));
        this.leftLowerLeg.xRot = Math.max(0, (float)(Math.sin(wa*sp+Math.PI)*0.4f*ls));

        // Body bobs with stride
        this.body.y = 12f + (float)(Math.abs(Math.sin(wa*1f))*0.8f*ls);

        // Neck and head bob — ostrich head bobs rhythmically while walking
        this.neck.xRot = 0.5f + (float)(Math.sin(wa*sp)*0.15f*ls);
        this.head.xRot = (float)(Math.sin(wa*sp)*-0.2f*ls);

        // Wing display while running — slight spread
        this.rightWing.zRot = (float)(Math.sin(wa*sp)*0.08f*ls) + 0.1f;
        this.leftWing.zRot = (float)(Math.sin(wa*sp+Math.PI)*0.08f*ls) - 0.1f;

        this.tail.xRot = (float)(Math.sin(age*0.3f)*0.05f);

        if (ls < 0.01f) {
            // Idle: neck sways, head looks around
            this.neck.yRot = (float)(Math.sin(age*0.06f)*0.2f);
            this.neck.xRot = 0.5f + (float)(Math.sin(age*0.05f)*0.1f);
            this.head.yRot = (float)(Math.sin(age*0.07f)*0.3f);
            this.rightWing.zRot = 0.1f;
            this.leftWing.zRot = -0.1f;
        } else {
            this.neck.yRot = 0;
            this.head.yRot = 0;
        }
    }
}