package ca.ulaval.glo4002.trading.application.dividend;

import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;

public class DividendPaymentDTO {

    private LocalDateTime date;
    private StockId stockId;
    private Money marketPrice;
    private Money value;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public Money getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Money marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

}
