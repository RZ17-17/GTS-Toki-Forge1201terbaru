
package net.mcreator.gts.potion;

import net.minecraftforge.common.EffectCures;
import net.minecraftforge.common.EffectCure;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.gts.GtsMod;

import java.util.Set;

public class GtsShrinkEffectMobEffect extends MobEffect {
	public GtsShrinkEffectMobEffect() {
		super(MobEffectCategory.HARMFUL, -1);
		this.addAttributeModifier(GtsModAttributes.SCALE.get(), new ResourceLocation(GtsMod.MODID, "effect.gts_shrink_effect_0"), -0.5, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
		cures.add(EffectCures.MILK);
		cures.add(EffectCures.PROTECTED_BY_TOTEM);
		cures.add(EffectCures.HONEY);
	}
}
