package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionView;

import javax.ws.rs.core.Response;

abstract class NotFound implements ExceptionView {

    @Override
    public Response.Status getStatus() {
        return Response.Status.NOT_FOUND;
    }

}
