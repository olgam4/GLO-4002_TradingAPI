package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.InvalidTransactionNumberException;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class InvalidTransactionNumberExceptionView extends BadRequest {

    private static final String ERROR = "INVALID_TRANSACTION_NUMBER";
    private static final String DESCRIPTION = "the transaction number is invalid";
    private final TransactionNumber transactionNumber;

    public InvalidTransactionNumberExceptionView(InvalidTransactionNumberException e) {
        this.transactionNumber = e.getTransactionNumber();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, DESCRIPTION, transactionNumber);
    }

}
