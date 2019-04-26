package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.NotEnoughStockException;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class NotEnoughStockExceptionView extends BadRequest {

    private static final String ERROR = "NOT_ENOUGH_STOCK";
    private static final String DESCRIPTION = "not enough stock '%s:%s'";
    private final TransactionNumber transactionNumber;
    private final StockId stockId;

    public NotEnoughStockExceptionView(NotEnoughStockException e) {
        this.transactionNumber = e.getTransactionNumber();
        this.stockId = e.getStockId();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(
                ERROR,
                String.format(DESCRIPTION, stockId.getSymbol(), stockId.getMarket()),
                transactionNumber
        );
    }

}
