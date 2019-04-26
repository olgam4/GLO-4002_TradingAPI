package ca.ulaval.glo4002.trading.rest.report.exceptions;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.CustomWebApplicationException;

import javax.ws.rs.core.Response;

public class ReportMissingTypeException extends CustomWebApplicationException {

    private static final String ERROR = "MISSING_REPORT_TYPE";
    private static final String DESCRIPTION = "report type is missing";

    public ReportMissingTypeException() {
        super(Response.Status.BAD_REQUEST, ERROR, DESCRIPTION);
    }

}
