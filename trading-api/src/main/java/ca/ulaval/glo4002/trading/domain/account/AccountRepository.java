package ca.ulaval.glo4002.trading.domain.account;

import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;

import java.util.List;

public interface AccountRepository {

    void save(Account account);

    void update(Account account);

    List<Account> getAll();

    Account findByAccountNumber(AccountNumber accountNumber);

    boolean isExistingInvestor(InvestorId investorId);

}
