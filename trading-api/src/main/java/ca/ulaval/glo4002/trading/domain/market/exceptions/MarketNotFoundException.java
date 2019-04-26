package ca.ulaval.glo4002.trading.domain.market.exceptions;

import ca.ulaval.glo4002.trading.domain.commons.TradingApiException;

public class MarketNotFoundException extends TradingApiException {

    private final String market;

    public MarketNotFoundException(String market) {
        this.market = market;
    }

    public String getMarket() {
        return market;
    }

}
