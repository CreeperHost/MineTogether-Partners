package net.creeperhost.minetogetherpartners.compat.kubejs;

import net.creeperhost.minetogetherpartners.orderform.OrderGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Consumer;

public interface KubeJSUIIntegration {
    Consumer<Screen> ORDER = screen -> Minecraft.getInstance().setScreen(new OrderGui.Screen(screen));
}
