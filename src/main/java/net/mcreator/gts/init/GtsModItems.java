
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.gts.item.TokiDiskItem;
import net.mcreator.gts.item.TokiCrystalItem;
import net.mcreator.gts.GtsMod;

public class GtsModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, GtsMod.MODID);
	public static final RegistryObject<Item> TOKI_SPAWN_EGG = REGISTRY.register("toki_spawn_egg", () -> new ForgeSpawnEggItem(GtsModEntities.TOKI, -16724737, -1, new Item.Properties()));
	public static final RegistryObject<Item> TOKI_DRONE_SPAWN_EGG = REGISTRY.register("toki_drone_spawn_egg", () -> new ForgeSpawnEggItem(GtsModEntities.TOKI_DRONE, -13382401, -6710887, new Item.Properties()));
	public static final RegistryObject<Item> MODIFIED_ANVIL = block(GtsModBlocks.MODIFIED_ANVIL);
	public static final RegistryObject<Item> TOKI_CRYSTAL = REGISTRY.register("toki_crystal", TokiCrystalItem::new);
	public static final RegistryObject<Item> TOKI_DISK = REGISTRY.register("toki_disk", TokiDiskItem::new);
	public static final RegistryObject<Item> CANDY_SPAWN_EGG = REGISTRY.register("candy_spawn_egg", () -> new ForgeSpawnEggItem(GtsModEntities.CANDY, -39271, -1, new Item.Properties()));

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
