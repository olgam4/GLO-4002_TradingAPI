package ca.ulaval.glo4002.trading.rest.exceptionhandling;

import javax.ws.rs.core.Response.Status;

public interface ExceptionView {

    Status getStatus();

    ExceptionMessage getMessage();

}
