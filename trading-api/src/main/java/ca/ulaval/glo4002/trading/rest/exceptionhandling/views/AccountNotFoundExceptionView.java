package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class AccountNotFoundExceptionView extends NotFound {

    private static final String DESCRIPTION = "account with number %s not found";
    private static final String ERROR = "ACCOUNT_NOT_FOUND";
    private final AccountNumber accountNumber;

    public AccountNotFoundExceptionView(AccountNotFoundException e) {
        this.accountNumber = e.getAccountNumber();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, String.format(DESCRIPTION, accountNumber.getValue()));
    }

}
