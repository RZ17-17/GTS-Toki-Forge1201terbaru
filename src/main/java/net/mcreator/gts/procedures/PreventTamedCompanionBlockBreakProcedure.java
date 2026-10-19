package net.mcreator.gts.procedures;

import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.entity.TokiVariantEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Prevents tamed GTS companions from destroying terrain through Forge's
 * living-entity block-destruction hook. Combat damage is unaffected.
 */
@Mod.EventBusSubscriber
public final class PreventTamedCompanionBlockBreakProcedure {
    private PreventTamedCompanionBlockBreakProcedure() {
    }

    @SubscribeEvent
    public static void onLivingDestroyBlock(LivingDestroyBlockEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof TokiEntity
                || entity instanceof TamedTokiEntity
                || entity instanceof CandyEntity
                || entity instanceof TamedCandyEntity
                || entity instanceof TokiVariantEntity) {
            event.setCanceled(true);
        }
    }
}
