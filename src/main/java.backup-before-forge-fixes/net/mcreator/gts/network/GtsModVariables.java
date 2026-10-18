package net.mcreator.gts.network;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.gts.GtsMod;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GtsModVariables {
	public static final Capability<PlayerVariables> PLAYER_VARIABLES = CapabilityManager.get(new CapabilityToken<>() {});
	private static final ResourceLocation PLAYER_VARIABLES_ID = new ResourceLocation(GtsMod.MODID, "player_variables");

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) { event.register(PlayerVariables.class); }

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			GtsMod.registerMessage(SavedDataSyncMessage.class, SavedDataSyncMessage::encode, SavedDataSyncMessage::decode, SavedDataSyncMessage::handleData);
			GtsMod.registerMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::encode, PlayerVariablesSyncMessage::decode, PlayerVariablesSyncMessage::handleData);
		});
	}

	public static PlayerVariables getPlayerVariables(Entity entity) { return entity.getCapability(PLAYER_VARIABLES).orElseGet(PlayerVariables::new); }

	@EventBusSubscriber
	public static class CapabilityHandlers {
		@SubscribeEvent
		public static void attach( net.minecraftforge.event.AttachCapabilitiesEvent<Entity> event) {
			if (!(event.getObject() instanceof net.minecraft.world.entity.player.Player)) return;
			PlayerVariablesProvider provider = new PlayerVariablesProvider();
			event.addCapability(PLAYER_VARIABLES_ID, provider);
			event.addListener(provider::invalidate);
		}
	}

	private static final class PlayerVariablesProvider implements ICapabilitySerializable<CompoundTag> {
		private final PlayerVariables variables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> optional = LazyOptional.of(() -> variables);
		@Override public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) { return PLAYER_VARIABLES.orEmpty(capability, optional); }
		@Override public CompoundTag serializeNBT() { return variables.serializeNBT(); }
		@Override public void deserializeNBT(CompoundTag tag) { variables.deserializeNBT(tag); }
		private void invalidate() { optional.invalidate(); }
	}

	@EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				getPlayerVariables(player).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				getPlayerVariables(player).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				getPlayerVariables(player).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			PlayerVariables original = getPlayerVariables(event.getOriginal());
			PlayerVariables clone = new PlayerVariables();
			if (!event.isWasDeath()) {
				clone.Player_Speed = original.Player_Speed;
			}
			event.getEntity().getCapability(PLAYER_VARIABLES).ifPresent(vars -> vars.Player_Speed = clone.Player_Speed);
		}

		@SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData mapdata = MapVariables.get(event.getEntity().level());
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (mapdata != null)
					GtsMod.sendToPlayer(player, new SavedDataSyncMessage(0, mapdata));
				if (worlddata != null)
					GtsMod.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}

		@SubscribeEvent
		public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (worlddata != null)
					GtsMod.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final String DATA_NAME = "gts_worldvars";

		public static WorldVariables load(CompoundTag tag) {
			WorldVariables data = new WorldVariables();
			data.read(tag);
			return data;
		}

		public void read(CompoundTag nbt) {
		}

		@Override
		public CompoundTag save(CompoundTag nbt) {
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof ServerLevel level)
				GtsMod.sendToPlayersInDimension(level, new SavedDataSyncMessage(1, this));
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(WorldVariables::load, WorldVariables::new, DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final String DATA_NAME = "gts_mapvars";
		public boolean DisableStep = false;
		public boolean DisableKick = false;
		public boolean DisableSit = false;
		public boolean DisableGunSlap = false;
		public boolean DisableGunSmash = false;
		public boolean DisableThrow = false;
		public boolean DisableVore = false;
		public boolean DisableSniper = false;
		public boolean DisableSpit = false;
		public boolean DisableDrone = false;
		public boolean DisableUpperBreach = false;
		public boolean DisableMiddleBreach = false;
		public boolean DisableLowerBreach = false;
		public boolean DisableTrappedBreach = false;
		public boolean DisableShowOff = false;
		public boolean DisableBelch = false;
		public boolean DisableStomachSound = false;
		public boolean DisableClothingDamage = false;
		public boolean DisableRaidWaveSizeChange = false;
		public double Toki_TurnSpeed = 30.0;
		public boolean DisableLeavesBreakWalk = false;
		public boolean DisableLockOnTarget = true;
		public boolean DisableSubmission = false;
		public double GtsNaturalSpawnHeight = 3.0;
		public boolean DisableNaturalSpawn = false;
		public boolean DisableCandyStep = false;
		public boolean DisableCandyShockWave = false;
		public boolean DisableCandyKick = false;
		public boolean DisableCandySwipe = false;
		public boolean DisableCandySmash = false;
		public boolean DisableCandyBreach = false;
		public boolean DisableCandyVore = false;
		public boolean DisableCandyHeartMagic = false;
		public boolean DisableCandySpit = false;
		public boolean DisableCandyThunderMagic = false;
		public boolean Version211 = true;
		public boolean DisableTamedGiantessFriendlyFire = false;
		public boolean DisableTamedGiantessBreach = false;
		public boolean DisableTamedGiantessMobAggro = false;

		public static MapVariables load(CompoundTag tag) {
			MapVariables data = new MapVariables();
			data.read(tag);
			return data;
		}

		public void read(CompoundTag nbt) {
			DisableStep = nbt.getBoolean("DisableStep");
			DisableKick = nbt.getBoolean("DisableKick");
			DisableSit = nbt.getBoolean("DisableSit");
			DisableGunSlap = nbt.getBoolean("DisableGunSlap");
			DisableGunSmash = nbt.getBoolean("DisableGunSmash");
			DisableThrow = nbt.getBoolean("DisableThrow");
			DisableVore = nbt.getBoolean("DisableVore");
			DisableSniper = nbt.getBoolean("DisableSniper");
			DisableSpit = nbt.getBoolean("DisableSpit");
			DisableDrone = nbt.getBoolean("DisableDrone");
			DisableUpperBreach = nbt.getBoolean("DisableUpperBreach");
			DisableMiddleBreach = nbt.getBoolean("DisableMiddleBreach");
			DisableLowerBreach = nbt.getBoolean("DisableLowerBreach");
			DisableTrappedBreach = nbt.getBoolean("DisableTrappedBreach");
			DisableShowOff = nbt.getBoolean("DisableShowOff");
			DisableBelch = nbt.getBoolean("DisableBelch");
			DisableStomachSound = nbt.getBoolean("DisableStomachSound");
			DisableClothingDamage = nbt.getBoolean("DisableClothingDamage");
			DisableRaidWaveSizeChange = nbt.getBoolean("DisableRaidWaveSizeChange");
			Toki_TurnSpeed = nbt.getDouble("Toki_TurnSpeed");
			DisableLeavesBreakWalk = nbt.getBoolean("DisableLeavesBreakWalk");
			DisableLockOnTarget = nbt.getBoolean("DisableLockOnTarget");
			DisableSubmission = nbt.getBoolean("DisableSubmission");
			GtsNaturalSpawnHeight = nbt.getDouble("GtsNaturalSpawnHeight");
			DisableNaturalSpawn = nbt.getBoolean("DisableNaturalSpawn");
			DisableCandyStep = nbt.getBoolean("DisableCandyStep");
			DisableCandyShockWave = nbt.getBoolean("DisableCandyShockWave");
			DisableCandyKick = nbt.getBoolean("DisableCandyKick");
			DisableCandySwipe = nbt.getBoolean("DisableCandySwipe");
			DisableCandySmash = nbt.getBoolean("DisableCandySmash");
			DisableCandyBreach = nbt.getBoolean("DisableCandyBreach");
			DisableCandyVore = nbt.getBoolean("DisableCandyVore");
			DisableCandyHeartMagic = nbt.getBoolean("DisableCandyHeartMagic");
			DisableCandySpit = nbt.getBoolean("DisableCandySpit");
			DisableCandyThunderMagic = nbt.getBoolean("DisableCandyThunderMagic");
			Version211 = nbt.getBoolean("Version211");
			DisableTamedGiantessFriendlyFire = nbt.getBoolean("DisableTamedGiantessFriendlyFire");
			DisableTamedGiantessBreach = nbt.getBoolean("DisableTamedGiantessBreach");
			DisableTamedGiantessMobAggro = nbt.getBoolean("DisableTamedGiantessMobAggro");
		}

		@Override
		public CompoundTag save(CompoundTag nbt) {
			nbt.putBoolean("DisableStep", DisableStep);
			nbt.putBoolean("DisableKick", DisableKick);
			nbt.putBoolean("DisableSit", DisableSit);
			nbt.putBoolean("DisableGunSlap", DisableGunSlap);
			nbt.putBoolean("DisableGunSmash", DisableGunSmash);
			nbt.putBoolean("DisableThrow", DisableThrow);
			nbt.putBoolean("DisableVore", DisableVore);
			nbt.putBoolean("DisableSniper", DisableSniper);
			nbt.putBoolean("DisableSpit", DisableSpit);
			nbt.putBoolean("DisableDrone", DisableDrone);
			nbt.putBoolean("DisableUpperBreach", DisableUpperBreach);
			nbt.putBoolean("DisableMiddleBreach", DisableMiddleBreach);
			nbt.putBoolean("DisableLowerBreach", DisableLowerBreach);
			nbt.putBoolean("DisableTrappedBreach", DisableTrappedBreach);
			nbt.putBoolean("DisableShowOff", DisableShowOff);
			nbt.putBoolean("DisableBelch", DisableBelch);
			nbt.putBoolean("DisableStomachSound", DisableStomachSound);
			nbt.putBoolean("DisableClothingDamage", DisableClothingDamage);
			nbt.putBoolean("DisableRaidWaveSizeChange", DisableRaidWaveSizeChange);
			nbt.putDouble("Toki_TurnSpeed", Toki_TurnSpeed);
			nbt.putBoolean("DisableLeavesBreakWalk", DisableLeavesBreakWalk);
			nbt.putBoolean("DisableLockOnTarget", DisableLockOnTarget);
			nbt.putBoolean("DisableSubmission", DisableSubmission);
			nbt.putDouble("GtsNaturalSpawnHeight", GtsNaturalSpawnHeight);
			nbt.putBoolean("DisableNaturalSpawn", DisableNaturalSpawn);
			nbt.putBoolean("DisableCandyStep", DisableCandyStep);
			nbt.putBoolean("DisableCandyShockWave", DisableCandyShockWave);
			nbt.putBoolean("DisableCandyKick", DisableCandyKick);
			nbt.putBoolean("DisableCandySwipe", DisableCandySwipe);
			nbt.putBoolean("DisableCandySmash", DisableCandySmash);
			nbt.putBoolean("DisableCandyBreach", DisableCandyBreach);
			nbt.putBoolean("DisableCandyVore", DisableCandyVore);
			nbt.putBoolean("DisableCandyHeartMagic", DisableCandyHeartMagic);
			nbt.putBoolean("DisableCandySpit", DisableCandySpit);
			nbt.putBoolean("DisableCandyThunderMagic", DisableCandyThunderMagic);
			nbt.putBoolean("Version211", Version211);
			nbt.putBoolean("DisableTamedGiantessFriendlyFire", DisableTamedGiantessFriendlyFire);
			nbt.putBoolean("DisableTamedGiantessBreach", DisableTamedGiantessBreach);
			nbt.putBoolean("DisableTamedGiantessMobAggro", DisableTamedGiantessMobAggro);
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level && !world.isClientSide())
				GtsMod.sendToAllPlayers(new SavedDataSyncMessage(0, this));
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAcc) {
				return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(MapVariables::load, MapVariables::new, DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public record SavedDataSyncMessage(int dataType, SavedData data) {
		public static void encode(SavedDataSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeInt(message.dataType());
			buffer.writeNbt(message.data() == null ? new CompoundTag() : message.data().save(new CompoundTag()));
		}
		public static SavedDataSyncMessage decode(FriendlyByteBuf buffer) {
			int dataType=buffer.readInt(); CompoundTag nbt=buffer.readNbt(); SavedData data=null;
			if(nbt!=null) { data=dataType==0?new MapVariables():new WorldVariables(); if(data instanceof MapVariables m)m.read(nbt); else if(data instanceof WorldVariables w)w.read(nbt); }
			return new SavedDataSyncMessage(dataType,data);
		}
		public static void handleData(SavedDataSyncMessage message, Supplier<NetworkEvent.Context> ctx) {
			NetworkEvent.Context context=ctx.get(); context.enqueueWork(() -> { if(message.data()==null)return; if(message.dataType()==0)MapVariables.clientSide.read(message.data().save(new CompoundTag())); else WorldVariables.clientSide.read(message.data().save(new CompoundTag())); }); context.setPacketHandled(true);
		}
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		public double Player_Speed = 0;
		@Override public CompoundTag serializeNBT() { CompoundTag nbt=new CompoundTag(); nbt.putDouble("Player_Speed",Player_Speed); return nbt; }
		@Override public void deserializeNBT(CompoundTag nbt) { Player_Speed=nbt.getDouble("Player_Speed"); }
		public void syncPlayerVariables(Entity entity) { if(entity instanceof ServerPlayer player) GtsMod.sendToPlayer(player,new PlayerVariablesSyncMessage(this)); }
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) {
		public static void encode(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) { buffer.writeNbt(message.data().serializeNBT()); }
		public static PlayerVariablesSyncMessage decode(FriendlyByteBuf buffer) { PlayerVariables v=new PlayerVariables(); CompoundTag tag=buffer.readNbt(); if(tag!=null)v.deserializeNBT(tag); return new PlayerVariablesSyncMessage(v); }
		public static void handleData(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> ctx) {
			NetworkEvent.Context context=ctx.get(); context.enqueueWork(() -> {
				if(context.getDirection().getReceptionSide().isClient()) { net.minecraft.client.Minecraft mc=net.minecraft.client.Minecraft.getInstance(); if(mc.player!=null)getPlayerVariables(mc.player).deserializeNBT(message.data().serializeNBT()); }
			}); context.setPacketHandled(true);
		}
	}
}
