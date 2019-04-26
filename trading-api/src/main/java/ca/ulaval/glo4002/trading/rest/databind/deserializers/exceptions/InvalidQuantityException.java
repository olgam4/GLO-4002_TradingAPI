package ca.ulaval.glo4002.trading.rest.databind.deserializers.exceptions;

import javax.ws.rs.core.Response;

public class InvalidQuantityException extends DeserializationException {

    private static final String ERROR = "INVALID_QUANTITY";
    private static final String DESCRIPTION = "quantity is invalid";

    public InvalidQuantityException() {
        super(Response.Status.BAD_REQUEST, ERROR, DESCRIPTION);
    }

}
