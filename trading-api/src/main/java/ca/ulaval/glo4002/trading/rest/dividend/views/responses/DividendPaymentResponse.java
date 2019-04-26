package ca.ulaval.glo4002.trading.rest.dividend.views.responses;

import ca.ulaval.glo4002.trading.domain.money.Money;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"market", "symbol", "marketprice", "dividends"})
public class DividendPaymentResponse {

    private String market;
    private String symbol;
    private Money marketprice;
    private Money dividends;

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

    public Money getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(Money marketprice) {
        this.marketprice = marketprice;
    }

    public Money getDividends() {
        return dividends;
    }

    public void setDividends(Money dividends) {
        this.dividends = dividends;
    }

}
