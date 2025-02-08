package net.creeperhost.minetogetherpartners.fabric;

import dev.architectury.platform.Platform;
import net.creeperhost.minetogetherpartners.MineTogetherPartners;
import net.creeperhost.minetogetherpartners.gui.MTTextures;
import net.creeperhost.minetogetherpartners.orderform.OrderForm;
import net.creeperhost.polylib.fabric.client.ResourceReloadListenerWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

/**
 * Created by covers1624 on 20/6/22.
 */
public class MineTogetherFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        MineTogetherPartners.init();

        if (Platform.getEnv() == EnvType.CLIENT) {
            clientInit();
            ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ResourceReloadListenerWrapper(MTTextures::getAtlasHolder, new ResourceLocation(MineTogetherPartners.MOD_ID, "gui_atlas_reload")));
        }
    }

    private void clientInit() {
        //We need this because INIT_POST from architectury only works in the initial init event.
        //It does not fire on re-init, e.g. when window is resized. I would consider this a bug in architectury.
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> OrderForm.onScreenPostInit(screen));
    }
}