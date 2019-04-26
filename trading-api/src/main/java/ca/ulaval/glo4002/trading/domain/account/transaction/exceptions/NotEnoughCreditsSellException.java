package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;

public class NotEnoughCreditsSellException extends TransactionException {

    public NotEnoughCreditsSellException(TransactionNumber transactionNumber) {
        super(transactionNumber);
    }

}
