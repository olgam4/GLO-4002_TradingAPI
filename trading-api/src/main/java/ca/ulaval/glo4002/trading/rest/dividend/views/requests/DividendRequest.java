package ca.ulaval.glo4002.trading.rest.dividend.views.requests;

import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;

public class DividendRequest {

    private float dps;
    private LocalDateTime date;
    private StockId stock;

    public float getDps() {
        return dps;
    }

    public void setDps(float dps) {
        this.dps = dps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StockId getStock() {
        return stock;
    }

    public void setStock(StockId stock) {
        this.stock = stock;
    }

}
