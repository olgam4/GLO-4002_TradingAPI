package ca.ulaval.glo4002.trading.rest.account.views.responses;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.money.Money;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"accountNumber", "investorId", "investorProfile", "credits"})
public class AccountResponse {

    private AccountNumber accountNumber;
    private InvestorId investorId;
    private InvestorProfileResponse investorProfile;
    private List<CreditResponse> credits;
    private Money total;

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

    public InvestorProfileResponse getInvestorProfile() {
        return investorProfile;
    }

    public void setInvestorProfile(InvestorProfileResponse investorProfile) {
        this.investorProfile = investorProfile;
    }

    public List<CreditResponse> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditResponse> credits) {
        this.credits = credits;
    }

    public Money getTotal() {
        return total;
    }

    public void setTotal(Money total) {
        this.total = total;
    }
}