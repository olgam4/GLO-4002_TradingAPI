package ca.ulaval.glo4002.trading.infrastructure.account.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {

    private final static String persistenceUnitName = "trading-api-persistence";
    private static EntityManagerFactory instance;

    public static EntityManagerFactory getFactory() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
        return instance;
    }

}
