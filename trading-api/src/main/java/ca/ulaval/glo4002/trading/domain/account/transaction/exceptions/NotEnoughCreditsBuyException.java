package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;

public class NotEnoughCreditsBuyException extends TransactionException {

    public NotEnoughCreditsBuyException(TransactionNumber transactionNumber) {
        super(transactionNumber);
    }

}
