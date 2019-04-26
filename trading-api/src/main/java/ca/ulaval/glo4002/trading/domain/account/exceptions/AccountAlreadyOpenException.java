package ca.ulaval.glo4002.trading.domain.account.exceptions;

import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.commons.TradingApiException;

public class AccountAlreadyOpenException extends TradingApiException {

    private final InvestorId investorId;

    public AccountAlreadyOpenException(InvestorId investorId) {
        this.investorId = investorId;
    }

    public InvestorId getInvestorId() {
        return investorId;
    }

}
