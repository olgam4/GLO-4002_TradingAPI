package ca.ulaval.glo4002.trading.rest.transaction;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.transaction.TransactionApplicationService;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.rest.account.AccountController;
import ca.ulaval.glo4002.trading.rest.transaction.views.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.rest.transaction.views.responses.TransactionResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path(AccountController.ACCOUNTS_PATH + "/" + AccountController.ACCOUNT_NUMBER_PATH_PARAM + "/" + TransactionController.TRANSACTIONS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

    public static final String TRANSACTIONS_PATH = "transactions";
    public static final String TRANSACTION_NUMBER_PARAM = "transactionNumber";
    public static final String TRANSACTION_NUMBER_PATH_PARAM = "{" + TRANSACTION_NUMBER_PARAM + "}";

    private final TransactionApplicationService transactionApplicationService;
    private final TransactionViewAssembler transactionViewAssembler;

    public TransactionController() {
        this(
                ServiceLocator.resolve(TransactionApplicationService.class),
                new TransactionViewAssembler()
        );
    }

    TransactionController(TransactionApplicationService transactionApplicationService,
                          TransactionViewAssembler transactionViewAssembler) {
        this.transactionApplicationService = transactionApplicationService;
        this.transactionViewAssembler = transactionViewAssembler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postTransaction(@PathParam(AccountController.ACCOUNT_NUMBER_PARAM) AccountNumber accountNumber,
                                    TransactionRequest transactionRequest) {
        TransactionDTO transactionDTO = transactionViewAssembler.from(transactionRequest);
        TransactionNumber transactionNumber;
        if (transactionRequest.getType() == TransactionType.BUY) {
            transactionNumber = transactionApplicationService.purchaseTransaction(accountNumber, transactionDTO);
        } else {
            transactionNumber = transactionApplicationService.sellTransaction(accountNumber, transactionDTO);
        }
        String accountNumberString = accountNumber.getValue();
        String transactionNumberString = transactionNumber.getValue().toString();
        URI location = UriBuilder.fromResource(AccountController.class)
                .path(accountNumberString)
                .path(TRANSACTIONS_PATH)
                .path(transactionNumberString)
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path(TRANSACTION_NUMBER_PATH_PARAM)
    public Response getByTransactionNumber(@PathParam(AccountController.ACCOUNT_NUMBER_PARAM) AccountNumber accountNumber,
                                           @PathParam(TRANSACTION_NUMBER_PARAM) TransactionNumber transactionNumber) {
        TransactionDTO transactionDTO = transactionApplicationService.getByTransactionNumber(accountNumber, transactionNumber);
        TransactionResponse transactionResponse = transactionViewAssembler.from(transactionDTO);
        return Response.ok(transactionResponse, MediaType.APPLICATION_JSON_TYPE).build();
    }

}
