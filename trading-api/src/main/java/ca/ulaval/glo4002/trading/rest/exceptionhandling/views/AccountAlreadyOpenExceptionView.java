package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountAlreadyOpenException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class AccountAlreadyOpenExceptionView extends BadRequest {

    private static final String ERROR = "ACCOUNT_ALREADY_OPEN";
    private static final String DESCRIPTION = "account already open for investor %s";
    private final InvestorId investorId;

    public AccountAlreadyOpenExceptionView(AccountAlreadyOpenException e) {
        this.investorId = e.getInvestorId();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, String.format(DESCRIPTION, investorId.getValue()));
    }

}
