/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.gts as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.gts;

import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.Mth;
import net.minecraft.core.BlockPos;

//Credit to Mowzie's Mobs for custom pathfinding
public class GtsMoveHelper extends MoveControl {
	private float maxRotate = 90;

	public GtsMoveHelper(Mob entitylivingIn, float maxRotate) {
		super(entitylivingIn);
		this.maxRotate = maxRotate;
	}

	public void tick() {
		if (this.operation == GtsMoveHelper.Operation.STRAFE) {
			float f = (float) this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
			float f1 = (float) this.speedModifier * f;
			float f2 = this.strafeForwards;
			float f3 = this.strafeRight;
			float f4 = Mth.sqrt(f2 * f2 + f3 * f3);
			if (f4 < 1.0F) {
				f4 = 1.0F;
			}
			f4 = f1 / f4;
			f2 = f2 * f4;
			f3 = f3 * f4;
			float f5 = Mth.sin(this.mob.getYRot() * 0.017453292F);
			float f6 = Mth.cos(this.mob.getYRot() * 0.017453292F);
			float f7 = f2 * f6 - f3 * f5;
			float f8 = f3 * f6 + f2 * f5;
			PathNavigation pathnavigate = this.mob.getNavigation();
			if (pathnavigate != null) {
				NodeEvaluator nodeprocessor = pathnavigate.getNodeEvaluator();
				if (nodeprocessor != null && WalkNodeEvaluator.getBlockPathTypeStatic(this.mob.level(), new BlockPos.MutableBlockPos(Mth.floor(this.mob.getX() + f7), Mth.floor(this.mob.getY()), Mth.floor(this.mob.getZ() + f8))) != BlockPathTypes.WALKABLE) {
					this.strafeForwards = 1.0F;
					this.strafeRight = 0.0F;
					f1 = f;
				}
			}
			this.mob.setSpeed(f1);
			this.mob.setZza(this.strafeForwards);
			this.mob.setXxa(this.strafeRight);
			this.operation = GtsMoveHelper.Operation.WAIT;
		} else if (this.operation == GtsMoveHelper.Operation.MOVE_TO) {
			this.operation = GtsMoveHelper.Operation.WAIT;
			double d0 = this.wantedX - this.mob.getX();
			double d1 = this.wantedZ - this.mob.getZ();
			double d2 = this.wantedY - this.mob.getY();
			double d3 = d0 * d0 + d2 * d2 + d1 * d1;
			if (d3 < 2.500000277905201E-7D) {
				this.mob.setZza(0.0F);
				return;
			}
			float f9 = (float) (Mth.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
			this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, maxRotate));
			this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
			if (d2 > (double) this.mob.maxUpStep() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth())) {
				this.mob.getJumpControl().jump();
				this.operation = GtsMoveHelper.Operation.JUMPING;
			}
		} else if (this.operation == GtsMoveHelper.Operation.JUMPING) {
			this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
			if (this.mob.onGround()) {
				this.operation = GtsMoveHelper.Operation.WAIT;
			}
		} else {
			this.mob.setZza(0.0F);
		}
	}
}
