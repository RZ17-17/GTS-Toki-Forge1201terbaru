package net.mcreator.gts.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;

/**
 * Shared target filter for tamed GTS companions.
 *
 * This prevents selecting or attacking other tamed companions and Touhou Little Maid
 * entities. It is an AI/attack guard, not merely a damage-cancellation rule.
 */
public final class FriendlyFireGuard {
    private FriendlyFireGuard() {}

    public static boolean isFriendlyTarget(Entity attacker, Entity target) {
        if (attacker == null || target == null || attacker == target) return true;
        if (!(attacker instanceof TamableAnimal tameAttacker) || !tameAttacker.isTame()) return false;

        if (tameAttacker.isAlliedTo(target)) return true;

        if (target instanceof TamableAnimal tameTarget && tameTarget.isTame()) {
            if (tameAttacker.getOwnerUUID() != null
                    && tameAttacker.getOwnerUUID().equals(tameTarget.getOwnerUUID())) {
                return true;
            }
            if (target instanceof TamedTokiEntity
                    || target instanceof TamedCandyEntity
                    || (target instanceof TokiVariantEntity variant && variant.isTame())) {
                return true;
            }
        }

        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        return id != null && id.getNamespace().equals("touhou_little_maid");
    }
}
