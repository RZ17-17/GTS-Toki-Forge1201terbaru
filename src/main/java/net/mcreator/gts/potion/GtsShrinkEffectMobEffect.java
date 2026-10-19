package net.mcreator.gts.potion;

import java.util.UUID;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.mcreator.gts.init.GtsModAttributes;

public class GtsShrinkEffectMobEffect extends MobEffect {
    private static final UUID SCALE_MODIFIER_UUID = UUID.fromString("3a6f3c2e-9f4b-4c1e-8d73-21c7b6e5a901");

    public GtsShrinkEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -1);
        this.addAttributeModifier(
            GtsModAttributes.SCALE.get(),
            SCALE_MODIFIER_UUID,
            -0.5,
            AttributeModifier.Operation.MULTIPLY_BASE
        );
    }
}
