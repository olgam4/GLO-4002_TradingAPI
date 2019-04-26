package ca.ulaval.glo4002.trading.domain.report;

import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.ValueObject;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

public class StockMarketReturn extends ValueObject {

    private final StockId stockId;
    private final float rateOfReturn;
    private final Money totalDividends;
    private final long quantity;

    StockMarketReturn(StockId stockId, float rateOfReturn, Money totalDividends, long quantity) {
        this.stockId = stockId;
        this.rateOfReturn = rateOfReturn;
        this.totalDividends = totalDividends;
        this.quantity = quantity;
    }

    public StockId getStockId() {
        return stockId;
    }

    public float getRateOfReturn() {
        return rateOfReturn;
    }

    public Money getTotalDividends() {
        return totalDividends;
    }

    public long getQuantity() {
        return quantity;
    }

}
