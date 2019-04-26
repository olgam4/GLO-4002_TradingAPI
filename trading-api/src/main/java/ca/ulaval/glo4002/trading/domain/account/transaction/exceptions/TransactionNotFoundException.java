package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;

public class TransactionNotFoundException extends TransactionException {

    public TransactionNotFoundException(TransactionNumber transactionNumber) {
        super(transactionNumber);
    }

}
