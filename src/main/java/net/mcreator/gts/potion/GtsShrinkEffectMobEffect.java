package net.mcreator.gts.potion;


import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.mcreator.gts.GtsMod;
import net.mcreator.gts.init.GtsModAttributes;



public class GtsShrinkEffectMobEffect extends MobEffect {
	public GtsShrinkEffectMobEffect() {
		super(MobEffectCategory.HARMFUL, -1);
		// Minecraft 1.20.1 expects this attribute modifier identifier to be a UUID,
		// not a namespaced resource location.
		this.addAttributeModifier(GtsModAttributes.SCALE.get(), "4d3c2b1a-9f8e-4c7b-a6d5-1e2f3a4b5c6d", -0.5, AttributeModifier.Operation.MULTIPLY_BASE);
	}


}
