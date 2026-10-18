package net.mcreator.gts.entity.model;

import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimationProcessor;

import org.joml.Vector3d;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.gts.entity.TokiDroneEntity;

import java.util.Collection;

public class TokiDroneModel extends GeoModel<TokiDroneEntity> {
	@Override
	public ResourceLocation getAnimationResource(TokiDroneEntity entity) {
		return new ResourceLocation("gts:animations/toki_drone.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TokiDroneEntity entity) {
		return new ResourceLocation("gts:geo/toki_drone.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TokiDroneEntity entity) {
		return new ResourceLocation("gts:textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(TokiDroneEntity entity, long instanceId, AnimationState animationState) {
		GeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
		Player player = ClientUtil.getClientPlayer();
		if (player instanceof Entity playerEntity) {
			AnimationProcessor processor = getAnimationProcessor();
			Collection<GeoBone> bones = processor.getRegisteredBones();
			int index = 0;
			for (GeoBone bone : bones) {
				if (bone.getName().startsWith("S_")) {
					Vector3d bPos = bone.getPositionVector();
					Vec3 offset = new Vec3(bPos.x(), bPos.y(), -bPos.z()).scale((1.0 / 16.0));
					offset = offset.yRot(Mth.DEG_TO_RAD * (float) -entity.getYRot());
					entity.setPassengerSlotOffset(index, offset);
					index += 1;
				}
			}
		}
	}
}
