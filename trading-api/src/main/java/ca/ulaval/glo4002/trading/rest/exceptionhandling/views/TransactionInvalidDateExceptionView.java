package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class TransactionInvalidDateExceptionView extends BadRequest {

    private static final String ERROR = "INVALID_DATE";
    private static final String DESCRIPTION = "the transaction date is invalid";

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, DESCRIPTION);
    }

}
