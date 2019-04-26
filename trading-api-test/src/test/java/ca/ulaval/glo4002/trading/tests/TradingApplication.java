package ca.ulaval.glo4002.trading.tests;

import ca.ulaval.glo4002.trading.TradingServer;
import ca.ulaval.glo4002.trading.rest.databind.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;


public class TradingApplication extends ResourceConfig {

    public TradingApplication() {
        packages(TradingServer.class.getPackage().getName());
        register(JacksonFeature.class);
    }
}
