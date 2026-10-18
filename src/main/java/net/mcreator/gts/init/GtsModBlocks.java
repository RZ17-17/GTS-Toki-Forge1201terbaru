
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Block;

import net.mcreator.gts.block.ModifiedAnvilBlock;
import net.mcreator.gts.GtsMod;

public class GtsModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, GtsMod.MODID);
	public static final RegistryObject<Block> MODIFIED_ANVIL = REGISTRY.register("modified_anvil", ModifiedAnvilBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
