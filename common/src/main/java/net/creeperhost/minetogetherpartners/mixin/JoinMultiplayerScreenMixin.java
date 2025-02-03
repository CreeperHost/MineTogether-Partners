package net.creeperhost.minetogetherpartners.mixin;

import net.creeperhost.minetogetherpartners.orderform.CreeperHostServerEntry;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin (JoinMultiplayerScreen.class)
public abstract class JoinMultiplayerScreenMixin {

    @Shadow
    protected ServerSelectionList serverSelectionList;

    @Inject (at = @At ("HEAD"), method = "joinSelectedServer", cancellable = true)
    public void joinSelectedServer(CallbackInfo ci) {
        ServerSelectionList.Entry entry = this.serverSelectionList.getSelected();
        if (entry instanceof CreeperHostServerEntry) {
            ci.cancel();
        }
    }

}
