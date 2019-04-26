package ca.ulaval.glo4002.trading.rest.exceptionhandling;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CustomWebApplicationException extends WebApplicationException {

    public CustomWebApplicationException(Response.Status status, String error, String description) {
        super(Response
                .status(status)
                .entity(new ExceptionMessage(error, description))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build()
        );
    }

}
