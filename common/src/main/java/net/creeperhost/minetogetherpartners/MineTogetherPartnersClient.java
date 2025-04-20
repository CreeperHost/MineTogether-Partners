package net.creeperhost.minetogetherpartners;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import net.creeperhost.minetogether.session.MineTogetherSession;
import net.creeperhost.minetogetherpartners.util.MTSessionProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initialize on a client.
 * <p>
 * Created by covers1624 on 20/6/22.
 */
public class MineTogetherPartnersClient {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        LOGGER.info("Initializing MineTogetherPartnersClient!");

        //Cant do this in init anymore because init now occurs before Minecraft.instance is initialised.
        ClientLifecycleEvent.CLIENT_SETUP.register(instance -> {
            MineTogetherSession.getDefault().setProvider(new MTSessionProvider());
            MineTogetherSession.getDefault().onTokenRefreshed(token -> MineTogetherPartners.AUTH.setHeader("Authorization", "Bearer " + token));

            // Trigger session validation and set auth header.
            MineTogetherSession.getDefault().getTokenAsync();
        });
    }

}
