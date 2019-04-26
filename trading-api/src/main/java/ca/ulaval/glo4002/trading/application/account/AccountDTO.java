package ca.ulaval.glo4002.trading.application.account;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorType;
import ca.ulaval.glo4002.trading.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO {

    private AccountNumber accountNumber;
    private InvestorId investorId;
    private String investorName;
    private InvestorType investorType;
    private List<String> focusAreas = new ArrayList<>();
    private List<CreditDTO> credits;
    private Money totalCredits;

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public InvestorId getInvestorId() {
        return investorId;
    }

    public void setInvestorId(InvestorId investorId) {
        this.investorId = investorId;
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    public InvestorType getInvestorType() {
        return investorType;
    }

    public void setInvestorType(InvestorType investorType) {
        this.investorType = investorType;
    }

    public List<String> getFocusAreas() {
        return focusAreas;
    }

    public void setFocusAreas(List<String> focusAreas) {
        this.focusAreas = focusAreas;
    }

    public List<CreditDTO> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditDTO> credits) {
        this.credits = credits;
    }

    public Money getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Money totalCredits) {
        this.totalCredits = totalCredits;
    }
}
