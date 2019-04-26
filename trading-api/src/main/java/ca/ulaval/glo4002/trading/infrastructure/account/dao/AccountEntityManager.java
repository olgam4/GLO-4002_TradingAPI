package ca.ulaval.glo4002.trading.infrastructure.account.dao;

import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedAccount;

import javax.persistence.EntityManager;
import java.util.List;

public class AccountEntityManager {

    public AccountEntityManager() {
    }

    public void create(PersistedAccount persistedAccount) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(persistedAccount);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public void update(PersistedAccount accountToUpdate) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(accountToUpdate);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public PersistedAccount get(String persistedAccountNumber) {
        List<PersistedAccount> persistedAccounts = this.getAll();
        for (PersistedAccount persistedAccount : persistedAccounts) {
            if (persistedAccountNumber.equals(persistedAccount.getAccountNumber())) {
                return persistedAccount;
            }
        }
        return null;
    }

    public List<PersistedAccount> getAll() {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        return entityManager.createQuery("select o from PersistedAccount o", PersistedAccount.class).getResultList();
    }

    public void delete() {
    }

}
