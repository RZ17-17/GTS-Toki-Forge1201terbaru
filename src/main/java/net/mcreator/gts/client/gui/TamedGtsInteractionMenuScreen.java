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

import net.mcreator.gts.world.inventory.TamedGtsInteractionMenuMenu;
import net.mcreator.gts.network.TamedGtsInteractionMenuButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class TamedGtsInteractionMenuScreen extends AbstractContainerScreen<TamedGtsInteractionMenuMenu> {
	private final static HashMap<String, Object> guistate = TamedGtsInteractionMenuMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_sitstand;
	Button button_size_increase;
	Button button_size_decrease;

	public TamedGtsInteractionMenuScreen(TamedGtsInteractionMenuMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 250;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("gts:textures/screens/tamed_gts_interaction_menu.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.tamed_gts_interaction_menu.label_tamed_giantess_interaction_menu"), 4, 4, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.tamed_gts_interaction_menu.label_cost_1_golden_apple"), 99, 42, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.gts.tamed_gts_interaction_menu.label_cost_1_golden_carrot"), 99, 63, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_sitstand = Button.builder(Component.translatable("gui.gts.tamed_gts_interaction_menu.button_sitstand"), e -> {
			if (true) {
				GtsMod.sendToServer(new TamedGtsInteractionMenuButtonMessage(0, x, y, z));
				TamedGtsInteractionMenuButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 4, this.topPos + 15, 72, 20).build();
		guistate.put("button:button_sitstand", button_sitstand);
		this.addRenderableWidget(button_sitstand);
		button_size_increase = Button.builder(Component.translatable("gui.gts.tamed_gts_interaction_menu.button_size_increase"), e -> {
			if (true) {
				GtsMod.sendToServer(new TamedGtsInteractionMenuButtonMessage(1, x, y, z));
				TamedGtsInteractionMenuButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 4, this.topPos + 36, 93, 20).build();
		guistate.put("button:button_size_increase", button_size_increase);
		this.addRenderableWidget(button_size_increase);
		button_size_decrease = Button.builder(Component.translatable("gui.gts.tamed_gts_interaction_menu.button_size_decrease"), e -> {
			if (true) {
				GtsMod.sendToServer(new TamedGtsInteractionMenuButtonMessage(2, x, y, z));
				TamedGtsInteractionMenuButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 4, this.topPos + 57, 93, 20).build();
		guistate.put("button:button_size_decrease", button_size_decrease);
		this.addRenderableWidget(button_size_decrease);
	}
}
