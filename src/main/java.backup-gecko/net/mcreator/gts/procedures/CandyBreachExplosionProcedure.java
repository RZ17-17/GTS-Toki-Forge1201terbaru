package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import com.ibm.icu.number.Scale;

public class CandyBreachExplosionProcedure {
	public static void execute(LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double Y = 0;
		double Scale = 0;
		if (!world.isClientSide()) {
			Scale = sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity1.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0;
			Y = Scale / 2;
			for (int index0 = 0; index0 < (int) (sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity2.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0); index0++) {
				if (world instanceof Level _level && !_level.isClientSide())
					_level.explode(null, (sourceentity.getX()), (sourceentity.getY() + Y), (sourceentity.getZ()), (float) (3.5 * Math.atan(Scale / 3)), Level.ExplosionInteraction.MOB);
				if (world instanceof Level _level && !_level.isClientSide())
					_level.explode(null,
							(sourceentity.getX() + sourceentity.getLookAngle().x
									* (sourceentity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity9.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
							(sourceentity.getY() + Y),
							(sourceentity.getZ() + sourceentity.getLookAngle().z
									* (sourceentity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity13.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)),
							(float) (3.5 * Math.atan(Scale / 3)), Level.ExplosionInteraction.MOB);
				Y = Y + 2.8;
			}
		}
	}
}
