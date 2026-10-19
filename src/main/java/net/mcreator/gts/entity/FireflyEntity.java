package net.mcreator.gts.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FireflyEntity extends TokiVariantEntity {
    public FireflyEntity(EntityType<FireflyEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public String getVariantModelPath() {
        return "geo/firefly.geo.json";
    }

    @Override
    public String getVariantAnimationPath() {
        return "animations/firefly.animation.json";
    }

    @Override
    public String getVariantTexturePath() {
        return "textures/entities/fireflytexture.png";
    }
}
