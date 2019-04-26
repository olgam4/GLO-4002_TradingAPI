package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class InvalidAmountExceptionView extends BadRequest {

    private static final String ERROR = "INVALID_AMOUNT";
    private static final String DESCRIPTION = "credit amount cannot be lower than or equal to zero";

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, DESCRIPTION);
    }

}
