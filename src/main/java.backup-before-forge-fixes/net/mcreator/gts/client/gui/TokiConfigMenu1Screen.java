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

import net.mcreator.gts.world.inventory.TokiConfigMenu1Menu;
import net.mcreator.gts.procedures.CheckDisableVoreProcedure;
import net.mcreator.gts.procedures.CheckDisableUpperBreachProcedure;
import net.mcreator.gts.procedures.CheckDisableTrappedBreachProcedure;
import net.mcreator.gts.procedures.CheckDisableThrowProcedure;
import net.mcreator.gts.procedures.CheckDisableStepProcedure;
import net.mcreator.gts.procedures.CheckDisableSpitProcedure;
import net.mcreator.gts.procedures.CheckDisableSniperProcedure;
import net.mcreator.gts.procedures.CheckDisableSitProcedure;
import net.mcreator.gts.procedures.CheckDisableShowOffProcedure;
import net.mcreator.gts.procedures.CheckDisableMiddleBreachProcedure;
import net.mcreator.gts.procedures.CheckDisableLowerBreachProcedure;
import net.mcreator.gts.procedures.CheckDisableKickProcedure;
import net.mcreator.gts.procedures.CheckDisableGunSmashProcedure;
import net.mcreator.gts.procedures.CheckDisableGunSlapProcedure;
import net.mcreator.gts.procedures.CheckDisableDroneProcedure;
import net.mcreator.gts.network.TokiConfigMenu1ButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class TokiConfigMenu1Screen extends AbstractContainerScreen<TokiConfigMenu1Menu> {
	private final static HashMap<String, Object> guistate = TokiConfigMenu1Menu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_nextpage;
	Button button_stomp;
	Button button_kick;
	Button button_ground_slam;
	Button button_gun_slap;
	Button button_gun_smash;
	Button button_throw;
	Button button_eat;
	Button button_long_range_shot;
	Button button_spit;
	Button button_drone;
	Button button_upperbreach;
	Button button_middlebreach;
	Button button_lowerbreach;
	Button button_trappedbreach;
	Button button_celebration_after_kill;

	public TokiConfigMenu1Screen(TokiConfigMenu1Menu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 400;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("gts:textures/screens/toki_config_menu_1.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.toki_config_menu_1.label_tokis_attack"), 6, 23, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.toki_config_menu_1.label_tokis_breach"), 6, 101, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.toki_config_menu_1.label_tokis_behavior"), 6, 160, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableStepProcedure.execute(world), 59, 40, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableKickProcedure.execute(world), 150, 40, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableSitProcedure.execute(world), 277, 40, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableGunSlapProcedure.execute(world), 74, 62, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableGunSmashProcedure.execute(world), 191, 62, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableThrowProcedure.execute(world), 143, 84, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableVoreProcedure.execute(world), 47, 84, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableSniperProcedure.execute(world), 338, 62, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableSpitProcedure.execute(world), 234, 84, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableDroneProcedure.execute(world), 329, 84, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableUpperBreachProcedure.execute(world), 90, 118, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableMiddleBreachProcedure.execute(world), 222, 118, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableLowerBreachProcedure.execute(world), 349, 118, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableTrappedBreachProcedure.execute(world), 101, 141, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableShowOffProcedure.execute(world), 148, 176, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_nextpage = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_nextpage"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(0, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 324, this.topPos + 10, 67, 20).build();
		guistate.put("button:button_nextpage", button_nextpage);
		this.addRenderableWidget(button_nextpage);
		button_stomp = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_stomp"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(1, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 34, 51, 20).build();
		guistate.put("button:button_stomp", button_stomp);
		this.addRenderableWidget(button_stomp);
		button_kick = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_kick"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(2, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 102, this.topPos + 34, 46, 20).build();
		guistate.put("button:button_kick", button_kick);
		this.addRenderableWidget(button_kick);
		button_ground_slam = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_ground_slam"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(3, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 193, this.topPos + 34, 82, 20).build();
		guistate.put("button:button_ground_slam", button_ground_slam);
		this.addRenderableWidget(button_ground_slam);
		button_gun_slap = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_gun_slap"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(4, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 56, 66, 20).build();
		guistate.put("button:button_gun_slap", button_gun_slap);
		this.addRenderableWidget(button_gun_slap);
		button_gun_smash = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_gun_smash"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(5, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}).bounds(this.leftPos + 117, this.topPos + 56, 72, 20).build();
		guistate.put("button:button_gun_smash", button_gun_smash);
		this.addRenderableWidget(button_gun_smash);
		button_throw = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_throw"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(6, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 6, x, y, z);
			}
		}).bounds(this.leftPos + 90, this.topPos + 78, 51, 20).build();
		guistate.put("button:button_throw", button_throw);
		this.addRenderableWidget(button_throw);
		button_eat = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_eat"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(7, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 7, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 78, 40, 20).build();
		guistate.put("button:button_eat", button_eat);
		this.addRenderableWidget(button_eat);
		button_long_range_shot = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_long_range_shot"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(8, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 8, x, y, z);
			}
		}).bounds(this.leftPos + 233, this.topPos + 56, 103, 20).build();
		guistate.put("button:button_long_range_shot", button_long_range_shot);
		this.addRenderableWidget(button_long_range_shot);
		button_spit = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_spit"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(9, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 9, x, y, z);
			}
		}).bounds(this.leftPos + 186, this.topPos + 78, 46, 20).build();
		guistate.put("button:button_spit", button_spit);
		this.addRenderableWidget(button_spit);
		button_drone = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_drone"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(10, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 10, x, y, z);
			}
		}).bounds(this.leftPos + 277, this.topPos + 78, 51, 20).build();
		guistate.put("button:button_drone", button_drone);
		this.addRenderableWidget(button_drone);
		button_upperbreach = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_upperbreach"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(11, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 11, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 112, 82, 20).build();
		guistate.put("button:button_upperbreach", button_upperbreach);
		this.addRenderableWidget(button_upperbreach);
		button_middlebreach = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_middlebreach"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(12, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 12, x, y, z);
			}
		}).bounds(this.leftPos + 132, this.topPos + 112, 88, 20).build();
		guistate.put("button:button_middlebreach", button_middlebreach);
		this.addRenderableWidget(button_middlebreach);
		button_lowerbreach = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_lowerbreach"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(13, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 13, x, y, z);
			}
		}).bounds(this.leftPos + 265, this.topPos + 112, 82, 20).build();
		guistate.put("button:button_lowerbreach", button_lowerbreach);
		this.addRenderableWidget(button_lowerbreach);
		button_trappedbreach = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_trappedbreach"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(14, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 14, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 135, 93, 20).build();
		guistate.put("button:button_trappedbreach", button_trappedbreach);
		this.addRenderableWidget(button_trappedbreach);
		button_celebration_after_kill = Button.builder(Component.translatable("gui.gts.toki_config_menu_1.button_celebration_after_kill"), e -> {
			if (true) {
				GtsMod.sendToServer(new TokiConfigMenu1ButtonMessage(15, x, y, z));
				TokiConfigMenu1ButtonMessage.handleButtonAction(entity, 15, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 170, 140, 20).build();
		guistate.put("button:button_celebration_after_kill", button_celebration_after_kill);
		this.addRenderableWidget(button_celebration_after_kill);
	}
}
