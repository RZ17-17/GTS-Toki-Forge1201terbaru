
package net.mcreator.gts.network;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import java.util.function.Supplier;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import net.mcreator.gts.procedures.TamedGtsPlayerControlRegularAttackProcedure;
import net.mcreator.gts.GtsMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record RegularAttackMessage(int eventType, int pressedms) {
	public static void encode(RegularAttackMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.eventType());
		buf.writeInt(msg.pressedms());
	}
	public static RegularAttackMessage decode(FriendlyByteBuf buf) { return new RegularAttackMessage(buf.readInt(), buf.readInt()); }

	public static void handleData(final RegularAttackMessage message, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(() -> pressAction(context.getSender(), message.eventType(), message.pressedms()));
		context.setPacketHandled(true);
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {

			TamedGtsPlayerControlRegularAttackProcedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> GtsMod.registerMessage(RegularAttackMessage.class, RegularAttackMessage::encode, RegularAttackMessage::decode, RegularAttackMessage::handleData));
	}
}
