package ca.ulaval.glo4002.trading.tests.account;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.account.AccountApplicationService;
import ca.ulaval.glo4002.trading.application.account.AccountDTO;
import ca.ulaval.glo4002.trading.application.account.CreditDTO;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountAlreadyOpenException;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.exceptions.InvalidAmountException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorType;
import ca.ulaval.glo4002.trading.tests.ErrorView;
import ca.ulaval.glo4002.trading.tests.TestContext;
import ca.ulaval.glo4002.trading.tests.TradingApplicationTest;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4002.trading.tests.Constants.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends TradingApplicationTest {

    @Mock
    private AccountApplicationService accountApplicationService;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ServiceLocator.reset();
        new TestContext().execute();
        ServiceLocator.register(AccountApplicationService.class, accountApplicationService);
    }/*

    @Test
    public void givenValidAccount_whenPost_thenCreated() {
        willReturn(ACCOUNT_NUMBER).given(accountApplicationService).createAccount(any());
        String validAccount = makeAccountRequest(VALID_CREDITS_VALUE);
        Response response = postAccount(validAccount);
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        String location = String.format("/accounts/%s", ACCOUNT_NUMBER_VALUE);
        assertEquals(location, response.getLocation().getPath());
    }

    @Test
    public void givenAlreadyOpenAccount_whenPost_thenBadRequest() {
        String validAccount = makeAccountRequest(VALID_CREDITS_VALUE);
        willThrow(new AccountAlreadyOpenException(INVESTOR_ID)).given(accountApplicationService).createAccount(any());

        Response response = postAccount(validAccount);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("ACCOUNT_ALREADY_OPEN", errorView.error);
        assertEquals(String.format("account already open for investor %d", INVESTOR_ID_VALUE), errorView.description);
    }

    @Test
    public void givenZeroCreditsAccount_whenPost_thenBadRequest() {
        invalidCredits(ZERO_CREDITS);
    }

    @Test
    public void givenNegativeCreditsAccount_whenPost_thenBadRequest() {
        invalidCredits(NEGATIVE_CREDITS);
    }

    private void invalidCredits(float negativeCredits) {
        String negativeCreditsAccount = makeAccountRequest(negativeCredits);
        willThrow(new InvalidAmountException()).given(accountApplicationService).createAccount(any());

        Response response = postAccount(negativeCreditsAccount);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_AMOUNT", errorView.error);
        assertEquals("credit amount cannot be lower than or equal to zero", errorView.description);
    }

    @Test
    public void givenValidAccountNumber_whenGet_thenOk() {
        AccountDTO accountDTO = getAccountDTO();
        willReturn(accountDTO).given(accountApplicationService).getByAccountNumber(ACCOUNT_NUMBER);

        Response response = getAccount(ACCOUNT_NUMBER_VALUE);
        String json = response.readEntity(String.class);
        AccountView accountView = AccountView.from(json);

        List<String> expectedKeys = new ArrayList<>();
        expectedKeys.add("accountNumber");
        expectedKeys.add("investorId");
        expectedKeys.add("investorProfile");
        expectedKeys.add("credits");
        JsonObject jsonObject = toJsonObject(json);
        assertEquals(expectedKeys, new ArrayList<>(jsonObject.keySet()));
        expectedKeys = new ArrayList<>();
        expectedKeys.add("type");
        expectedKeys.add("focusAreas");
        JsonObject investorProfile = jsonObject.getAsJsonObject("investorProfile");
        assertEquals(expectedKeys, new ArrayList<>(investorProfile.keySet()));

        assertEquals(OK.getStatusCode(), response.getStatus());
        assertEquals(ACCOUNT_NUMBER_VALUE, accountView.accountNumber);
        assertEquals(INVESTOR_ID_VALUE, accountView.investorId);
        assertEquals(InvestorType.CONSERVATIVE.toString(), accountView.investorProfile.type);
        assertTrue(accountView.investorProfile.focusAreas.isEmpty());
        assertEquals(VALID_CREDITS_VALUE, accountView.credits, DELTA);
    }
*/
    @Test
    public void givenInvalidAccountNumber_whenGet_thenNotFound() {
        AccountNotFoundException notFound = new AccountNotFoundException(INVALID_ACCOUNT_NUMBER);
        willThrow(notFound).given(accountApplicationService).getByAccountNumber(INVALID_ACCOUNT_NUMBER);

        Response response = getAccount(INVALID_ACCOUNT_NUMBER_VALUE);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("ACCOUNT_NOT_FOUND", errorView.error);
        assertEquals(String.format("account with number %s not found", INVALID_ACCOUNT_NUMBER_VALUE), errorView.description);
    }

    private Response postAccount(String account) {
        return target("accounts").request().post(Entity.json(account));
    }

    private Response getAccount(String accountNumberValue) {
        return target("accounts").path(accountNumberValue).request().get();
    }

    private String makeAccountRequest(float credits) {
        JsonObject account = new JsonObject();
        account.addProperty("investorId", INVESTOR_ID_VALUE);
        account.addProperty("investorName", NAME);
        account.addProperty("email", EMAIL);
        account.addProperty("credits", credits);
        return account.toString();
    }

    private AccountDTO getAccountDTO() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(ACCOUNT_NUMBER);
        accountDTO.setInvestorId(INVESTOR_ID);
        accountDTO.setInvestorType(InvestorType.CONSERVATIVE);
        accountDTO.setFocusAreas(new ArrayList<>());
        List<CreditDTO> creditDTOList = new ArrayList<>();
        CreditDTO creditDTO = new CreditDTO();
        creditDTOList.add(creditDTO);
        accountDTO.setCredits(creditDTOList);
        return accountDTO;
    }
}