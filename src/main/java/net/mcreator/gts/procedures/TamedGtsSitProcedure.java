package net.mcreator.gts.procedures;

import java.util.UUID;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.entity.TokiVariantEntity;

public class TamedGtsSitProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (!(entity instanceof Player owner) || !(world instanceof ServerLevel server))
            return;
        Entity target;
        try {
            String uuid = owner.getPersistentData().getString("targetedTamedGiantess");
            if (uuid.isEmpty()) return;
            UUID targetId = UUID.fromString(uuid);
            target = server.getEntity(targetId);
            // The menu can stay open while a companion crosses a dimension or its
            // entity lookup cache changes. Resolve the saved companion UUID across
            // loaded server levels before treating the button press as a no-op.
            if (target == null) {
                for (ServerLevel level : server.getServer().getAllLevels()) {
                    target = level.getEntity(targetId);
                    if (target != null) break;
                }
            }
        } catch (Exception ignored) {
            return;
        }
        if (!(target instanceof TamableAnimal tame) || !tame.isTame() || !tame.isOwnedBy(owner))
            return;

        if (target instanceof TamedTokiEntity toki) {
            boolean sit = !toki.isOrderedToSit();
            toki.setOrderedToSit(sit);
            toki.getNavigation().stop();
            toki.setTarget(null);
            toki.getEntityData().set(TamedTokiEntity.DATA_Sleep, sit);
            toki.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision,
                sit ? (server.isDay() ? "Toki.sit" : "Toki.sleep") : "empty");
        } else if (target instanceof TamedCandyEntity candy) {
            boolean sit = !candy.isOrderedToSit();
            candy.setOrderedToSit(sit);
            candy.getNavigation().stop();
            candy.setTarget(null);
            candy.getEntityData().set(TamedCandyEntity.DATA_Sleep, sit);
            candy.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision,
                sit ? (server.isDay() ? "candy.sit" : "candy.sleep") : "empty");
        } else if (target instanceof TokiVariantEntity variant) {
            boolean sit = !variant.isOrderedToSit();
            variant.setOrderedToSit(sit);
            variant.getNavigation().stop();
            variant.setTarget(null);
            variant.setDeltaMovement(0.0D, variant.getDeltaMovement().y, 0.0D);
            variant.setSprinting(false);
        }
    }
}