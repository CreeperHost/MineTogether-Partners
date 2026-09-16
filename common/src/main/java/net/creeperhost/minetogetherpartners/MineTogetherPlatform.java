package net.creeperhost.minetogetherpartners;

import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;

/** Mod metadata supplied by the loader before common initialization. */
public final class MineTogetherPlatform {
    private static Path modJar;
    private static Path gameFolder;
    private static String version;

    private MineTogetherPlatform() {}

    public static void initialize(@Nullable Path jar, Path gameDir, String modVersion) {
        modJar = jar;
        gameFolder = gameDir;
        version = modVersion;
    }

    public static @Nullable Path getModJar() { return modJar; }
    public static Path getGameFolder() { return gameFolder; }
    public static String getVersion() { return version; }
}
