package ca.ulaval.glo4002.trading.rest.report.exceptions;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.CustomWebApplicationException;

import javax.ws.rs.core.Response;

public class ReportUnsupportedTypeException extends CustomWebApplicationException {

    private static final String ERROR = "REPORT_TYPE_UNSUPPORTED";
    private static final String DESCRIPTION = "report '%s' is not supported";

    public ReportUnsupportedTypeException(String type) {
        super(Response.Status.BAD_REQUEST, ERROR, String.format(DESCRIPTION, type));
    }

}
