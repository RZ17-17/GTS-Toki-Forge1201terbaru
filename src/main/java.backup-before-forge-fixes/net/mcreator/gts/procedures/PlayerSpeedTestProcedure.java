package net.mcreator.gts.procedures;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.network.GtsModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerSpeedTestProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			Player player = (Player) entity;
			Vec3 pos = player.position();
			a++;
			if (a > INTERVAL) {
				a = 0;
			}
			if (a == 0) {
				lastPos = player.position();
			}
			double distance = lastPos.distanceTo(pos);
			double fixedDistance = distance * (20 / INTERVAL);
			if (a == INTERVAL) {
				String text = (new java.text.DecimalFormat("#.###").format(fixedDistance)) + " blocks/s";
				{
					GtsModVariables.PlayerVariables _vars = GtsModVariables.getPlayerVariables(entity);
					_vars.Player_Speed = fixedDistance;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}

	static int a = 0;
	static Vec3 lastPos = new Vec3(0, 0, 0);
	private static final int INTERVAL = 5;
	{
	}
}
