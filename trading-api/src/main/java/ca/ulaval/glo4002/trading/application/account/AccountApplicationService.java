package ca.ulaval.glo4002.trading.application.account;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountAlreadyOpenException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;

public class AccountApplicationService {

    private final AccountRepository accountRepository;
    private final AccountDomainAssembler accountDomainAssembler;

    public AccountApplicationService() {
        this(
                ServiceLocator.resolve(AccountRepository.class),
                ServiceLocator.resolve(AccountDomainAssembler.class)
        );
    }

    AccountApplicationService(AccountRepository accountRepository,
                              AccountDomainAssembler accountDomainAssembler) {
        this.accountRepository = accountRepository;
        this.accountDomainAssembler = accountDomainAssembler;
    }

    public AccountNumber createAccount(AccountDTO accountDTO) {
        checkIfAlreadyOpenAccount(accountDTO.getInvestorId());
        Account account = accountDomainAssembler.from(accountDTO);
        accountRepository.save(account);
        return account.getAccountNumber();
    }

    private void checkIfAlreadyOpenAccount(InvestorId investorId) {
        if (accountRepository.isExistingInvestor(investorId)) {
            throw new AccountAlreadyOpenException(investorId);
        }
    }

    public AccountDTO getByAccountNumber(AccountNumber accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        return accountDomainAssembler.from(account);
    }

}