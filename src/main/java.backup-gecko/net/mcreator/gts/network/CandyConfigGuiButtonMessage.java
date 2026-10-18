
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

import net.mcreator.gts.world.inventory.CandyConfigGuiMenu;
import net.mcreator.gts.procedures.DisableCandyVoreProcedure;
import net.mcreator.gts.procedures.DisableCandyUpperSwipeProcedure;
import net.mcreator.gts.procedures.DisableCandyUpperSmashProcedure;
import net.mcreator.gts.procedures.DisableCandyThunderMagicProcedure;
import net.mcreator.gts.procedures.DisableCandyStompProcedure;
import net.mcreator.gts.procedures.DisableCandySpitProcedure;
import net.mcreator.gts.procedures.DisableCandyKickProcedure;
import net.mcreator.gts.procedures.DisableCandyHeartMagicProcedure;
import net.mcreator.gts.procedures.DisableCandyGroundSlamProcedure;
import net.mcreator.gts.procedures.DisableCandyBreachProcedure;
import net.mcreator.gts.procedures.CandyConfigMainMenuProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record CandyConfigGuiButtonMessage(int buttonID, int x, int y, int z) {

	public static void encode(CandyConfigGuiButtonMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.buttonID());
		buf.writeInt(msg.x());
		buf.writeInt(msg.y());
		buf.writeInt(msg.z());
	}
	public static CandyConfigGuiButtonMessage decode(FriendlyByteBuf buf) { return new CandyConfigGuiButtonMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()); }

	public static void handleData(final CandyConfigGuiButtonMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(() -> handleButtonAction(context.getSender(), message.buttonID(), message.x(), message.y(), message.z()));
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = CandyConfigGuiMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			CandyConfigMainMenuProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			DisableCandyStompProcedure.execute(world);
		}
		if (buttonID == 2) {

			DisableCandyGroundSlamProcedure.execute(world);
		}
		if (buttonID == 3) {

			DisableCandyKickProcedure.execute(world);
		}
		if (buttonID == 4) {

			DisableCandyUpperSwipeProcedure.execute(world);
		}
		if (buttonID == 5) {

			DisableCandyUpperSmashProcedure.execute(world);
		}
		if (buttonID == 6) {

			DisableCandyBreachProcedure.execute(world);
		}
		if (buttonID == 7) {

			DisableCandyVoreProcedure.execute(world);
		}
		if (buttonID == 8) {

			DisableCandySpitProcedure.execute(world);
		}
		if (buttonID == 9) {

			DisableCandyHeartMagicProcedure.execute(world);
		}
		if (buttonID == 10) {

			DisableCandyThunderMagicProcedure.execute(world);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> GtsMod.registerMessage(CandyConfigGuiButtonMessage.class, CandyConfigGuiButtonMessage::encode, CandyConfigGuiButtonMessage::decode, CandyConfigGuiButtonMessage::handleData));
	}
}
