package net.mcreator.gts.network;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mcreator.gts.world.inventory.TamedGtsInteractionMenuMenu;
import net.mcreator.gts.procedures.TamedGtsSizeIncreaseProcedure;
import net.mcreator.gts.procedures.TamedGtsSizeDecreaseProcedure;
import net.mcreator.gts.procedures.TamedGtsSitProcedure;
import net.mcreator.gts.GtsMod;
import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record TamedGtsInteractionMenuButtonMessage(int buttonID, int x, int y, int z) {
    public static void encode(TamedGtsInteractionMenuButtonMessage msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.buttonID());
        buf.writeInt(msg.x());
        buf.writeInt(msg.y());
        buf.writeInt(msg.z());
    }

    public static TamedGtsInteractionMenuButtonMessage decode(FriendlyByteBuf buf) {
        return new TamedGtsInteractionMenuButtonMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handleData(final TamedGtsInteractionMenuButtonMessage message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> handleButtonAction(context.getSender(), message.buttonID(), message.x(), message.y(), message.z()));
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player player, int buttonID, int x, int y, int z) {
        if (player == null || player.level().isClientSide())
            return;
        if (!(player.containerMenu instanceof TamedGtsInteractionMenuMenu))
            return;
        switch (buttonID) {
            case 0 -> TamedGtsSitProcedure.execute(player.level(), player);
            case 1 -> TamedGtsSizeIncreaseProcedure.execute(player.level(), player);
            case 2 -> TamedGtsSizeDecreaseProcedure.execute(player.level(), player);
            default -> { }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> GtsMod.registerMessage(TamedGtsInteractionMenuButtonMessage.class,
            TamedGtsInteractionMenuButtonMessage::encode, TamedGtsInteractionMenuButtonMessage::decode,
            TamedGtsInteractionMenuButtonMessage::handleData));
    }
}