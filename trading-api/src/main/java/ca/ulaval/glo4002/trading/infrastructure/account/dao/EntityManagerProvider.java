package ca.ulaval.glo4002.trading.infrastructure.account.dao;

import javax.persistence.EntityManager;

public class EntityManagerProvider {

    private static ThreadLocal<EntityManager> localEntityManager = new ThreadLocal<>();

    public static EntityManager getEntityManager() {
        return localEntityManager.get();
    }

    public static void setEntityManager(EntityManager entityManager) {
        localEntityManager.set(entityManager);
    }

    public static void clearEntityManager() {
        localEntityManager.set(null);
    }

}