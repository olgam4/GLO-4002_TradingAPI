package ca.ulaval.glo4002.trading.infrastructure.market.dto;

import ca.ulaval.glo4002.trading.domain.money.Currency;

import java.util.List;

public class RestApiMarketDTO {

    private List<String> openHours;
    private String symbol;
    private String timezone;
    private Currency currency;

    public void setOpenHours(List<String> openHours) {
        this.openHours = openHours;
    }

    public void setMarket(String symbol) {
        this.symbol = symbol;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<String> getOpenHours() {
        return openHours;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getTimezone() {
        return timezone;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
