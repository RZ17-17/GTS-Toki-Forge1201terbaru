package net.mcreator.gts.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TamedAraEntity extends TokiVariantEntity {
    public TamedAraEntity(EntityType<TamedAraEntity> type, Level level) { super(type, level); }
    @Override public String getVariantModelPath() { return "geo/arageckolib.geo.json"; }
    @Override public String getVariantAnimationPath() { return "animations/arageckolib.animation.json"; }
    @Override public String getVariantTexturePath() { return "textures/entities/aratexture.png"; }
}
