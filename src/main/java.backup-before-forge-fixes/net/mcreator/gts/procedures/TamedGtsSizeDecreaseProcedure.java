package net.mcreator.gts.procedures;


import net.mcreator.gts.init.GtsModAttributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

import java.util.UUID;

public class TamedGtsSizeDecreaseProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity TargetEntity = null;
		if (!world.isClientSide() && !(new Object() {
			Entity entityFromStringUUID(String uuid, Level world) {
				Entity _uuidentity = null;
				if (world instanceof ServerLevel _server) {
					try {
						_uuidentity = _server.getEntity(UUID.fromString(uuid));
					} catch (Exception e) {
					}
				}
				return _uuidentity;
			}
		}.entityFromStringUUID((entity.getPersistentData().getString("targetedTamedGiantess")), (Level) world) == null)) {
			if ((entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(Items.GOLDEN_CARROT)) : false) || new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity)) {
				TargetEntity = new Object() {
					Entity entityFromStringUUID(String uuid, Level world) {
						Entity _uuidentity = null;
						if (world instanceof ServerLevel _server) {
							try {
								_uuidentity = _server.getEntity(UUID.fromString(uuid));
							} catch (Exception e) {
							}
						}
						return _uuidentity;
					}
				}.entityFromStringUUID((entity.getPersistentData().getString("targetedTamedGiantess")), (Level) world);
				if ((TargetEntity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity8.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) > 1) {
					if (TargetEntity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()))
						_livingEntity10.getAttribute(GtsModAttributes.SCALE.get())
								.setBaseValue(((TargetEntity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity9.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) - 1));
					if (TargetEntity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
						_livingEntity12.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(
								(0.25 + 0.05 * (TargetEntity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity11.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					if (TargetEntity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Attributes.SAFE_FALL_DISTANCE))
						_livingEntity14.getAttribute(Attributes.SAFE_FALL_DISTANCE)
								.setBaseValue((5 * (TargetEntity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity13.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0)));
					if (TargetEntity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
						_livingEntity16.getAttribute(Attributes.MAX_HEALTH)
								.setBaseValue(((TargetEntity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
					if (TargetEntity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((TargetEntity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(GtsModAttributes.SCALE.get()) ? _livingEntity17.getAttribute(GtsModAttributes.SCALE.get()).getBaseValue() : 0) * 125));
					if (new Object() {
						public boolean checkGamemode(Entity _ent) {
							if (_ent instanceof ServerPlayer _serverPlayer) {
								return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
							} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
								return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
										&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SURVIVAL;
							}
							return false;
						}
					}.checkGamemode(entity)) {
						if (entity instanceof Player _player) {
							ItemStack _stktoremove = new ItemStack(Items.GOLDEN_CARROT);
							_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
						}
					}
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("You need a golden carrot in your inventory!"), false);
			}
		}
	}
}
