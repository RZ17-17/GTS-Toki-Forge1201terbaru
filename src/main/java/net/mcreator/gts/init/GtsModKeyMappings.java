
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.gts.network.RegularAttackMessage;
import net.mcreator.gts.network.PlayerStruggleKeyMessage;
import net.mcreator.gts.network.BreachAttackMessage;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class GtsModKeyMappings {
	public static final KeyMapping PLAYER_STRUGGLE_KEY = new KeyMapping("key.gts.player_struggle_key", GLFW.GLFW_KEY_SPACE, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				PLAYER_STRUGGLE_KEY_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - PLAYER_STRUGGLE_KEY_LASTPRESS);
				GtsMod.sendToServer(new PlayerStruggleKeyMessage(1, dt));
				PlayerStruggleKeyMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping REGULAR_ATTACK = new KeyMapping("key.gts.regular_attack", GLFW.GLFW_KEY_1, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				GtsMod.sendToServer(new RegularAttackMessage(0, 0));
				RegularAttackMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping BREACH_ATTACK = new KeyMapping("key.gts.breach_attack", GLFW.GLFW_KEY_2, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				GtsMod.sendToServer(new BreachAttackMessage(0, 0));
				BreachAttackMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	private static long PLAYER_STRUGGLE_KEY_LASTPRESS = 0;

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(PLAYER_STRUGGLE_KEY);
		event.register(REGULAR_ATTACK);
		event.register(BREACH_ATTACK);
	}

	@EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
			if (Minecraft.getInstance().screen == null) {
				PLAYER_STRUGGLE_KEY.consumeClick();
				REGULAR_ATTACK.consumeClick();
				BREACH_ATTACK.consumeClick();
			}
		}
	}
}
