package net.creeperhost.minetogetherpartners.neoforge;

import net.creeperhost.minetogetherpartners.MineTogetherPartners;
import net.creeperhost.minetogetherpartners.MineTogetherPlatform;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

@Mod(MineTogetherPartners.MOD_ID)
public class MineTogetherNeoForge {
    public MineTogetherNeoForge(ModContainer container) {
        var mod = container.getModInfo();
        MineTogetherPlatform.initialize(mod.getOwningFile().getFile().getFilePath(), FMLPaths.GAMEDIR.get(), mod.getVersion().toString());
        MineTogetherPartners.init();
    }
}
