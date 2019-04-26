package ca.ulaval.glo4002.trading.domain.stock;

import ca.ulaval.glo4002.trading.domain.commons.ValueObject;

public class StockId extends ValueObject {

    private final String market;
    private final String symbol;

    public StockId(String market, String symbol) {
        this.market = market;
        this.symbol = symbol;
    }

    public String getMarket() {
        return market;
    }

    public String getSymbol() {
        return symbol;
    }

}
