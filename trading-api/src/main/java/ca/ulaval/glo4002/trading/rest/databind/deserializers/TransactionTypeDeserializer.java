package ca.ulaval.glo4002.trading.rest.databind.deserializers;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.rest.databind.deserializers.exceptions.UnsupportedTransactionTypeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class TransactionTypeDeserializer extends JsonDeserializer<TransactionType> {

    @Override
    public TransactionType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String textValue = node.textValue();
        try {
            return TransactionType.valueOf(textValue);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedTransactionTypeException(textValue);
        }
    }

}
