package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.StockNotFoundException;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class StockNotFoundExceptionView extends BadRequest {

    private static final String ERROR = "STOCK_NOT_FOUND";
    private static final String DESCRIPTION = "stock '%s:%s' not found";
    private StockId stockId;

    public StockNotFoundExceptionView(StockNotFoundException e) {
        this.stockId = e.getStockId();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(
                ERROR,
                String.format(DESCRIPTION, stockId.getSymbol(), stockId.getMarket())
        );
    }

}
