package ca.ulaval.glo4002.trading.domain.stock;

import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StockTest {

    private static final LocalDateTime VALID_DATE = LocalDateTime.of(2018, 8, 10, 2, 18, 20, 142000000);
    private static final LocalDateTime INVALID_DATE = LocalDateTime.of(2020, 8, 10, 2, 18, 20, 142000000);
    private static final String MARKET = "NASDAQ";
    private static final String SYMBOL = "MSFT";
    private static final StockType STOCK_TYPE = StockType.COMMON;
    private static final Money STOCK_PRICE = new Money(14.2d);

    private Stock stock;
    private Map<LocalDate, Money> prices;

    @Before
    public void setUp() {
        prices = new HashMap<>();
        prices.put(VALID_DATE.toLocalDate(), STOCK_PRICE);
        stock = new Stock(MARKET, SYMBOL, prices, STOCK_TYPE);
    }

    @Test
    public void givenAValidDate_whenGettingStockPrice_thenCorrectPriceIsReturned() throws InvalidDateException {
        Money stockPrice = stock.getPrice(VALID_DATE);
        assertEquals(STOCK_PRICE, stockPrice);
    }

    @Test(expected = InvalidDateException.class)
    public void givenAnInvalidDate_whenGettingStockPrice_thenThrows() throws InvalidDateException {
        stock.getPrice(INVALID_DATE);
    }

}