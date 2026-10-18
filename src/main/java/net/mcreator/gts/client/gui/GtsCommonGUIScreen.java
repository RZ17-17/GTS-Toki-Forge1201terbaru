package net.mcreator.gts.client.gui;


import net.mcreator.gts.GtsMod;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.gts.world.inventory.GtsCommonGUIMenu;
import net.mcreator.gts.procedures.GtsNaturalHeightDisplayProcedure;
import net.mcreator.gts.procedures.GiantessTurnSpeedDisplayProcedure;
import net.mcreator.gts.procedures.CheckDisableStomachSoundProcedure;
import net.mcreator.gts.procedures.CheckDisableRaidWaveSizeChangeProcedure;
import net.mcreator.gts.procedures.CheckDisableNaturalSpawnProcedure;
import net.mcreator.gts.procedures.CheckDisableLockOnTargetProcedure;
import net.mcreator.gts.procedures.CheckDisableClothingDamageProcedure;
import net.mcreator.gts.procedures.CheckDisableBreakLeavesWhenWalkProcedure;
import net.mcreator.gts.procedures.CheckDisableBelchProcedure;
import net.mcreator.gts.network.GtsCommonGUIButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class GtsCommonGUIScreen extends AbstractContainerScreen<GtsCommonGUIMenu> {
	private final static HashMap<String, Object> guistate = GtsCommonGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_empty;
	Button button_empty1;
	Button button_empty2;
	Button button_empty3;
	Button button_toki_config;
	Button button_candy_config;
	Button button_reset_to_default;
	Button button_next_page;
	Button button_stomach_sound;
	Button button_clothing_damage;
	Button button_natural_spawn;
	Button button_raid_wave_size_increase;
	Button button_belch;
	Button button_break_leaves_on_contact;
	Button button_lock_onto_target_default_disabl;

	public GtsCommonGUIScreen(GtsCommonGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 400;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("gts:textures/screens/gts_common_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui.label_giantess_config_menu"), 9, 11, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui.label_general_giantess_behavior"), 99, 25, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui.label_giantess_natural_spawn_height"), 134, 81, -12829636, false);
		guiGraphics.drawString(this.font,

				GtsNaturalHeightDisplayProcedure.execute(world), 292, 81, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui.label_giantess_pathfinding"), 99, 114, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui.label_giantess_turn_speed"), 133, 176, -12829636, false);
		guiGraphics.drawString(this.font,

				GiantessTurnSpeedDisplayProcedure.execute(world), 240, 176, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui.label_page_1"), 360, 10, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableStomachSoundProcedure.execute(world), 203, 42, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableClothingDamageProcedure.execute(world), 349, 42, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableNaturalSpawnProcedure.execute(world), 203, 98, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableRaidWaveSizeChangeProcedure.execute(world), 254, 65, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableBelchProcedure.execute(world), 297, 98, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableBreakLeavesWhenWalkProcedure.execute(world), 254, 131, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableLockOnTargetProcedure.execute(world), 316, 154, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_empty = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_empty"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(0, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 318, this.topPos + 76, 30, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		button_empty1 = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_empty1"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(1, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 351, this.topPos + 76, 30, 20).build();
		guistate.put("button:button_empty1", button_empty1);
		this.addRenderableWidget(button_empty1);
		button_empty2 = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_empty2"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(2, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 271, this.topPos + 171, 30, 20).build();
		guistate.put("button:button_empty2", button_empty2);
		this.addRenderableWidget(button_empty2);
		button_empty3 = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_empty3"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(3, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 304, this.topPos + 171, 30, 20).build();
		guistate.put("button:button_empty3", button_empty3);
		this.addRenderableWidget(button_empty3);
		button_toki_config = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_toki_config"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(4, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 9, this.topPos + 36, 82, 20).build();
		guistate.put("button:button_toki_config", button_toki_config);
		this.addRenderableWidget(button_toki_config);
		button_candy_config = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_candy_config"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(5, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}).bounds(this.leftPos + 9, this.topPos + 63, 87, 20).build();
		guistate.put("button:button_candy_config", button_candy_config);
		this.addRenderableWidget(button_candy_config);
		button_reset_to_default = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_reset_to_default"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(6, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 6, x, y, z);
			}
		}).bounds(this.leftPos + 8, this.topPos + 171, 108, 20).build();
		guistate.put("button:button_reset_to_default", button_reset_to_default);
		this.addRenderableWidget(button_reset_to_default);
		button_next_page = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_next_page"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(7, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 7, x, y, z);
			}
		}).bounds(this.leftPos + 282, this.topPos + 5, 72, 20).build();
		guistate.put("button:button_next_page", button_next_page);
		this.addRenderableWidget(button_next_page);
		button_stomach_sound = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_stomach_sound"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(8, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 8, x, y, z);
			}
		}).bounds(this.leftPos + 107, this.topPos + 36, 93, 20).build();
		guistate.put("button:button_stomach_sound", button_stomach_sound);
		this.addRenderableWidget(button_stomach_sound);
		button_clothing_damage = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_clothing_damage"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(9, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 9, x, y, z);
			}
		}).bounds(this.leftPos + 243, this.topPos + 36, 103, 20).build();
		guistate.put("button:button_clothing_damage", button_clothing_damage);
		this.addRenderableWidget(button_clothing_damage);
		button_natural_spawn = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_natural_spawn"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(10, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 10, x, y, z);
			}
		}).bounds(this.leftPos + 107, this.topPos + 92, 93, 20).build();
		guistate.put("button:button_natural_spawn", button_natural_spawn);
		this.addRenderableWidget(button_natural_spawn);
		button_raid_wave_size_increase = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_raid_wave_size_increase"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(11, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 11, x, y, z);
			}
		}).bounds(this.leftPos + 107, this.topPos + 59, 145, 20).build();
		guistate.put("button:button_raid_wave_size_increase", button_raid_wave_size_increase);
		this.addRenderableWidget(button_raid_wave_size_increase);
		button_belch = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_belch"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(12, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 12, x, y, z);
			}
		}).bounds(this.leftPos + 243, this.topPos + 92, 51, 20).build();
		guistate.put("button:button_belch", button_belch);
		this.addRenderableWidget(button_belch);
		button_break_leaves_on_contact = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_break_leaves_on_contact"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(13, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 13, x, y, z);
			}
		}).bounds(this.leftPos + 107, this.topPos + 125, 145, 20).build();
		guistate.put("button:button_break_leaves_on_contact", button_break_leaves_on_contact);
		this.addRenderableWidget(button_break_leaves_on_contact);
		button_lock_onto_target_default_disabl = Button.builder(Component.translatable("gui.gts.gts_common_gui.button_lock_onto_target_default_disabl"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGUIButtonMessage(14, x, y, z));
				GtsCommonGUIButtonMessage.handleButtonAction(entity, 14, x, y, z);
			}
		}).bounds(this.leftPos + 107, this.topPos + 148, 207, 20).build();
		guistate.put("button:button_lock_onto_target_default_disabl", button_lock_onto_target_default_disabl);
		this.addRenderableWidget(button_lock_onto_target_default_disabl);
	}
}
