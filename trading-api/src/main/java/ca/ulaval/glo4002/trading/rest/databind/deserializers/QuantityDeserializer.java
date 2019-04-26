package ca.ulaval.glo4002.trading.rest.databind.deserializers;

import ca.ulaval.glo4002.trading.rest.databind.deserializers.exceptions.InvalidQuantityException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class QuantityDeserializer extends JsonDeserializer<Long> {

    private static final int MINIMUM_QUANTITY = 0;

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        long quantity = node.longValue();
        if (quantity <= MINIMUM_QUANTITY) {
            throw new InvalidQuantityException();
        }
        return quantity;
    }

}
