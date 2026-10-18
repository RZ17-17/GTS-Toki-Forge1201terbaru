
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.common.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.gts.client.gui.TokiConfigMenu1Screen;
import net.mcreator.gts.client.gui.TokiAnvilGUIScreen;
import net.mcreator.gts.client.gui.TamedGtsInteractionMenuScreen;
import net.mcreator.gts.client.gui.GtsCommonGuiPage2Screen;
import net.mcreator.gts.client.gui.GtsCommonGUIScreen;
import net.mcreator.gts.client.gui.CandyConfigGuiScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GtsModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(GtsModMenus.TOKI_CONFIG_MENU_1.get(), TokiConfigMenu1Screen::new);
			MenuScreens.register(GtsModMenus.TOKI_ANVIL_GUI.get(), TokiAnvilGUIScreen::new);
			MenuScreens.register(GtsModMenus.GTS_COMMON_GUI.get(), GtsCommonGUIScreen::new);
			MenuScreens.register(GtsModMenus.CANDY_CONFIG_GUI.get(), CandyConfigGuiScreen::new);
			MenuScreens.register(GtsModMenus.GTS_COMMON_GUI_PAGE_2.get(), GtsCommonGuiPage2Screen::new);
			MenuScreens.register(GtsModMenus.TAMED_GTS_INTERACTION_MENU.get(), TamedGtsInteractionMenuScreen::new);
		});
	}
}
