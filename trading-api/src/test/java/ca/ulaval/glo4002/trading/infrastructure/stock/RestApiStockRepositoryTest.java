package ca.ulaval.glo4002.trading.infrastructure.stock;

import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.StockNotFoundException;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
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
public class RestApiStockRepositoryTest {

    private static final String UNEXISTING_MARKET = "fake market";
    private static final String UNEXISTING_SYMBOL = "fake symbol";
    private static final StockId UNEXISTING_STOCK_ID = new StockId(UNEXISTING_MARKET, UNEXISTING_SYMBOL);
    private static final String MARKET = "NASDAQ";
    private static final String SYMBOL = "MSFT";
    private static final StockId STOCK_ID = new StockId(MARKET, SYMBOL);
    private static final String RESPONSE = "{\n" +
            "  \"id\" : 4,\n" +
            "  \"symbol\" : \"MSFT\",\n" +
            "  \"type\" : \"common\",\n" +
            "  \"market\" : \"NASDAQ\",\n" +
            "  \"prices\" : [ {\n" +
            "    \"date\" : \"2015-01-01T05:00:00Z\",\n" +
            "    \"price\" : 157.54\n" +
            "  }, {\n" +
            "    \"date\" : \"2015-01-02T05:00:00Z\",\n" +
            "    \"price\" : 176.86\n" +
            "  }, {\n" +
            "    \"date\" : \"2015-01-03T05:00:00Z\",\n" +
            "    \"price\" : 168.03\n" +
            "  }, {\n" +
            "    \"date\" : \"2015-01-04T05:00:00Z\",\n" +
            "    \"price\" : 178.50\n" +
            "  }, {" +
            "    \"date\" : \"2018-12-24T05:00:00Z\",\n" +
            "    \"price\" : 119.43\n" +
            "  }, {\n" +
            "    \"date\" : \"2018-12-25T05:00:00Z\",\n" +
            "    \"price\" : 106.15\n" +
            "  }, {\n" +
            "    \"date\" : \"2018-12-26T05:00:00Z\",\n" +
            "    \"price\" : 87.44\n" +
            "  }, {\n" +
            "    \"date\" : \"2018-12-27T05:00:00Z\",\n" +
            "    \"price\" : 95.66\n" +
            "  }, {\n" +
            "    \"date\" : \"2018-12-28T05:00:00Z\",\n" +
            "    \"price\" : 84.59\n" +
            "  }, {\n" +
            "    \"date\" : \"2018-12-29T05:00:00Z\",\n" +
            "    \"price\" : 78.56\n" +
            "  }, {\n" +
            "    \"date\" : \"2018-12-30T05:00:00Z\",\n" +
            "    \"price\" : 69.86\n" +
            "  }, {\n" +
            "    \"date\" : \"2018-12-31T05:00:00Z\",\n" +
            "    \"price\" : 66.30\n" +
            "  } ]\n" +
            "}";

    private RestApiStockRepository restApiStockRepository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebTarget target;


    @Before
    public void setUp() {
        restApiStockRepository = new RestApiStockRepository(target);
    }

    @Test(expected = StockNotFoundException.class)
    public void givenAnUnexistingStockId_whenFindingStock_thenThrows() {
        String emptyResponse = "";
        when(target.path(UNEXISTING_MARKET).path(UNEXISTING_SYMBOL).request().get(String.class)).thenReturn(emptyResponse);
        restApiStockRepository.findByStockId(UNEXISTING_STOCK_ID);
    }

    @Test
    public void givenAnExistingStockId_whenFindingStock_thenRetrieve() {
        when(target.path(MARKET).path(SYMBOL).request().get(String.class)).thenReturn(RESPONSE);
        Stock stock = restApiStockRepository.findByStockId(STOCK_ID);
        assertEquals(MARKET, stock.getStockId().getMarket());
        assertEquals(SYMBOL, stock.getStockId().getSymbol());
    }

}