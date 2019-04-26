package ca.ulaval.glo4002.trading.domain.market;

import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class MarketTest {

    private static final LocalDateTime VALID_DATE = LocalDateTime.of(2018, 11, 12, 8, 0, 0);
    private static final LocalDateTime SUNDAY = LocalDateTime.of(2018, 1, 7, 0, 0, 0);
    private static final String MARKET_SYMBOL = "MARKET";
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    private Market market;

    @Mock
    private Period period;

    @Before
    public void setUp() {
        List<Period> openHours = new ArrayList<>();
        openHours.add(period);
        market = new Market(MARKET_SYMBOL, ZONE_OFFSET, openHours, Currency.UNKNOWN);
    }

    @Test
    public void givenValidDate_whenCheckingIfMarketIsOpened_thenItIsOpened() {
        willReturn(true).given(period).contains(any(LocalDateTime.class));
        assertFalse(market.isMarketClose(VALID_DATE));
    }

    @Test
    public void givenInvalidDay_whenCheckingIfMarketIsOpened_thenItIsClosed() {
        assertTrue(market.isMarketClose(SUNDAY));
    }

    @Test
    public void givenInvalidTime_whenCheckingIfMarketOpened_thenItIsClosed() {
        LocalDateTime localDateTime = LocalDateTime.now();
        willReturn(false).given(period).contains(any(LocalDateTime.class));
        assertTrue(market.isMarketClose(localDateTime));
    }
}