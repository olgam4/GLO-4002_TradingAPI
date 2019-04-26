package ca.ulaval.glo4002.trading.rest.account;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.account.AccountApplicationService;
import ca.ulaval.glo4002.trading.application.account.AccountDTO;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.rest.account.views.requests.AccountRequest;
import ca.ulaval.glo4002.trading.rest.account.views.responses.AccountResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path(AccountController.ACCOUNTS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    public static final String ACCOUNTS_PATH = "/accounts";
    public static final String ACCOUNT_NUMBER_PARAM = "accountNumber";
    public static final String ACCOUNT_NUMBER_PATH_PARAM = "{" + ACCOUNT_NUMBER_PARAM + "}";

    private final AccountApplicationService accountApplicationService;
    private final AccountViewAssembler accountViewAssembler;

    public AccountController() {
        this(
                ServiceLocator.resolve(AccountApplicationService.class),
                new AccountViewAssembler()
        );
    }

    AccountController(AccountApplicationService accountApplicationService, AccountViewAssembler accountViewAssembler) {
        this.accountApplicationService = accountApplicationService;
        this.accountViewAssembler = accountViewAssembler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAccount(AccountRequest accountRequest) {
        AccountDTO accountDTO = accountViewAssembler.from(accountRequest);
        AccountNumber accountNumber = accountApplicationService.createAccount(accountDTO);
        String accountNumberString = accountNumber.getValue();
        URI location = UriBuilder.fromResource(AccountController.class)
                .path(accountNumberString)
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path(ACCOUNT_NUMBER_PATH_PARAM)
    public Response getAccount(@PathParam(ACCOUNT_NUMBER_PARAM) AccountNumber accountNumber) {
        AccountDTO accountDTO = accountApplicationService.getByAccountNumber(accountNumber);
        AccountResponse accountResponse = accountViewAssembler.from(accountDTO);
        return Response.ok(accountResponse, MediaType.APPLICATION_JSON_TYPE).build();
    }

}