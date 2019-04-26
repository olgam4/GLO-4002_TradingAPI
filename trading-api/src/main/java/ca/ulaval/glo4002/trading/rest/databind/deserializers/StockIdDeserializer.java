package ca.ulaval.glo4002.trading.rest.databind.deserializers;

import ca.ulaval.glo4002.trading.domain.stock.StockId;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class StockIdDeserializer extends JsonDeserializer<StockId> {

    @Override
    public StockId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String market = node.get("market").textValue();
        String symbol = node.get("symbol").textValue();
        return new StockId(market, symbol);
    }

}
