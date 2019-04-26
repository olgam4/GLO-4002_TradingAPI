package ca.ulaval.glo4002.trading.rest.databind.deserializers.exceptions;

import javax.ws.rs.core.Response;

public class UnsupportedTransactionTypeException extends DeserializationException {

    private static final String ERROR = "UNSUPPORTED_TRANSACTION_TYPE";
    private static final String DESCRIPTION = "transaction '%s' is not supported";

    public UnsupportedTransactionTypeException(String transactionType) {
        super(Response.Status.BAD_REQUEST, ERROR, String.format(DESCRIPTION, transactionType));
    }

}
