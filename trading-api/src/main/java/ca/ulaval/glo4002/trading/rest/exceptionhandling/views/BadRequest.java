package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionView;

import javax.ws.rs.core.Response.Status;

abstract class BadRequest implements ExceptionView {

    @Override
    public Status getStatus() {
        return Status.BAD_REQUEST;
    }

}
