package ca.ulaval.glo4002.trading.rest.databind.serializers;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TransactionNumberSerializer extends JsonSerializer<TransactionNumber> {

    @Override
    public void serialize(TransactionNumber transactionNumber, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String transactionNumberString = transactionNumber.getValue().toString();
        gen.writeString(transactionNumberString);
    }

}
