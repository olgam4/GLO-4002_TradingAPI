package ca.ulaval.glo4002.trading.infrastructure.account;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.infrastructure.account.dao.AccountEntityManager;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedAccount;
import ca.ulaval.glo4002.trading.infrastructure.account.hydratators.AccountHydratator;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHibernateAccountRepository implements AccountRepository {

    private AccountEntityManager accountEntityManager;
    private AccountHydratator accountHydratator;

    public InMemoryHibernateAccountRepository() {
        this.accountEntityManager = new AccountEntityManager();
        this.accountHydratator = new AccountHydratator();
    }

    @Override
    public void save(Account account) {
        PersistedAccount persistedAccount = accountHydratator.dehydrate(account);
        accountEntityManager.create(persistedAccount);
    }

    @Override
    public List<Account> getAll() {
        List<PersistedAccount> persistedAccounts = accountEntityManager.getAll();
        List<Account> accounts = new ArrayList<>();
        for (PersistedAccount persistedAccount : persistedAccounts) {
            accounts.add(accountHydratator.hydrate(persistedAccount));
        }
        return accounts;
    }

    @Override
    public Account findByAccountNumber(AccountNumber accountNumber) {
        PersistedAccount persistedAccount = accountEntityManager.get(accountNumber.getValue());
        if (persistedAccount == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return accountHydratator.hydrate(persistedAccount);
    }

    @Override
    public boolean isExistingInvestor(InvestorId investorId) {
        List<Account> accounts = this.getAll();
        for (Account account : accounts) {
            if (investorId.equals(account.getInvestorId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Account account) {
        PersistedAccount persistedAccount = accountEntityManager.get(account.getAccountNumber().getValue());
        PersistedAccount updatedPersistedAccount = accountHydratator.update(account, persistedAccount);
        accountEntityManager.update(updatedPersistedAccount);
    }

}
