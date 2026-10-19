package net.mcreator.gts.procedures;

import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.entity.TamedTokiEntity;

public class TamedTokiSpawnProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double health, double maxhealth, double movementspeed, double pitch, double safefall, double scale, double yaw) {
        if (entity == null || !(world instanceof ServerLevel level))
            return;
        Entity spawned = GtsModEntities.TAMED_TOKI.get().spawn(level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        if (!(spawned instanceof TamedTokiEntity tamed))
            return;
        tamed.setYRot((float) yaw);
        tamed.setYBodyRot((float) yaw);
        tamed.setYHeadRot((float) yaw);
        tamed.setXRot((float) pitch);
        if (tamed.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()))
            tamed.getAttribute(GtsModAttributes.SCALE.get()).setBaseValue(scale);
        if (tamed.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
            tamed.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxhealth);
        if (tamed.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
            tamed.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementspeed);
        if (tamed instanceof LivingEntity living)
            living.setHealth((float) Math.min(health, living.getMaxHealth()));
        if (tamed instanceof TamableAnimal animal && entity instanceof Player owner)
            animal.tame(owner);
    }
}
