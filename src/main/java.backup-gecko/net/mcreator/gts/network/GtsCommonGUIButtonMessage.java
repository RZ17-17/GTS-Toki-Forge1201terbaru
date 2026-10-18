
package net.mcreator.gts.network;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import java.util.function.Supplier;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.GtsCommonGUIMenu;
import net.mcreator.gts.procedures.MainPage1NextPageProcedure;
import net.mcreator.gts.procedures.MainMenuTokiConfigProcedure;
import net.mcreator.gts.procedures.MainMenuCandyConfigProcedure;
import net.mcreator.gts.procedures.GtsNaturalHeightIncreaseProcedure;
import net.mcreator.gts.procedures.GtsNaturalHeightDecreaseProcedure;
import net.mcreator.gts.procedures.GiantessTurnSpeedIncreaseProcedure;
import net.mcreator.gts.procedures.GiantessTurnSpeedDecreaseProcedure;
import net.mcreator.gts.procedures.DisableStomachSoundProcedure;
import net.mcreator.gts.procedures.DisableRaidWaveSizeIncreaseProcedure;
import net.mcreator.gts.procedures.DisableNaturalSpawnProcedure;
import net.mcreator.gts.procedures.DisableLockOntoTargetProcedure;
import net.mcreator.gts.procedures.DisableClothingDamageProcedure;
import net.mcreator.gts.procedures.DisableBreakLeavesWhenContactProcedure;
import net.mcreator.gts.procedures.DisableBelchProcedure;
import net.mcreator.gts.procedures.ConfigResetProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record GtsCommonGUIButtonMessage(int buttonID, int x, int y, int z) {

	public static void encode(GtsCommonGUIButtonMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.buttonID());
		buf.writeInt(msg.x());
		buf.writeInt(msg.y());
		buf.writeInt(msg.z());
	}
	public static GtsCommonGUIButtonMessage decode(FriendlyByteBuf buf) { return new GtsCommonGUIButtonMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()); }

	public static void handleData(final GtsCommonGUIButtonMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(() -> handleButtonAction(context.getSender(), message.buttonID(), message.x(), message.y(), message.z()));
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = GtsCommonGUIMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			GtsNaturalHeightIncreaseProcedure.execute(world);
		}
		if (buttonID == 1) {

			GtsNaturalHeightDecreaseProcedure.execute(world);
		}
		if (buttonID == 2) {

			GiantessTurnSpeedIncreaseProcedure.execute(world);
		}
		if (buttonID == 3) {

			GiantessTurnSpeedDecreaseProcedure.execute(world);
		}
		if (buttonID == 4) {

			MainMenuTokiConfigProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 5) {

			MainMenuCandyConfigProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 6) {

			ConfigResetProcedure.execute(world, entity);
		}
		if (buttonID == 7) {

			MainPage1NextPageProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 8) {

			DisableStomachSoundProcedure.execute(world);
		}
		if (buttonID == 9) {

			DisableClothingDamageProcedure.execute(world);
		}
		if (buttonID == 10) {

			DisableNaturalSpawnProcedure.execute(world);
		}
		if (buttonID == 11) {

			DisableRaidWaveSizeIncreaseProcedure.execute(world);
		}
		if (buttonID == 12) {

			DisableBelchProcedure.execute(world);
		}
		if (buttonID == 13) {

			DisableBreakLeavesWhenContactProcedure.execute(world);
		}
		if (buttonID == 14) {

			DisableLockOntoTargetProcedure.execute(world);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> GtsMod.registerMessage(GtsCommonGUIButtonMessage.class, GtsCommonGUIButtonMessage::encode, GtsCommonGUIButtonMessage::decode, GtsCommonGUIButtonMessage::handleData));
	}
}
