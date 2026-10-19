package net.mcreator.gts.procedures;

import java.util.UUID;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import net.mcreator.gts.init.GtsModAttributes;

public class TamedGtsSizeIncreaseProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (!(entity instanceof Player player) || !(world instanceof ServerLevel server))
            return;
        Entity target;
        try {
            String uuid = player.getPersistentData().getString("targetedTamedGiantess");
            if (uuid.isEmpty()) return;
            target = server.getEntity(UUID.fromString(uuid));
        } catch (Exception ignored) {
            return;
        }
        if (!(target instanceof TamableAnimal tame) || !tame.isTame() || !tame.isOwnedBy(player) || !(target instanceof LivingEntity living))
            return;
        AttributeInstance scaleAttribute = living.getAttribute(GtsModAttributes.SCALE.get());
        if (scaleAttribute == null) return;
        double oldScale = scaleAttribute.getBaseValue();
        if (oldScale >= 15.0D) { player.displayClientMessage(Component.literal("Ukuran sudah maksimum (15)."), true); return; }
        Item required = Items.GOLDEN_APPLE;
        boolean creative = player.getAbilities().instabuild;
        ItemStack payment = creative ? ItemStack.EMPTY : findItem(player, required);
        if (!creative && payment.isEmpty()) {
            player.displayClientMessage(Component.literal("Item tidak ditemukan di inventori/hotbar: " + required.getDescription().getString()), true);
            return;
        }
        double newScale = oldScale + 1.0D;
        scaleAttribute.setBaseValue(newScale);
        AttributeInstance speed = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) speed.setBaseValue(0.25D + 0.05D * newScale);
        AttributeInstance maxHealth = living.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(newScale * 125.0D);
            living.setHealth((float) maxHealth.getValue());
        }
        if (!creative) payment.shrink(1);
        player.displayClientMessage(Component.literal("Ukuran berubah menjadi " + (int)newScale + "."), true);
    }

    // Inventory.items mencakup hotbar dan inventori utama.
    private static ItemStack findItem(Player player, Item item) {
        for (ItemStack stack : player.getInventory().items)
            if (!stack.isEmpty() && stack.is(item)) return stack;
        ItemStack offhand = player.getOffhandItem();
        return !offhand.isEmpty() && offhand.is(item) ? offhand : ItemStack.EMPTY;
    }
}