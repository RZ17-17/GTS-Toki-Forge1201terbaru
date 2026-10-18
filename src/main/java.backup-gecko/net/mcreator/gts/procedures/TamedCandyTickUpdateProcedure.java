package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;

public class TamedCandyTickUpdateProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity targetEntity = null;
		if (!world.isClientSide() && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
			targetEntity = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!GtsModVariables.MapVariables.get(world).DisableLockOnTarget && !(entity instanceof Mob _mobGetNoAi ? _mobGetNoAi.isNoAi() : false)) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((targetEntity.getX()), (targetEntity.getY()), (targetEntity.getZ())));
			}
			if (targetEntity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:giantessfaction")))) {
				if (entity instanceof Mob _entity)
					_entity.setTarget(null);
			}
			if (entity.getPersistentData().getDouble("tamedgtsticktimer") <= 0) {
				if (!(entity instanceof Mob _mobGetNoAi ? _mobGetNoAi.isNoAi() : false)) {
					TamedCandyRangeAndBreachProcedure.execute(world, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, entity);
				}
				entity.getPersistentData().putDouble("tamedgtsticktimer", 3);
			} else {
				entity.getPersistentData().putDouble("tamedgtsticktimer", (entity.getPersistentData().getDouble("tamedgtsticktimer") - 1));
			}
		}
	}
}
