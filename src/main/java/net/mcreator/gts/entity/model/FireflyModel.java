package net.mcreator.gts.entity.model;

import net.mcreator.gts.entity.FireflyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireflyModel extends GeoModel<FireflyEntity> {
    @Override
    public ResourceLocation getModelResource(FireflyEntity entity) {
        return new ResourceLocation("gts", entity.getVariantModelPath());
    }

    @Override
    public ResourceLocation getTextureResource(FireflyEntity entity) {
        return new ResourceLocation("gts", entity.getVariantTexturePath());
    }

    @Override
    public ResourceLocation getAnimationResource(FireflyEntity entity) {
        return new ResourceLocation("gts", entity.getVariantAnimationPath());
    }
}
