
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.world.inventory.TokiConfigMenu1Menu;
import net.mcreator.gts.world.inventory.TokiAnvilGUIMenu;
import net.mcreator.gts.world.inventory.TamedGtsInteractionMenuMenu;
import net.mcreator.gts.world.inventory.GtsCommonGuiPage2Menu;
import net.mcreator.gts.world.inventory.GtsCommonGUIMenu;
import net.mcreator.gts.world.inventory.CandyConfigGuiMenu;
import net.mcreator.gts.GtsMod;

public class GtsModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, GtsMod.MODID);
	public static final RegistryObject<MenuType<TokiConfigMenu1Menu>> TOKI_CONFIG_MENU_1 = REGISTRY.register("toki_config_menu_1", () -> IMenuTypeExtension.create(TokiConfigMenu1Menu::new));
	public static final RegistryObject<MenuType<TokiAnvilGUIMenu>> TOKI_ANVIL_GUI = REGISTRY.register("toki_anvil_gui", () -> IMenuTypeExtension.create(TokiAnvilGUIMenu::new));
	public static final RegistryObject<MenuType<GtsCommonGUIMenu>> GTS_COMMON_GUI = REGISTRY.register("gts_common_gui", () -> IMenuTypeExtension.create(GtsCommonGUIMenu::new));
	public static final RegistryObject<MenuType<CandyConfigGuiMenu>> CANDY_CONFIG_GUI = REGISTRY.register("candy_config_gui", () -> IMenuTypeExtension.create(CandyConfigGuiMenu::new));
	public static final RegistryObject<MenuType<GtsCommonGuiPage2Menu>> GTS_COMMON_GUI_PAGE_2 = REGISTRY.register("gts_common_gui_page_2", () -> IMenuTypeExtension.create(GtsCommonGuiPage2Menu::new));
	public static final RegistryObject<MenuType<TamedGtsInteractionMenuMenu>> TAMED_GTS_INTERACTION_MENU = REGISTRY.register("tamed_gts_interaction_menu", () -> IMenuTypeExtension.create(TamedGtsInteractionMenuMenu::new));
}
