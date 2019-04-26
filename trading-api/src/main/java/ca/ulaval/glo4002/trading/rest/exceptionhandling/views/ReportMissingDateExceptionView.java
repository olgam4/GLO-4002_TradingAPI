package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class ReportMissingDateExceptionView extends BadRequest {

    private static final String ERROR = "MISSING_DATE";
    private static final String DESCRIPTION = "date is missing";

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, DESCRIPTION);
    }

}
