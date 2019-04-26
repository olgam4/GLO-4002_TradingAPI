package ca.ulaval.glo4002.trading.rest.exceptionhandling;

import ca.ulaval.glo4002.trading.domain.commons.TradingApiException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TradingApiExceptionMapper implements ExceptionMapper<TradingApiException> {

    private final ExceptionViewFactory exceptionViewFactory;

    public TradingApiExceptionMapper() {
        exceptionViewFactory = new ExceptionViewFactory();
    }

    @Override
    public Response toResponse(TradingApiException e) {
        ExceptionView exceptionView = exceptionViewFactory.createExceptionView(e);
        return Response
                .status(exceptionView.getStatus())
                .entity(exceptionView.getMessage())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
