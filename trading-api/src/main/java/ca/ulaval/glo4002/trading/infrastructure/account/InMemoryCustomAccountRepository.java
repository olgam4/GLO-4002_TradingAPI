package ca.ulaval.glo4002.trading.infrastructure.account;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCustomAccountRepository implements AccountRepository {

    private final Map<AccountNumber, Account> accounts = new HashMap<>();

    @Override
    public void save(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public void update(Account account) {
        save(account);
    }

    public boolean contains(AccountNumber accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public Account findByAccountNumber(AccountNumber accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber);
        }
        throw new AccountNotFoundException(accountNumber);
    }

    @Override
    public boolean isExistingInvestor(InvestorId investorId) {
        for (Account account : accounts.values()) {
            if (investorId.equals(account.getInvestorId())) {
                return true;
            }
        }
        return false;
    }

    public void deleteByAccountNumber(AccountNumber accountNumber) {
        accounts.remove(accountNumber);
    }

    boolean isEmpty() {
        return accounts.isEmpty();
    }

}
