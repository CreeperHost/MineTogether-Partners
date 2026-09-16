package net.creeperhost.minetogetherpartners.mixin.order;

import net.creeperhost.minetogetherpartners.config.LocalConfig;
import net.creeperhost.minetogetherpartners.orderform.CreeperHostServerEntry;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerSelectionList.class)
public class MixinServerSelectionList {
    @Shadow @Final private JoinMultiplayerScreen screen;

    @Inject(method = "refreshEntries()V", at = @At("RETURN"))
    private void afterRefreshEntries(CallbackInfo info) {
        if (!LocalConfig.instance().mpMenuEnabled) return;
        ServerSelectionList list = (ServerSelectionList) (Object) this;
        list.addEntry(new CreeperHostServerEntry(screen, list));
    }
}
