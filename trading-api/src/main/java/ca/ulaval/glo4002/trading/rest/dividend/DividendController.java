package ca.ulaval.glo4002.trading.rest.dividend;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.dividend.DividendApplicationService;
import ca.ulaval.glo4002.trading.application.dividend.DividendDTO;
import ca.ulaval.glo4002.trading.rest.dividend.views.requests.DividendRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(DividendController.DIVIDENDS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class DividendController {

    public static final String DIVIDENDS_PATH = "/dividends";
    private final DividendApplicationService dividendApplicationService;
    private final DividendViewAssembler dividendViewAssembler;

    public DividendController() {
        this(
                ServiceLocator.resolve(DividendApplicationService.class),
                new DividendViewAssembler()
        );
    }

    DividendController(DividendApplicationService dividendApplicationService, DividendViewAssembler dividendViewAssembler) {
        this.dividendApplicationService = dividendApplicationService;
        this.dividendViewAssembler = dividendViewAssembler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDividend(DividendRequest dividendRequest) {
        DividendDTO dividendDTO = dividendViewAssembler.from(dividendRequest);
        dividendApplicationService.createDividend(dividendDTO);
        return Response.status(Response.Status.CREATED).build();
    }

}
