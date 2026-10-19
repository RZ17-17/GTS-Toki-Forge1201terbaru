package net.mcreator.gts.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class ModelGts<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("gts", "model_gts"), "main");
	public final ModelPart HeartBullet;

	public ModelGts(ModelPart root) {
		this.HeartBullet = root.getChild("HeartBullet");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition HeartBullet = partdefinition.addOrReplaceChild("HeartBullet", CubeListBuilder.create(), PartPose.offsetAndRotation(-15.25F, 24.0F, 0.0F, -1.5708F, 1.5708F, 0.0F));
		PartDefinition cube_r1 = HeartBullet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 10).addBox(0.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(7.75F, -25.5F, 0.5F, 0.0F, 0.0F, 3.1416F));
		PartDefinition cube_r2 = HeartBullet.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(12.75F, -22.0F, 0.5F, 0.0F, 0.0F, -2.5307F));
		PartDefinition cube_r3 = HeartBullet.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(12.75F, -16.0F, 0.5F, 0.0F, 0.0F, -1.5708F));
		PartDefinition cube_r4 = HeartBullet.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(10.5F, -10.5F, 0.5F, 0.0F, 0.0F, -1.1781F));
		PartDefinition cube_r5 = HeartBullet.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -1.0F, -1.0F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, 0.0F, -0.7854F));
		PartDefinition cube_r6 = HeartBullet.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(14, 8).addBox(0.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.75F, -25.5F, 0.5F, 0.0F, 0.0F, 2.1817F));
		PartDefinition cube_r7 = HeartBullet.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(14, 6).addBox(-5.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.75F, -25.5F, 0.5F, 0.0F, 0.0F, -2.1817F));
		PartDefinition cube_r8 = HeartBullet.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(14, 4).addBox(-5.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.75F, -25.5F, 0.5F, 0.0F, 0.0F, -3.1416F));
		PartDefinition cube_r9 = HeartBullet.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 8).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-12.75F, -22.0F, 0.5F, 0.0F, 0.0F, 2.5307F));
		PartDefinition cube_r10 = HeartBullet.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 6).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-12.75F, -16.0F, 0.5F, 0.0F, 0.0F, 1.5708F));
		PartDefinition cube_r11 = HeartBullet.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 4).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.5F, -10.5F, 0.5F, 0.0F, 0.0F, 1.1781F));
		PartDefinition cube_r12 = HeartBullet.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, 0.0F, 0.7854F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		HeartBullet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
