package ca.ulaval.glo4002.trading.application.report;

import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

public class StockMarketReturnDTO {

    private StockId stockId;
    private float rateOfReturn;
    private Money totalDividends;
    private long quantity;

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public float getRateOfReturn() {
        return rateOfReturn;
    }

    public void setRateOfReturn(float rateOfReturn) {
        this.rateOfReturn = rateOfReturn;
    }

    public Money getTotalDividends() {
        return totalDividends;
    }

    public void setTotalDividends(Money totalDividends) {
        this.totalDividends = totalDividends;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
