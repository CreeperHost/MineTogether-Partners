package net.creeperhost.minetogetherpartners.orderform;

import com.mojang.blaze3d.platform.InputConstants;
import net.creeperhost.minetogetherpartners.MineTogetherPartners;
import net.creeperhost.minetogetherpartners.config.LocalConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class CreeperHostServerEntry extends ServerSelectionList.Entry {
    private static final Identifier SERVER_ICON = Identifier.fromNamespaceAndPath(MineTogetherPartners.MOD_ID, "textures/creeperhost.png");
    private final Minecraft mc = Minecraft.getInstance();
    private final JoinMultiplayerScreen screen;
    private final ServerSelectionList list;
    private final Button removeButton;
    private float transparency = 0.5F;

    public CreeperHostServerEntry(JoinMultiplayerScreen screen, ServerSelectionList list) {
        this.screen = screen;
        this.list = list;
        removeButton = Button.builder(Component.literal("✖").withStyle(ChatFormatting.RED), button -> {
            LocalConfig.instance().mpMenuEnabled = false;
            LocalConfig.save();
            list.children().remove(this);
            list.setSelected(null);
        }).size(12, 12).build();
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
        int x = getContentX();
        int y = getContentY();
        transparency = Math.clamp(transparency + (hovered ? 0.04F : -0.04F), 0.5F, 1.0F);
        int alpha = (int) (transparency * 255) << 24;
        graphics.blit(RenderPipelines.GUI_TEXTURED, SERVER_ICON, x, y, 0, 0, 32, 32, 32, 32, alpha | 0xFFFFFF);
        graphics.text(mc.font, Component.translatable("minetogether.multiplayerscreen.partner"), x + 35, y, alpha | 0xFFFFFF);
        graphics.text(mc.font, Component.translatable("minetogether.multiplayerscreen.getserver"), x + 35, y + mc.font.lineHeight + 1, alpha | 0xFFFFFF);
        graphics.text(mc.font, Component.translatable("minetogether.multiplayerscreen.clickherebrand"), x + 35, y + mc.font.lineHeight * 2 + 3, alpha | 0x808080);
        removeButton.setPosition(getContentRight() - 12, y);
        removeButton.extractRenderState(graphics, mouseX, mouseY, partialTick);
        if (removeButton.isMouseOver(mouseX, mouseY)) {
            graphics.setTooltipForNextFrame(Component.translatable("minetogether.multiplayerscreen.hide_ad"), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (removeButton.mouseClicked(event, doubleClick)) return true;
        if (event.button() != InputConstants.MOUSE_BUTTON_LEFT) return false;
        join();
        return true;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.isSelection()) {
            join();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public void join() {
        mc.gui.setScreen(new OrderGui.Screen(screen));
    }

    @Override
    protected boolean matches(ServerSelectionList.Entry other) {
        return other instanceof CreeperHostServerEntry;
    }

    @Override
    public Component getNarration() {
        return Component.translatable("minetogether.multiplayerscreen.getserver");
    }
}
