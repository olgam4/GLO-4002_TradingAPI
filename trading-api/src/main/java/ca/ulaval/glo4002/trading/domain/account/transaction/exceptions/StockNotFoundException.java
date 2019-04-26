package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.commons.TradingApiException;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

public class StockNotFoundException extends TradingApiException {

    private final StockId stockId;

    public StockNotFoundException(StockId stockId) {
        this.stockId = stockId;
    }

    public StockId getStockId() {
        return stockId;
    }

}
