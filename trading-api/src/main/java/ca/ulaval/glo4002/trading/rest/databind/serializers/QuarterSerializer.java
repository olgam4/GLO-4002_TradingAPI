package ca.ulaval.glo4002.trading.rest.databind.serializers;

import ca.ulaval.glo4002.trading.domain.report.Quarter;
import ca.ulaval.glo4002.trading.domain.report.QuarterType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;

public class QuarterSerializer extends JsonSerializer<Quarter> {

    @Override
    public void serialize(Quarter quarter, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        LocalDateTime ending = quarter.getEnding();
        Year year = Year.from(ending);
        QuarterType quarterType = quarter.getQuarterType();
        String quarterString = String.format("%s-%s", year.toString(), quarterType.toString());
        jsonGenerator.writeString(quarterString);
    }

}
