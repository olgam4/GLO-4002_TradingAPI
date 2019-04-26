package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;

public class InvalidTransactionNumberException extends TransactionException {

    public InvalidTransactionNumberException(TransactionNumber transactionNumber) {
        super(transactionNumber);
    }

}
