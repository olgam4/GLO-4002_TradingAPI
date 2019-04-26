package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;

public class StockParametersDontMatchException extends TransactionException {

    public StockParametersDontMatchException(TransactionNumber transactionNumber) {
        super(transactionNumber);
    }

}
