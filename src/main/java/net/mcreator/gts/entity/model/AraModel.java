package net.mcreator.gts.entity.model;

import net.mcreator.gts.entity.AraEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AraModel extends GeoModel<AraEntity> {
    @Override
    public ResourceLocation getModelResource(AraEntity entity) {
        return new ResourceLocation("gts", entity.getVariantModelPath());
    }

    @Override
    public ResourceLocation getTextureResource(AraEntity entity) {
        return new ResourceLocation("gts", entity.getVariantTexturePath());
    }

    @Override
    public ResourceLocation getAnimationResource(AraEntity entity) {
        return new ResourceLocation("gts", entity.getVariantAnimationPath());
    }
}
