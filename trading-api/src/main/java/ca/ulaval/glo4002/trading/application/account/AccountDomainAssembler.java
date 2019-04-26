package ca.ulaval.glo4002.trading.application.account;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;

public class AccountDomainAssembler {
    private CreditDomainAssembler creditDomainAssembler;

    public AccountDomainAssembler() {
        this.creditDomainAssembler = new CreditDomainAssembler();
    }

    public Account from(AccountDTO accountDTO) {
        InvestorId investorId = accountDTO.getInvestorId();
        String investorName = accountDTO.getInvestorName();
        Credits credits = creditDomainAssembler.from(accountDTO.getCredits());
        return new Account(investorId, investorName, credits);
    }

    public AccountDTO from(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setInvestorId(account.getInvestorId());
        accountDTO.setInvestorType(account.getInvestorType());
        accountDTO.setFocusAreas(account.getFocusAreas());
        accountDTO.setCredits(creditDomainAssembler.from(account.getBalance()));
        accountDTO.setTotalCredits(account.getTotalBalance());
        return accountDTO;
    }

}
