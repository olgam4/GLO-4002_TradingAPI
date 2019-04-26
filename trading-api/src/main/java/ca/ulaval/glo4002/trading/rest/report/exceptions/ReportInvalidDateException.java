package ca.ulaval.glo4002.trading.rest.report.exceptions;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.CustomWebApplicationException;

import javax.ws.rs.core.Response;
import java.time.LocalDate;

public class ReportInvalidDateException extends CustomWebApplicationException {

    private static final String ERROR = "INVALID_DATE";
    private static final String DESCRIPTION = "date '%s' is invalid";

    public ReportInvalidDateException(String localDate) {
        super(Response.Status.BAD_REQUEST, ERROR, String.format(DESCRIPTION, localDate));
    }

    public ReportInvalidDateException(LocalDate localDate) {
        this(localDate.toString());
    }

}
