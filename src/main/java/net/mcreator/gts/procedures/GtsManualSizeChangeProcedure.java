package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;

@EventBusSubscriber
public class GtsManualSizeChangeProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getTarget(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double RaidNumber = 0;
		if (!world.isClientSide() && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts:untamedgiantess"))) && (sourceentity instanceof Player _plr ? _plr.getAbilities().instabuild : false)) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
				if ((entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity5.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) <= 15) {
					if (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()))
						_livingEntity7.getAttribute(GtsModAttributes.SCALE.get())
								.setBaseValue(((entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity6.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) + 1));
					if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
						_livingEntity9.getAttribute(Attributes.MOVEMENT_SPEED)
								.setBaseValue((0.25 + 0.05 * (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity8.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					if (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
						_livingEntity13.getAttribute(Attributes.MAX_HEALTH)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity12.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity14.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
				}
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.NETHER_STAR) {
				if ((entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity18.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > 1) {
					if (entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()))
						_livingEntity20.getAttribute(GtsModAttributes.SCALE.get())
								.setBaseValue(((entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity19.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) - 1));
					if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
						_livingEntity22.getAttribute(Attributes.MOVEMENT_SPEED)
								.setBaseValue((0.25 + 0.05 * (entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity21.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					if (entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
						_livingEntity26.getAttribute(Attributes.MAX_HEALTH)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity25.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
				}
			}
		}
	}
}
