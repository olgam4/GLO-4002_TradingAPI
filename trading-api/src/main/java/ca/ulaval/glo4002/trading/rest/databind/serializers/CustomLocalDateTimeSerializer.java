package ca.ulaval.glo4002.trading.rest.databind.serializers;

import ca.ulaval.glo4002.trading.rest.databind.deserializers.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String date = localDateTime.format(CustomLocalDateTimeDeserializer.DATE_TIME_FORMATTER);
        jsonGenerator.writeString(date);
    }

}
