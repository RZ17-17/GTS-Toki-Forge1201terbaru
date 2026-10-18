
package net.mcreator.gts.item;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.mcreator.gts.init.GtsModSounds;

import net.mcreator.gts.GtsMod;

public class TokiDiskItem extends RecordItem {
	public TokiDiskItem() {
		super(0, () -> GtsModSounds.TOKI_DISKS.get(), new net.minecraft.world.item.Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 100);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}
}
