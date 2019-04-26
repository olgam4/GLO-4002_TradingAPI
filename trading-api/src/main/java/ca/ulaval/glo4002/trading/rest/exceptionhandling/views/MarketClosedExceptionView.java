package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.market.exceptions.MarketClosedException;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class MarketClosedExceptionView extends BadRequest {

    private static final String ERROR = "MARKET_CLOSED";
    private static final String DESCRIPTION = "market '%s' is closed";
    private final String market;
    private final TransactionNumber transactionNumber;

    public MarketClosedExceptionView(MarketClosedException e) {
        this.market = e.getMarket();
        this.transactionNumber = e.getTransactionNumber();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, String.format(DESCRIPTION, market), transactionNumber);
    }

}
