package net.mcreator.gts;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.world.entity.player.Player;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraft.util.Tuple;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModTabs;
import net.mcreator.gts.init.GtsModSounds;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.init.GtsModMenus;
import net.mcreator.gts.init.GtsModItems;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.init.GtsModBlocks;
import net.mcreator.gts.init.GtsModAttributes;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

@Mod("gts")
public class GtsMod {
	public static final Logger LOGGER = LogManager.getLogger(GtsMod.class);
	public static final String MODID = "gts";

	public GtsMod() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Start of user code block mod constructor
		// End of user code block mod constructor
		MinecraftForge.EVENT_BUS.register(this);
		GtsModSounds.REGISTRY.register(modEventBus);
		GtsModBlocks.REGISTRY.register(modEventBus);
		GtsModAttributes.REGISTRY.register(modEventBus);

		GtsModItems.REGISTRY.register(modEventBus);
		GtsModEntities.REGISTRY.register(modEventBus);
		GtsModTabs.REGISTRY.register(modEventBus);

		GtsModMobEffects.REGISTRY.register(modEventBus);
		GtsModMenus.REGISTRY.register(modEventBus);

		// Start of user code block mod init
		// End of user code block mod init
	}

	// Prevent a tamed Toki from being killed as a side effect when its owner dies.
	@SubscribeEvent
	public void onTamedTokiOwnerDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof TamedTokiEntity toki && toki.isTame()
				&& toki.getOwner() instanceof Player owner && !owner.isAlive()) {
			event.setCanceled(true);
			toki.setHealth(Math.max(1.0F, toki.getMaxHealth() * 0.25F));
			toki.setLastHurtByMob(null);
		}
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int packetId = 0;
	public static <T> void registerMessage(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> handler) {
		NETWORK.messageBuilder(type, packetId++).encoder(encoder).decoder(decoder).consumerMainThread(handler).add();
	}

	public static <MSG> void sendToServer(MSG message) { NETWORK.sendToServer(message); }
	public static <MSG> void sendToPlayer(ServerPlayer player, MSG message) { NETWORK.send(PacketDistributor.PLAYER.with(() -> player), message); }
	public static <MSG> void sendToAllPlayers(MSG message) { NETWORK.send(PacketDistributor.ALL.noArg(), message); }
	public static <MSG> void sendToPlayersInDimension(ServerLevel level, MSG message) { NETWORK.send(PacketDistributor.DIMENSION.with(level::dimension), message); }

	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new Tuple<>(action, tick));
	}

	/** Forge 1.20.1 replacement for the original NeoForge biome modifier JSON. */

	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setB(work.getB() - 1);
			if (work.getB() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getA().run());
		workQueue.removeAll(actions);
	}
}
