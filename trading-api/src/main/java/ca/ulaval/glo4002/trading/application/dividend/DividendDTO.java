package ca.ulaval.glo4002.trading.application.dividend;

import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;

public class DividendDTO {

    private LocalDateTime date;
    private float dividendPerShare;
    private StockId stockId;
    private Stock stock;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getDividendPerShare() {
        return dividendPerShare;
    }

    public void setDividendPerShare(float dividendPerShare) {
        this.dividendPerShare = dividendPerShare;
    }

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
