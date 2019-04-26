package ca.ulaval.glo4002.trading.rest.account;

import ca.ulaval.glo4002.trading.application.account.AccountApplicationService;
import ca.ulaval.glo4002.trading.application.account.AccountDTO;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.rest.account.views.requests.AccountRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private static final String ACCOUNT_NUMBER_STRING = "YL-420";
    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber(ACCOUNT_NUMBER_STRING);
    private static final AccountRequest ACCOUNT_REQUEST = new AccountRequest();
    private static final AccountDTO ACCOUNT_DTO = new AccountDTO();

    private AccountController accountController;

    @Mock
    private AccountApplicationService accountApplicationService;

    @Mock
    private AccountViewAssembler accountViewAssembler;

    @Before
    public void setUp() {
        ACCOUNT_DTO.setAccountNumber(ACCOUNT_NUMBER);
        accountController = new AccountController(accountApplicationService, accountViewAssembler);
        willReturn(ACCOUNT_DTO).given(accountViewAssembler).from(ACCOUNT_REQUEST);
        willReturn(ACCOUNT_NUMBER).given(accountApplicationService).createAccount(ACCOUNT_DTO);
    }

    @Test
    public void whenCreateAccount_thenAccountIsCreated() {
        accountController.postAccount(ACCOUNT_REQUEST);
        verify(accountApplicationService).createAccount(ACCOUNT_DTO);
    }

    @Test
    public void whenCreateAccount_thenResponseIsAsExpected() {
        Response response = accountController.postAccount(ACCOUNT_REQUEST);
        assertEquals(CREATED, response.getStatusInfo());
        assertEquals(String.format("/accounts/%s", ACCOUNT_NUMBER_STRING), response.getHeaderString("location"));
    }

    @Test
    public void whenGetAccount_thenAccountRetrieved() {
        accountController.getAccount(ACCOUNT_NUMBER);
        verify(accountApplicationService).getByAccountNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void whenGetAccount_thenResponseIsAsExpected() {
        Response response = accountController.getAccount(ACCOUNT_NUMBER);
        assertEquals(OK, response.getStatusInfo());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

}
