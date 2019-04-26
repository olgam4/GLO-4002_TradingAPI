package ca.ulaval.glo4002.trading.rest.databind.serializers;

import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class InvestorIdSerializer extends JsonSerializer<InvestorId> {

    @Override
    public void serialize(InvestorId investorId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        long investorIdLong = investorId.getValue();
        jsonGenerator.writeNumber(investorIdLong);
    }

}
