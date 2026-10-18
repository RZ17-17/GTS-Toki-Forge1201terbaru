
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

import net.mcreator.gts.world.inventory.TokiAnvilGUIMenu;
import net.mcreator.gts.procedures.TokiAnvilOutputSlotProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record TokiAnvilGUISlotMessage(int slotID, int x, int y, int z, int changeType, int meta) {

	public static void encode(TokiAnvilGUISlotMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.slotID());
		buf.writeInt(msg.x());
		buf.writeInt(msg.y());
		buf.writeInt(msg.z());
		buf.writeInt(msg.changeType());
		buf.writeInt(msg.meta());
	}
	public static TokiAnvilGUISlotMessage decode(FriendlyByteBuf buf) { return new TokiAnvilGUISlotMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()); }

	public static void handleData(final TokiAnvilGUISlotMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(() -> handleSlotAction(context.getSender(), message.slotID(), message.changeType(), message.meta(), message.x(), message.y(), message.z()));
		context.setPacketHandled(true);
	}

	public static void handleSlotAction(Player entity, int slot, int changeType, int meta, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = TokiAnvilGUIMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (slot == 1 && changeType == 1) {

			TokiAnvilOutputSlotProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> GtsMod.registerMessage(TokiAnvilGUISlotMessage.class, TokiAnvilGUISlotMessage::encode, TokiAnvilGUISlotMessage::decode, TokiAnvilGUISlotMessage::handleData));
	}
}
