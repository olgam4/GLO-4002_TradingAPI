package ca.ulaval.glo4002.trading.rest.report.exceptions;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.CustomWebApplicationException;

import javax.ws.rs.core.Response;

public class ReportMissingDateException extends CustomWebApplicationException {

    private static final String ERROR = "MISSING_DATE";
    private static final String DESCRIPTION = "date is missing";

    public ReportMissingDateException() {
        super(Response.Status.BAD_REQUEST, ERROR, DESCRIPTION);
    }

}
