package ca.ulaval.glo4002.trading.rest.report.exceptions;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.CustomWebApplicationException;

import javax.ws.rs.core.Response;

public class ReportInvalidYearException extends CustomWebApplicationException {

    private static final String ERROR = "INVALID_YEAR";
    private static final String DESCRIPTION = "the year is invalid";

    public ReportInvalidYearException() {
        super(Response.Status.BAD_REQUEST, ERROR, DESCRIPTION);
    }

}
