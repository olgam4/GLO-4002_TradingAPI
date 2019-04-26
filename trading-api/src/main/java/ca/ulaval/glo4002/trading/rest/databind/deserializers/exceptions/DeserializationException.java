package ca.ulaval.glo4002.trading.rest.databind.deserializers.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.Response.Status;

public class DeserializationException extends JsonProcessingException {

    private final Status status;
    private final String error;
    private final String description;


    public DeserializationException(Status status, String error, String description) {
        super(error);
        this.status = status;
        this.error = error;
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

}
