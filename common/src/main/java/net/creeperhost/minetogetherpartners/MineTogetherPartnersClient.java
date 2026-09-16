package net.creeperhost.minetogetherpartners;

import net.creeperhost.minetogether.session.MineTogetherSession;
import net.creeperhost.minetogetherpartners.orderform.OrderForm;
import net.creeperhost.minetogetherpartners.util.MTSessionProvider;
import net.creeperhost.polylib.event.events.client.PolyClientLifecycleEvents;
import net.creeperhost.polylib.event.events.client.PolyScreenEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MineTogetherPartnersClient {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        LOGGER.info("Initializing MineTogetherPartnersClient!");
        PolyClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            MineTogetherSession.getDefault().setProvider(new MTSessionProvider());
            MineTogetherSession.getDefault().onTokenRefreshed(token -> MineTogetherPartners.AUTH.setHeader("Authorization", "Bearer " + token));
            MineTogetherSession.getDefault().getTokenAsync();
        });
        PolyScreenEvents.SCREEN_OPENED.register((client, screen, width, height) -> OrderForm.onScreenPostInit(screen));
    }
}
