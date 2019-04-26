package ca.ulaval.glo4002.trading.rest.exceptionhandling;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;

public class ExceptionMessage {

    private final String error;
    private final String description;
    private final TransactionNumber transactionNumber;

    public ExceptionMessage(String error, String description) {
        this(error, description, null);
    }

    public ExceptionMessage(String error, String description, TransactionNumber transactionNumber) {
        this.error = error;
        this.description = description;
        this.transactionNumber = transactionNumber;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

}