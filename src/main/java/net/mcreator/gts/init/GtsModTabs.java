
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.GtsMod;

public class GtsModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GtsMod.MODID);
	public static final RegistryObject<CreativeModeTab> GIANTESS_TOKI = REGISTRY.register("giantess_toki",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.gts.giantess_toki")).icon(() -> new ItemStack(GtsModItems.TOKI_DISK.get())).displayItems((parameters, tabData) -> {
				tabData.accept(GtsModItems.TOKI_SPAWN_EGG.get());
				tabData.accept(GtsModItems.TOKI_DRONE_SPAWN_EGG.get());
				tabData.accept(GtsModBlocks.MODIFIED_ANVIL.get().asItem());
				tabData.accept(GtsModItems.TOKI_CRYSTAL.get());
				tabData.accept(GtsModItems.TOKI_DISK.get());
				tabData.accept(GtsModItems.CANDY_SPAWN_EGG.get());
				tabData.accept(GtsModItems.ARA_SPAWN_EGG.get());
				tabData.accept(GtsModItems.FIREFLY_SPAWN_EGG.get());
			}).build());
}
