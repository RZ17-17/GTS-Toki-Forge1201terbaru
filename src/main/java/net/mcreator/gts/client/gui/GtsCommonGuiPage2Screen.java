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

import net.mcreator.gts.world.inventory.GtsCommonGuiPage2Menu;
import net.mcreator.gts.procedures.CheckDisableTamedMobAggroProcedure;
import net.mcreator.gts.procedures.CheckDisableTamedFriendlyFireProcedure;
import net.mcreator.gts.procedures.CheckDisableTamedBreachProcedure;
import net.mcreator.gts.network.GtsCommonGuiPage2ButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class GtsCommonGuiPage2Screen extends AbstractContainerScreen<GtsCommonGuiPage2Menu> {
	private final static HashMap<String, Object> guistate = GtsCommonGuiPage2Menu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_previous_page;
	Button button_tamed_giantess_friendly_fire;
	Button button_tamed_giantess_seek_out_monsters;
	Button button_tamed_giantess_breach_attack;

	public GtsCommonGuiPage2Screen(GtsCommonGuiPage2Menu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 400;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("gts:textures/screens/gts_common_gui_page_2.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui_page_2.label_giantess_config_main_menu"), 9, 11, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui_page_2.label_page_2"), 360, 10, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.gts_common_gui_page_2.label_tamed_giantess_config"), 8, 41, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableTamedFriendlyFireProcedure.execute(world), 182, 60, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableTamedMobAggroProcedure.execute(world), 203, 84, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableTamedBreachProcedure.execute(world), 182, 108, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_previous_page = Button.builder(Component.translatable("gui.gts.gts_common_gui_page_2.button_previous_page"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGuiPage2ButtonMessage(0, x, y, z));
				GtsCommonGuiPage2ButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 261, this.topPos + 5, 93, 20).build();
		guistate.put("button:button_previous_page", button_previous_page);
		this.addRenderableWidget(button_previous_page);
		button_tamed_giantess_friendly_fire = Button.builder(Component.translatable("gui.gts.gts_common_gui_page_2.button_tamed_giantess_friendly_fire"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGuiPage2ButtonMessage(1, x, y, z));
				GtsCommonGuiPage2ButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 8, this.topPos + 55, 171, 20).build();
		guistate.put("button:button_tamed_giantess_friendly_fire", button_tamed_giantess_friendly_fire);
		this.addRenderableWidget(button_tamed_giantess_friendly_fire);
		button_tamed_giantess_seek_out_monsters = Button.builder(Component.translatable("gui.gts.gts_common_gui_page_2.button_tamed_giantess_seek_out_monsters"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGuiPage2ButtonMessage(2, x, y, z));
				GtsCommonGuiPage2ButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 8, this.topPos + 79, 192, 20).build();
		guistate.put("button:button_tamed_giantess_seek_out_monsters", button_tamed_giantess_seek_out_monsters);
		this.addRenderableWidget(button_tamed_giantess_seek_out_monsters);
		button_tamed_giantess_breach_attack = Button.builder(Component.translatable("gui.gts.gts_common_gui_page_2.button_tamed_giantess_breach_attack"), e -> {
			if (true) {
				GtsMod.sendToServer(new GtsCommonGuiPage2ButtonMessage(3, x, y, z));
				GtsCommonGuiPage2ButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 8, this.topPos + 103, 171, 20).build();
		guistate.put("button:button_tamed_giantess_breach_attack", button_tamed_giantess_breach_attack);
		this.addRenderableWidget(button_tamed_giantess_breach_attack);
	}
}
