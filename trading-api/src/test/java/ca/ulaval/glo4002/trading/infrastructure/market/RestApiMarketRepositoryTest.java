package ca.ulaval.glo4002.trading.infrastructure.market;


import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.exceptions.MarketNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestApiMarketRepositoryTest {

    private static final String UNEXISTING_MARKET = "fake market";
    private static final String MARKET = "NASDAQ";
    private static final String RESPONSE = "{\n" +
            "    \"symbol\": \"NASDAQ\",\n" +
            "    \"openHours\": [\n" +
            "        \"9:30-12:00\",\n" +
            "        \"13:00-16:30\"\n" +
            "    ],\n" +
            "    \"timezone\": \"UTC-04:00\"\n" +
            "}";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebTarget target;

    private RestApiMarketRepository restApiMarketRepository;

    @Before
    public void setUp() {
        restApiMarketRepository = new RestApiMarketRepository(target);
    }

    @Test(expected = MarketNotFoundException.class)
    public void givenAnUnexistingMarket_whenFindingMarket_thenThrows() {
        when(target.path(UNEXISTING_MARKET).request().get(String.class)).thenReturn("");
        restApiMarketRepository.findByMarket(UNEXISTING_MARKET);
    }

    @Test
    public void givenAnExistingMarket_whenFindingMarket_thenRetrieve() {
        when(target.path(MARKET).request().get(String.class)).thenReturn(RESPONSE);
        Market market = restApiMarketRepository.findByMarket(MARKET);
        assertEquals(MARKET, market.getMarketSymbol());
    }


}