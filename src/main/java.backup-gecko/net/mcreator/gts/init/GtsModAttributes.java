package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.mcreator.gts.GtsMod;

/** Provides the scale attribute used by the original 1.21.1 code on Forge 1.20.1. */
public final class GtsModAttributes {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, GtsMod.MODID);
    public static final RegistryObject<Attribute> SCALE = REGISTRY.register("scale", () ->
        new RangedAttribute("attribute.name.generic.scale", 1.0D, 0.0625D, 64.0D).setSyncable(true));

    private GtsModAttributes() {}

    @Mod.EventBusSubscriber(modid = GtsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class AttributeSetup {
        @SubscribeEvent
        public static void addScaleToLivingEntities(EntityAttributeModificationEvent event) {
            for (EntityType<? extends LivingEntity> type : event.getTypes()) {
                if (!event.has(type, SCALE.get())) event.add(type, SCALE.get());
            }
        }
    }
}
