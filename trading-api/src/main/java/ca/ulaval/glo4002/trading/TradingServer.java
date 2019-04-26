package ca.ulaval.glo4002.trading;

import ca.ulaval.glo4002.trading.contexts.ApplicationContext;
import ca.ulaval.glo4002.trading.contexts.DevContext;
import ca.ulaval.glo4002.trading.contexts.ProdContext;
import ca.ulaval.glo4002.trading.rest.databind.JacksonFeature;
import ca.ulaval.glo4002.trading.rest.filters.EntityManagerContextFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Optional;

public class TradingServer implements Runnable {

    private static final String PORT_PROPERTY = "port";
    private static final int DEFAULT_PORT = 8181;
    private static final String CONTEXT_PROPERTY = "context";
    private static final String DEFAULT_CONTEXT = "prod";

    public static void main(String[] args) {
        new TradingServer().run();
    }

    private Server server;

    @Override
    public void run() {
        int httpPort = Optional.ofNullable(System.getProperty(PORT_PROPERTY)).map(Integer::parseInt)
                .orElse(DEFAULT_PORT);
        ApplicationContext context =
                resolveContext(Optional.ofNullable(System.getProperty(CONTEXT_PROPERTY)).orElse(DEFAULT_CONTEXT));
        TradingServer server = new TradingServer();
        server.start(httpPort, context);
        server.join();
    }

    private static ApplicationContext resolveContext(String contextName) {
        switch (contextName) {
            case "prod":
                return new ProdContext();
            case "dev":
                return new DevContext();
            default:
                throw new RuntimeException("Cannot load context " + contextName);
        }
    }

    public void start(int httpPort, ApplicationContext context) {
        start(httpPort, context, true);
    }

    public void start(int httpPort, ApplicationContext context, boolean registerEntityManagerFilter) {
        context.execute();
        server = new Server(httpPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        configureJersey(servletContextHandler, registerEntityManagerFilter);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tryStopServer();
        }
    }

    public void join() {
        try {
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tryStopServer();
        }
    }

    private void tryStopServer() {
        try {
            server.destroy();
        } catch (Exception e) {
            return;
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureJersey(ServletContextHandler servletContextHandler, boolean registerEntityManagerFilter) {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("ca.ulaval.glo4002.trading.rest");
        resourceConfig.register(JacksonFeature.class);
        ServletContainer container = new ServletContainer(resourceConfig);
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
        if (registerEntityManagerFilter) {
            servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        }
    }

}
