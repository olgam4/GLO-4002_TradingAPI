package ca.ulaval.glo4002.trading.rest.databind.deserializers;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.UUID;

public class TransactionNumberDeserializer extends JsonDeserializer<TransactionNumber> {

    @Override
    public TransactionNumber deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return new TransactionNumber(UUID.fromString(node.asText()));
    }

}
