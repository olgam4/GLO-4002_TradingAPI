package ca.ulaval.glo4002.trading.domain.market.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.TransactionException;

public class MarketClosedException extends TransactionException {

    private final String market;

    public MarketClosedException(String market, TransactionNumber transactionNumber) {
        super(transactionNumber);
        this.market = market;
    }

    public String getMarket() {
        return market;
    }

}
