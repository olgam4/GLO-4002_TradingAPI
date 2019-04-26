package ca.ulaval.glo4002.trading.infrastructure.market;

import ca.ulaval.glo4002.trading.domain.money.Currency;

class CurrencyMapper {
    static Currency convertMarketToCurrency(String market) {
        switch (market.toUpperCase()) {
            case "NASDAQ":
                return Currency.USD;
            case "XTKS":
                return Currency.JPY;
            case "XSWX":
                return Currency.CHF;
            case "NYSE":
                return Currency.USD;
            default:
                return Currency.UNKNOWN;
        }
    }
}
