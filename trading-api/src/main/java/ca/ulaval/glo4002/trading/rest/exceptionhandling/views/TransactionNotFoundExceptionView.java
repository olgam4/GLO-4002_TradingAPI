package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.TransactionNotFoundException;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class TransactionNotFoundExceptionView extends NotFound {

    private static final String ERROR = "TRANSACTION_NOT_FOUND";
    private static final String DESCRIPTION = "transaction with number %s not found";
    private final TransactionNumber transactionNumber;

    public TransactionNotFoundExceptionView(TransactionNotFoundException e) {
        this.transactionNumber = e.getTransactionNumber();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, String.format(DESCRIPTION, transactionNumber.toString()));
    }

}
