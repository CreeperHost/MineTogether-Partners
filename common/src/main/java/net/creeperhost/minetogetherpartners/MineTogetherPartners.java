package net.creeperhost.minetogetherpartners;

import net.creeperhost.polylib.platform.Services;

import net.covers1624.quack.net.httpapi.HttpEngine;
import net.covers1624.quack.net.httpapi.java11.Java11HttpEngine;
import net.creeperhost.minetogetherpartners.config.Config;
import net.creeperhost.minetogether.lib.MineTogetherLib;
import net.creeperhost.minetogether.lib.web.ApiClient;
import net.creeperhost.minetogether.lib.web.DynamicWebAuth;
import net.creeperhost.minetogetherpartners.orderform.WebUtils;
import net.creeperhost.minetogetherpartners.util.Log4jUtils;
import net.creeperhost.minetogetherpartners.util.ModPackInfo;
import net.creeperhost.minetogetherpartners.util.SignatureVerifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main common mod entrypoint.
 * <p>
 * Created by covers1624 on 20/6/22.
 */
public class MineTogetherPartners {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "minetogetherpartners";

    public static final String FINGERPRINT = SignatureVerifier.generateSignature();
    public static final DynamicWebAuth AUTH = new DynamicWebAuth();
    public static final HttpEngine WEB_ENGINE = Java11HttpEngine.create();
    public static final ApiClient API = ApiClient.builder()
            .httpEngine(WEB_ENGINE)
            .addUserAgentSegment("MineTogether-lib/" + MineTogetherLib.VERSION)
            .addUserAgentSegment("MineTogether-Partners-mod/" + MineTogetherPlatform.getVersion())
            .addUserAgentSegment("Minecraft/" + net.minecraft.SharedConstants.getCurrentVersion().name())
            .addUserAgentSegment("Modloader/" + Services.PLATFORM.getPlatformName())
            .webAuth(AUTH)
            .build();
    static {
        WebUtils.userAgent += " MineTogether-lib/" + MineTogetherLib.VERSION;
        WebUtils.userAgent += " MineTogether-mod/" + MineTogetherPlatform.getVersion();
        WebUtils.userAgent += " Minecraft/" + net.minecraft.SharedConstants.getCurrentVersion().name();
        WebUtils.userAgent += " Modloader/" + Services.PLATFORM.getPlatformName();
    }

    public static void init() {
        Log4jUtils.attachMTLogs(MineTogetherPlatform.getGameFolder().resolve("logs"));
        LOGGER.info("Initializing MineTogether Partners!");
        AUTH.setHeader("Fingerprint", FINGERPRINT);

        if (Config.instance().debugMode) {
            LOGGER.warn("Debug mode enabled. Prepare for _VERY_ verbose logging!");
        }

        if (Services.PLATFORM.isClient()) {
            MineTogetherPartnersClient.init();
        }

        ModPackInfo.init();
        ModPackInfo.waitForInfo(info -> AUTH.setHeader("Identifier", info.realName));

    }
}
