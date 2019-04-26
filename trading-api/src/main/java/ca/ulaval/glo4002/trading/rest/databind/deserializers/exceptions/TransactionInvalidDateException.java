package ca.ulaval.glo4002.trading.rest.databind.deserializers.exceptions;

import javax.ws.rs.core.Response;

public class TransactionInvalidDateException extends DeserializationException {

    private static final String ERROR = "INVALID_DATE";
    private static final String DESCRIPTION = "the transaction date is invalid";

    public TransactionInvalidDateException() {
        super(Response.Status.BAD_REQUEST, ERROR, DESCRIPTION);
    }

}
