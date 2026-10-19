package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import com.ibm.icu.number.Scale;

public class TokiLowerBreachExplosionProcedure {
	public static void execute(LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double X = 0;
		double Y = 0;
		double Z = 0;
		double Vx = 0;
		double Vy = 0;
		double Vz = 0;
		double Magnitude = 0;
		double X2 = 0;
		double Y2 = 0;
		double Z2 = 0;
		double Scale = 0;
		if (!world.isClientSide()) {
			X = sourceentity.getX() + sourceentity.getLookAngle().x;
			Y = sourceentity.getY() + (sourceentity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity4.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0);
			Z = sourceentity.getZ() + sourceentity.getLookAngle().z;
			X2 = sourceentity.getX()
					+ sourceentity.getLookAngle().x * (sourceentity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity9.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.2;
			Y2 = sourceentity.getY();
			Z2 = sourceentity.getZ()
					+ sourceentity.getLookAngle().z * (sourceentity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity13.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 1.2;
			Magnitude = new Vec3(X, Y, Z).distanceTo(new Vec3(X2, Y2, Z2));
			Vx = (X2 - X) / Magnitude;
			Vy = (Y2 - Y) / Magnitude;
			Vz = (Z2 - Z) / Magnitude;
			Scale = sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0;
			for (int index0 = 0; index0 < (int) Scale; index0++) {
				if (world instanceof Level _level && !_level.isClientSide())
					_level.explode(null, X, Y, Z,
							(float) (3.5 * Math.atan((sourceentity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity16.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) / 3)),
							Level.ExplosionInteraction.NONE);
				X = X + Vx * 1.5;
				Y = Y + Vy * 1.5;
				Z = Z + Vz * 1.5;
			}
		}
	}
}
