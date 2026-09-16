package net.creeperhost.minetogetherpartners.fabric;

import net.creeperhost.minetogetherpartners.MineTogetherPartners;
import net.creeperhost.minetogetherpartners.MineTogetherPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModOrigin;
import java.nio.file.Path;

public class MineTogetherFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        var loader = FabricLoader.getInstance();
        var mod = loader.getModContainer(MineTogetherPartners.MOD_ID).orElseThrow();
        var origin = mod.getOrigin();
        Path jar = origin.getKind() == ModOrigin.Kind.PATH && !origin.getPaths().isEmpty() ? origin.getPaths().getFirst() : null;
        MineTogetherPlatform.initialize(jar, loader.getGameDir(), mod.getMetadata().getVersion().getFriendlyString());
        MineTogetherPartners.init();
    }
}
