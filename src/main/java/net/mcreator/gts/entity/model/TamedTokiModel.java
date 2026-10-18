package net.mcreator.gts.entity.model;


import net.mcreator.gts.init.GtsModAttributes;
import software.bernie.geckolib.util.ClientUtils;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationProcessor;

import org.joml.Vector3d;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.gts.entity.TamedTokiEntity;

import java.util.Collection;

public class TamedTokiModel extends GeoModel<TamedTokiEntity> {
	@Override
	public ResourceLocation getAnimationResource(TamedTokiEntity entity) {
		return new ResourceLocation("gts:animations/toki_gecko.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TamedTokiEntity entity) {
		return new ResourceLocation("gts:geo/toki_gecko.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TamedTokiEntity entity) {
		return new ResourceLocation("gts:textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(TamedTokiEntity entity, long instanceId, AnimationState animationState) {
		GeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
		Player player = ClientUtils.getClientPlayer();
		if (player instanceof Entity playerEntity) {
			AnimationProcessor processor = getAnimationProcessor();
			Collection<GeoBone> bones = processor.getRegisteredBones();
			int index = 0;
			for (GeoBone bone : bones) {
				if (bone.getName().startsWith("S_")) {
					Vector3d bPos = bone.getPositionVector();
					Vec3 offset = new Vec3(bPos.x(), bPos.y(), -bPos.z()).scale((entity.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() / 16.0));
					offset = offset.yRot(Mth.DEG_TO_RAD * (float) -entity.getVisualRotationYInDegrees());
					entity.setPassengerSlotOffset(index, offset);
				}
			}
		}
	}
}
