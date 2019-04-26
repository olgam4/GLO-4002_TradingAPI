package ca.ulaval.glo4002.trading.rest.databind.serializers;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AccountNumberSerializer extends JsonSerializer<AccountNumber> {

    @Override
    public void serialize(AccountNumber accountNumber, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String value = accountNumber.getValue();
        jsonGenerator.writeString(value);
    }

}
