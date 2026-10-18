
package net.mcreator.gts.potion;


import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;



public class GtsShrinkEffectMobEffect extends MobEffect {
	public GtsShrinkEffectMobEffect() {
		super(MobEffectCategory.HARMFUL, -1);
		this.addAttributeModifier(GtsModAttributes.SCALE.get(), new ResourceLocation(GtsMod.MODID, "effect.gts_shrink_effect_0"), -0.5, AttributeModifier.Operation.MULTIPLY_BASE);
	}


}
