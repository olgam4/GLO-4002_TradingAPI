package ca.ulaval.glo4002.trading.infrastructure.stock;

import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.StockNotFoundException;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;
import ca.ulaval.glo4002.trading.domain.stock.StockType;
import ca.ulaval.glo4002.trading.infrastructure.stock.dto.RestApiPriceDTO;
import ca.ulaval.glo4002.trading.infrastructure.stock.dto.RestApiStockDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestApiStockRepository implements StockRepository {

    private static final String URI = "http://localhost:8080";
    private static final String PATH = "stocks";

    private WebTarget target;

    public RestApiStockRepository() {
        this.target = ClientBuilder.newClient()
                .target(URI)
                .path(PATH);
    }

    RestApiStockRepository(WebTarget webTarget) {
        this.target = webTarget;
    }

    private String getResponse(StockId stockId) {
        String market = stockId.getMarket();
        String symbol = stockId.getSymbol();
        return target.path(market).path(symbol).request().get(String.class);
    }

    @Override
    public Stock findByStockId(StockId stockId) {
        String response = getResponse(stockId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            RestApiStockDTO restApiStockDTO = mapper.readValue(response, RestApiStockDTO.class);
            return createStock(restApiStockDTO);
        } catch (IOException e) {
            throw new StockNotFoundException(stockId);
        }
    }

    private Stock createStock(RestApiStockDTO restApiStockDTO) {
        StockType type = StockType.valueOf(restApiStockDTO.getType().toUpperCase());
        return new Stock(restApiStockDTO.getMarket(), restApiStockDTO.getSymbol(),
                getPrices(restApiStockDTO.getPrices()), type);
    }

    private Map<LocalDate, Money> getPrices(List<RestApiPriceDTO> pricesDTO) {
        Map<LocalDate, Money> prices = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        pricesDTO.forEach((priceDTO) -> {
            LocalDate date = LocalDate.parse(priceDTO.getDate(), dateFormatter);
            Money price = new Money(priceDTO.getPrice());
            prices.put(date, price);
        });
        return prices;
    }

}
