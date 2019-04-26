package ca.ulaval.glo4002.trading.rest.exceptionhandling;

import ca.ulaval.glo4002.trading.rest.databind.deserializers.exceptions.DeserializationException;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DeserializationExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    @Override
    public Response toResponse(JsonProcessingException jpe) {
        if (jpe.getCause() instanceof DeserializationException) {
            DeserializationException e = (DeserializationException) jpe.getCause();
            return Response
                    .status(e.getStatus())
                    .entity(new ExceptionMessage(e.getError(), e.getDescription()))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
        return Response
                .serverError()
                .entity(jpe.toString())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
