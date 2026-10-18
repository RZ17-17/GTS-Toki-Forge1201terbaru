
package net.mcreator.gts.network;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import java.util.function.Supplier;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.GtsCommonGuiPage2Menu;
import net.mcreator.gts.procedures.MainPage2PreviousPageProcedure;
import net.mcreator.gts.procedures.DisableTamedSeekOutHostileMobProcedure;
import net.mcreator.gts.procedures.DisableTamedFriendlyFireProcedure;
import net.mcreator.gts.procedures.DisableTamedBreachProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record GtsCommonGuiPage2ButtonMessage(int buttonID, int x, int y, int z) {

	public static void encode(GtsCommonGuiPage2ButtonMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.buttonID());
		buf.writeInt(msg.x());
		buf.writeInt(msg.y());
		buf.writeInt(msg.z());
	}
	public static GtsCommonGuiPage2ButtonMessage decode(FriendlyByteBuf buf) { return new GtsCommonGuiPage2ButtonMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()); }

	public static void handleData(final GtsCommonGuiPage2ButtonMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(() -> handleButtonAction(context.getSender(), message.buttonID(), message.x(), message.y(), message.z()));
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = GtsCommonGuiPage2Menu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			MainPage2PreviousPageProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			DisableTamedFriendlyFireProcedure.execute(world);
		}
		if (buttonID == 2) {

			DisableTamedSeekOutHostileMobProcedure.execute(world);
		}
		if (buttonID == 3) {

			DisableTamedBreachProcedure.execute(world);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> GtsMod.registerMessage(GtsCommonGuiPage2ButtonMessage.class, GtsCommonGuiPage2ButtonMessage::encode, GtsCommonGuiPage2ButtonMessage::decode, GtsCommonGuiPage2ButtonMessage::handleData));
	}
}
