package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.commons.TradingApiException;

public class TransactionException extends TradingApiException {

    private final TransactionNumber transactionNumber;

    public TransactionException(TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

}
