package ca.ulaval.glo4002.trading.domain.stock;

import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Stock {

    private StockId stockId;
    private Map<LocalDate, Money> prices;
    private StockType type;

    public Stock(String market, String symbol, Map<LocalDate, Money> prices, StockType type) {
        this.stockId = new StockId(market, symbol);
        this.prices = prices;
        this.type = type;
    }

    public StockId getStockId() {
        return stockId;
    }

    public Money getPrice(LocalDateTime date) throws InvalidDateException {
        LocalDate localDate = date.toLocalDate();
        Money price = prices.get(localDate);
        if (price == null) {
            throw new InvalidDateException();
        }
        return price;
    }

    public StockType getType() {
        return type;
    }

    public float getDividendRate() {
        return type.getRate();
    }

    public void setCurrency(Currency currency) {
        prices.forEach((date, money) -> {
            money.setCurrency(currency);
        });
    }
}
