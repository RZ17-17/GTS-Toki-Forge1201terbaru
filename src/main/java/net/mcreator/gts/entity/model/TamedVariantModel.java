package net.mcreator.gts.entity.model;

import net.mcreator.gts.entity.TokiVariantEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TamedVariantModel extends GeoModel<TokiVariantEntity> {
    @Override public ResourceLocation getModelResource(TokiVariantEntity entity) {
        return new ResourceLocation("gts:" + entity.getVariantModelPath());
    }
    @Override public ResourceLocation getTextureResource(TokiVariantEntity entity) {
        return new ResourceLocation("gts:" + entity.getVariantTexturePath());
    }
    @Override public ResourceLocation getAnimationResource(TokiVariantEntity entity) {
        return new ResourceLocation("gts:" + entity.getVariantAnimationPath());
    }
}
