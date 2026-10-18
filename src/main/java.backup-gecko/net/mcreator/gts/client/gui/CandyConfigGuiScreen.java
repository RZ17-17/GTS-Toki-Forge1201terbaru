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

import net.mcreator.gts.world.inventory.CandyConfigGuiMenu;
import net.mcreator.gts.procedures.CheckDisableCandyVoreProcedure;
import net.mcreator.gts.procedures.CheckDisableCandyThunderMagicProcedure;
import net.mcreator.gts.procedures.CheckDisableCandySwipeProcedure;
import net.mcreator.gts.procedures.CheckDisableCandyStepProcedure;
import net.mcreator.gts.procedures.CheckDisableCandySpitProcedure;
import net.mcreator.gts.procedures.CheckDisableCandySmashProcedure;
import net.mcreator.gts.procedures.CheckDisableCandyShockwaveProcedure;
import net.mcreator.gts.procedures.CheckDisableCandyKickProcedure;
import net.mcreator.gts.procedures.CheckDisableCandyHeartMagicProcedure;
import net.mcreator.gts.procedures.CheckDisableCandyBreachProcedure;
import net.mcreator.gts.network.CandyConfigGuiButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class CandyConfigGuiScreen extends AbstractContainerScreen<CandyConfigGuiMenu> {
	private final static HashMap<String, Object> guistate = CandyConfigGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_main_menu;
	Button button_stomp;
	Button button_ground_slam;
	Button button_kick;
	Button button_upper_swipe;
	Button button_upper_smash;
	Button button_breach;
	Button button_eat;
	Button button_spit;
	Button button_heart_magic;
	Button button_thunder_magic;

	public CandyConfigGuiScreen(CandyConfigGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 400;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("gts:textures/screens/candy_config_gui.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.candy_config_gui.label_candys_attacks"), 6, 22, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandyStepProcedure.execute(world), 59, 42, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandyShockwaveProcedure.execute(world), 186, 42, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandyKickProcedure.execute(world), 276, 42, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandySwipeProcedure.execute(world), 90, 66, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandySmashProcedure.execute(world), 215, 66, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandyBreachProcedure.execute(world), 315, 66, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandyVoreProcedure.execute(world), 48, 90, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandySpitProcedure.execute(world), 138, 90, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandyHeartMagicProcedure.execute(world), 264, 90, -12829636, false);
		guiGraphics.drawString(this.font,

				CheckDisableCandyThunderMagicProcedure.execute(world), 102, 114, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_main_menu = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_main_menu"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(0, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 317, this.topPos + 10, 72, 20).build();
		guistate.put("button:button_main_menu", button_main_menu);
		this.addRenderableWidget(button_main_menu);
		button_stomp = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_stomp"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(1, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 36, 51, 20).build();
		guistate.put("button:button_stomp", button_stomp);
		this.addRenderableWidget(button_stomp);
		button_ground_slam = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_ground_slam"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(2, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 102, this.topPos + 36, 82, 20).build();
		guistate.put("button:button_ground_slam", button_ground_slam);
		this.addRenderableWidget(button_ground_slam);
		button_kick = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_kick"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(3, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 229, this.topPos + 36, 46, 20).build();
		guistate.put("button:button_kick", button_kick);
		this.addRenderableWidget(button_kick);
		button_upper_swipe = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_upper_swipe"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(4, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 60, 82, 20).build();
		guistate.put("button:button_upper_swipe", button_upper_swipe);
		this.addRenderableWidget(button_upper_swipe);
		button_upper_smash = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_upper_smash"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(5, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}).bounds(this.leftPos + 132, this.topPos + 60, 82, 20).build();
		guistate.put("button:button_upper_smash", button_upper_smash);
		this.addRenderableWidget(button_upper_smash);
		button_breach = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_breach"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(6, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 6, x, y, z);
			}
		}).bounds(this.leftPos + 258, this.topPos + 60, 56, 20).build();
		guistate.put("button:button_breach", button_breach);
		this.addRenderableWidget(button_breach);
		button_eat = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_eat"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(7, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 7, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 84, 40, 20).build();
		guistate.put("button:button_eat", button_eat);
		this.addRenderableWidget(button_eat);
		button_spit = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_spit"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(8, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 8, x, y, z);
			}
		}).bounds(this.leftPos + 91, this.topPos + 84, 46, 20).build();
		guistate.put("button:button_spit", button_spit);
		this.addRenderableWidget(button_spit);
		button_heart_magic = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_heart_magic"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(9, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 9, x, y, z);
			}
		}).bounds(this.leftPos + 180, this.topPos + 84, 82, 20).build();
		guistate.put("button:button_heart_magic", button_heart_magic);
		this.addRenderableWidget(button_heart_magic);
		button_thunder_magic = Button.builder(Component.translatable("gui.gts.candy_config_gui.button_thunder_magic"), e -> {
			if (true) {
				GtsMod.sendToServer(new CandyConfigGuiButtonMessage(10, x, y, z));
				CandyConfigGuiButtonMessage.handleButtonAction(entity, 10, x, y, z);
			}
		}).bounds(this.leftPos + 7, this.topPos + 108, 93, 20).build();
		guistate.put("button:button_thunder_magic", button_thunder_magic);
		this.addRenderableWidget(button_thunder_magic);
	}
}
