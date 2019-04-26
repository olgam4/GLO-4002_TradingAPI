package ca.ulaval.glo4002.trading.rest.report.views.responses;

import ca.ulaval.glo4002.trading.domain.money.Money;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"market", "symbol", "rateOfReturn", "totalDividends", "quantity"})
public class StockMarketReturnResponse {

    private String market;
    private String symbol;
    private float rateOfReturn;
    private Money totalDividends;
    private long quantity;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
