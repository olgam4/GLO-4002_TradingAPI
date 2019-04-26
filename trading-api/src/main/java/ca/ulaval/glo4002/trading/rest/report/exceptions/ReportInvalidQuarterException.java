package ca.ulaval.glo4002.trading.rest.report.exceptions;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.CustomWebApplicationException;

import javax.ws.rs.core.Response;

public class ReportInvalidQuarterException extends CustomWebApplicationException {

    private static final String ERROR = "INVALID_QUARTER";
    private static final String DESCRIPTION = "the quarter is invalid";

    public ReportInvalidQuarterException() {
        super(Response.Status.BAD_REQUEST, ERROR, DESCRIPTION);
    }

}