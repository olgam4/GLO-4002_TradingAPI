package ca.ulaval.glo4002.trading.rest.transaction;

import ca.ulaval.glo4002.trading.application.transaction.TransactionApplicationService;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.rest.transaction.views.requests.TransactionRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    private static final String ACCOUNT_NUMBER_VALUE = "SC-9";
    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber(ACCOUNT_NUMBER_VALUE);
    private static final UUID TRANSACTION_NUMBER_VALUE = UUID.randomUUID();
    private static final TransactionNumber TRANSACTION_NUMBER = new TransactionNumber(TRANSACTION_NUMBER_VALUE);
    private static final TransactionRequest TRANSACTION_REQUEST = new TransactionRequest();
    private static final TransactionDTO TRANSACTION_DTO = new TransactionDTO();
    private static final String ACCOUNT_PATH = "/accounts/";
    private static final String TRANSACTION_PATH = "/transactions/";

    private TransactionController transactionController;

    @Mock
    TransactionApplicationService transactionApplicationService;

    @Mock
    TransactionViewAssembler transactionViewAssembler;

    @Before
    public void setUp() {
        willReturn(TRANSACTION_DTO).given(transactionViewAssembler).from(TRANSACTION_REQUEST);
        willReturn(TRANSACTION_NUMBER).given(transactionApplicationService).purchaseTransaction(any(AccountNumber.class), any(TransactionDTO.class));
        willReturn(TRANSACTION_NUMBER).given(transactionApplicationService).sellTransaction(any(AccountNumber.class), any(TransactionDTO.class));
        transactionController = new TransactionController(transactionApplicationService, transactionViewAssembler);
    }

    @Test
    public void givenAPurchaseTransaction_whenCreatingTransaction_thenPurchaseTransaction() {
        TRANSACTION_REQUEST.setType(TransactionType.BUY);
        transactionController.postTransaction(ACCOUNT_NUMBER, TRANSACTION_REQUEST);
        verify(transactionApplicationService).purchaseTransaction(ACCOUNT_NUMBER, TRANSACTION_DTO);
    }

    @Test
    public void givenASellTransaction_whenCreatingTransaction_thenSellTransaction() {
        TRANSACTION_REQUEST.setType(TransactionType.SELL);
        transactionController.postTransaction(ACCOUNT_NUMBER, TRANSACTION_REQUEST);
        verify(transactionApplicationService).sellTransaction(ACCOUNT_NUMBER, TRANSACTION_DTO);
    }

    @Test
    public void whenCreatingTransaction_thenResponseTypeIsAsExpected() {
        Response response = transactionController.postTransaction(ACCOUNT_NUMBER, TRANSACTION_REQUEST);
        String location = ACCOUNT_PATH + ACCOUNT_NUMBER.getValue() + TRANSACTION_PATH +
                TRANSACTION_NUMBER_VALUE.toString();
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
        assertEquals(location, response.getHeaderString("location"));
    }

    @Test
    public void whenRetrievingTransactionByNumber_thenResponseTypeIsAsExpected() {
        Response response = transactionController.getByTransactionNumber(ACCOUNT_NUMBER, TRANSACTION_NUMBER);
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    public void whenRetrievingTransactionByNumber_thenTransactionServiceIsCalled() {
        transactionController.getByTransactionNumber(ACCOUNT_NUMBER, TRANSACTION_NUMBER);
        verify(transactionApplicationService).getByTransactionNumber(ACCOUNT_NUMBER, TRANSACTION_NUMBER);
    }

}
