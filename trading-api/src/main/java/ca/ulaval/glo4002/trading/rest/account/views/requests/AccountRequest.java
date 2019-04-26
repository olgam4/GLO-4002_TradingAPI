package ca.ulaval.glo4002.trading.rest.account.views.requests;

import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;

import java.util.List;

public class AccountRequest {

    private InvestorId investorId;
    private String investorName;
    private String email;
    private List<CreditRequest> credits;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CreditRequest> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditRequest> credits) {
        this.credits = credits;
    }

}