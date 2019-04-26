package ca.ulaval.glo4002.trading.infrastructure.market;

import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.market.exceptions.MarketNotFoundException;
import ca.ulaval.glo4002.trading.infrastructure.market.dto.RestApiMarketDTO;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RestApiMarketRepository implements MarketRepository {

    private static final String URI = "http://localhost:8080";
    private static final String PATH = "markets";

    private WebTarget target;

    public RestApiMarketRepository() {
        this.target = ClientBuilder.newClient()
                .target(URI)
                .path(PATH);
    }

    RestApiMarketRepository(WebTarget webTarget) {
        this.target = webTarget;
    }

    private String getResponse(String market) {
        return target.path(market).request().get(String.class);
    }

    public Market findByMarket(String market) {
        String response = getResponse(market);
        ObjectMapper mapper = new ObjectMapper();
        try {
            RestApiMarketDTO restApiMarketDTO = mapper.readValue(response, RestApiMarketDTO.class);
            return createMarket(restApiMarketDTO);
        } catch (IOException e) {
            throw new MarketNotFoundException(market);
        }
    }

    private Market createMarket(RestApiMarketDTO restApiMarketDTO) {
        return new Market(restApiMarketDTO.getSymbol(), getMarketTimeOffset(restApiMarketDTO.getTimezone()),
                parseOpenHours(restApiMarketDTO.getOpenHours()), CurrencyMapper.convertMarketToCurrency(restApiMarketDTO.getSymbol()));
    }

    private ZoneOffset getMarketTimeOffset(String timezone) {
        String offset = timezone.replace("UTC", "");
        return ZoneOffset.of(offset);
    }

    private List<Period> parseOpenHours(List<String> openHoursDTO) {
        List<Period> openHours = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (String stringPeriod : openHoursDTO) {
            String[] slots = stringPeriod.split("-");
            String startTime = formatHours(slots[0]);
            String endTime = formatHours(slots[1]);
            LocalTime openingTime = LocalTime.parse(startTime, timeFormatter);
            LocalTime closingTime = LocalTime.parse(endTime, timeFormatter);
            LocalDate today = LocalDate.now();
            LocalDateTime openingDateTime = LocalDateTime.of(today, openingTime);
            LocalDateTime closingDateTime = LocalDateTime.of(today, closingTime);
            Period period = new Period(openingDateTime, closingDateTime);
            openHours.add(period);
        }
        return openHours;
    }

    private String formatHours(String time) {
        return StringUtils.leftPad(time, 5, '0');
    }

}
