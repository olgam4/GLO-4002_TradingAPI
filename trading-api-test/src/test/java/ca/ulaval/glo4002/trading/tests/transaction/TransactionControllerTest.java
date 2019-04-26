package ca.ulaval.glo4002.trading.tests.transaction;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.transaction.TransactionApplicationService;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.*;
import ca.ulaval.glo4002.trading.domain.market.exceptions.MarketClosedException;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
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
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest extends TradingApplicationTest {

    @Mock
    private TransactionApplicationService transactionApplicationService;

    public static TransactionDTO getTransactionBuyDTO() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionNumber(TRANSACTION_NUMBER_BUY);
        transactionDTO.setType(TransactionType.BUY);
        transactionDTO.setDate(VALID_DATE);
        transactionDTO.setFees(VALID_MONEY);
        transactionDTO.setStockId(STOCK_ID);
        transactionDTO.setPrice(VALID_MONEY);
        transactionDTO.setQuantity(VALID_QUANTITY);
        return transactionDTO;
    }

    public static TransactionDTO getTransactionSellDTO() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionNumber(TRANSACTION_NUMBER_SELL);
        transactionDTO.setType(TransactionType.SELL);
        transactionDTO.setDate(VALID_DATE);
        transactionDTO.setFees(VALID_MONEY);
        transactionDTO.setStockId(STOCK_ID);
        transactionDTO.setPrice(VALID_MONEY);
        transactionDTO.setQuantity(VALID_QUANTITY);
        return transactionDTO;
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ServiceLocator.reset();
        new TestContext().execute();
        ServiceLocator.register(TransactionApplicationService.class, transactionApplicationService);
    }

    @Test
    public void givenValidTransactionBuy_whenPost_thenCreated() {
        willReturn(TRANSACTION_NUMBER_BUY).given(transactionApplicationService).purchaseTransaction(any(), any());
        String validTransactionBuy = makeTransactionBuyRequest(BUY, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, null);
        Response response = postTransaction(validTransactionBuy);
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        String location = String.format("/accounts/%s/transactions/%s",
                ACCOUNT_NUMBER_VALUE, TRANSACTION_NUMBER_BUY.getValue().toString());
        assertEquals(location, response.getLocation().getPath());
    }

    @Test
    public void givenInvalidAccountNumberBuy_whenGet_thenNotFound() {
        AccountNotFoundException notFound = new AccountNotFoundException(INVALID_ACCOUNT_NUMBER);
        willThrow(notFound).given(transactionApplicationService).purchaseTransaction(any(), any());
        String accountNotFoundTransactionRequest = makeTransactionBuyRequest(BUY, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, null);

        Response response = postTransaction(accountNotFoundTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("ACCOUNT_NOT_FOUND", errorView.error);
        assertEquals(String.format("account with number %s not found", INVALID_ACCOUNT_NUMBER_VALUE), errorView.description);
    }

    @Test
    public void givenUnsupportedTransactionType_whenPost_thenBadRequest() {
        String invalidTypeTransactionRequest = makeTransactionBuyRequest(UNSUPPORTED_TYPE, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, null);

        Response response = postTransaction(invalidTypeTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("UNSUPPORTED_TRANSACTION_TYPE", errorView.error);
        assertEquals(String.format("transaction '%s' is not supported", UNSUPPORTED_TYPE), errorView.description);
    }

    @Test
    public void givenNotEnoughCreditsBuy_whenPost_thenBadRequest() {
        NotEnoughCreditsBuyException exception = new NotEnoughCreditsBuyException(TRANSACTION_NUMBER_BUY);
        willThrow(exception).given(transactionApplicationService).purchaseTransaction(any(), any());
        String invalidTypeTransactionRequest = makeTransactionBuyRequest(BUY, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, null);

        Response response = postTransaction(invalidTypeTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertTransactionError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("NOT_ENOUGH_CREDITS", errorView.error);
        assertEquals("not enough credits in wallet", errorView.description);
        assertEquals(TRANSACTION_NUMBER_BUY_VALUE_STRING, errorView.transactionNumber);
    }

    @Test
    public void givenZeroQuantity_whenPost_thenBadRequest() {
        String zeroQuantityTransactionRequest = makeTransactionBuyRequest(BUY, VALID_DATE_STRING, STOCK_ID, 0, null);

        Response response = postTransaction(zeroQuantityTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_QUANTITY", errorView.error);
        assertEquals("quantity is invalid", errorView.description);
    }

    @Test
    public void givenNegativeQuantity_whenPost_thenBadRequest() {
        String negativeQuantityTransactionRequest = makeTransactionBuyRequest(BUY, VALID_DATE_STRING, STOCK_ID, -1, null);

        Response response = postTransaction(negativeQuantityTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_QUANTITY", errorView.error);
        assertEquals("quantity is invalid", errorView.description);
    }

    @Test
    public void givenStockNotFoundBuy_whenPost_thenBadRequest() {
        StockNotFoundException exception = new StockNotFoundException(INVALID_STOCK_ID);
        willThrow(exception).given(transactionApplicationService).purchaseTransaction(any(), any());
        String stockNotFoundTransactionRequest = makeTransactionBuyRequest(BUY, VALID_DATE_STRING, INVALID_STOCK_ID, VALID_QUANTITY, null);

        Response response = postTransaction(stockNotFoundTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("STOCK_NOT_FOUND", errorView.error);
        assertEquals(String.format("stock '%s:%s' not found", INVALID_SYMBOL, INVALID_MARKET), errorView.description);
    }

    @Test
    public void givenInvalidDate_whenPost_thenBadRequest() {
        String invalidDateTransaction = makeTransactionBuyRequest(BUY, INVALID_DATE, STOCK_ID, VALID_QUANTITY, null);

        Response response = postTransaction(invalidDateTransaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_DATE", errorView.error);
        assertEquals("the transaction date is invalid", errorView.description);
    }

    @Test
    public void givenDateWithoutPriceBuy_whenPost_thenBadRequest() {
        TransactionInvalidDateException exception = new TransactionInvalidDateException();
        willThrow(exception).given(transactionApplicationService).purchaseTransaction(any(), any());
        String invalidDateTransaction = makeTransactionBuyRequest(BUY, DATE_WITHOUT_PRICE_STRING, STOCK_ID, VALID_QUANTITY, null);

        Response response = postTransaction(invalidDateTransaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_DATE", errorView.error);
        assertEquals("the transaction date is invalid", errorView.description);
    }

    @Test
    public void givenMarketClosedBuy_whenPost_thenBadRequest() {
        MarketClosedException exception = new MarketClosedException(MARKET, TRANSACTION_NUMBER_BUY);
        willThrow(exception).given(transactionApplicationService).purchaseTransaction(any(), any());
        String transaction = makeTransactionBuyRequest(BUY, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, null);

        Response response = postTransaction(transaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertTransactionError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("MARKET_CLOSED", errorView.error);
        assertEquals(String.format("market '%s' is closed", MARKET), errorView.description);
        assertEquals(TRANSACTION_NUMBER_BUY_VALUE_STRING, errorView.transactionNumber);
    }

    @Test
    public void givenValidTransactionBuyNumber_whenGet_thenOk() {
        TransactionDTO transactionDTO = getTransactionBuyDTO();
        willReturn(transactionDTO).given(transactionApplicationService).getByTransactionNumber(ACCOUNT_NUMBER, TRANSACTION_NUMBER_BUY);

        Response response = getTransaction(TRANSACTION_NUMBER_BUY);
        String json = response.readEntity(String.class);
        TransactionView transactionView = TransactionView.from(json);

        List<String> expectedKeys = new ArrayList<>();
        expectedKeys.add("transactionNumber");
        expectedKeys.add("type");
        expectedKeys.add("date");
        expectedKeys.add("fees");
        expectedKeys.add("stock");
        expectedKeys.add("purchasedPrice");
        expectedKeys.add("quantity");
        JsonObject jsonObject = toJsonObject(json);
        assertEquals(expectedKeys, new ArrayList<>(jsonObject.keySet()));
        expectedKeys = new ArrayList<>();
        expectedKeys.add("market");
        expectedKeys.add("symbol");
        JsonObject stock = jsonObject.getAsJsonObject("stock");
        assertEquals(expectedKeys, new ArrayList<>(stock.keySet()));

        assertEquals(OK.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals(TRANSACTION_NUMBER_BUY_VALUE_STRING, transactionView.transactionNumber);
        assertEquals(BUY, transactionView.type);
        assertEquals(VALID_DATE_STRING, transactionView.date);
        assertEquals(VALID_CREDITS_VALUE, transactionView.fees, DELTA);
        assertEquals(MARKET, transactionView.stock.market);
        assertEquals(SYMBOL, transactionView.stock.symbol);
        assertEquals(VALID_CREDITS_VALUE, transactionView.purchasedPrice, DELTA);
        assertEquals(VALID_QUANTITY, transactionView.quantity);
    }

    @Test
    public void givenInvalidAccountNumberSell_whenGet_thenNotFound() {
        AccountNotFoundException notFound = new AccountNotFoundException(INVALID_ACCOUNT_NUMBER);
        willThrow(notFound).given(transactionApplicationService).sellTransaction(any(), any());
        String accountNotFoundTransactionRequest = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);

        Response response = postTransaction(accountNotFoundTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("ACCOUNT_NOT_FOUND", errorView.error);
        assertEquals(String.format("account with number %s not found", INVALID_ACCOUNT_NUMBER_VALUE), errorView.description);
    }

    @Test
    public void givenInvalidTransactionNumber_whenGet_thenNotFound() {
        TransactionNotFoundException exception = new TransactionNotFoundException(INVALID_TRANSACTION_NUMBER);
        willThrow(exception).given(transactionApplicationService).getByTransactionNumber(ACCOUNT_NUMBER, TRANSACTION_NUMBER_SELL);

        Response response = getTransaction(TRANSACTION_NUMBER_SELL);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("TRANSACTION_NOT_FOUND", errorView.error);
        assertEquals(String.format("transaction with number %s not found", INVALID_TRANSACTION_NUMBER_VALUE_STRING), errorView.description);
    }

    @Test
    public void givenValidTransactionSell_whenPost_thenCreated() {
        willReturn(TRANSACTION_NUMBER_SELL).given(transactionApplicationService).sellTransaction(any(), any());
        String validTransactionBuy = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);
        Response response = postTransaction(validTransactionBuy);
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        String location = String.format("/accounts/%s/transactions/%s",
                ACCOUNT_NUMBER_VALUE, TRANSACTION_NUMBER_SELL_VALUE_STRING);
        assertEquals(location, response.getLocation().getPath());
    }

    @Test
    public void givenStockNotFoundSell_whenPost_thenBadRequest() {
        StockNotFoundException exception = new StockNotFoundException(INVALID_STOCK_ID);
        willThrow(exception).given(transactionApplicationService).sellTransaction(any(), any());
        String stockNotFoundTransactionRequest = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, INVALID_STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);

        Response response = postTransaction(stockNotFoundTransactionRequest);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("STOCK_NOT_FOUND", errorView.error);
        assertEquals(String.format("stock '%s:%s' not found", INVALID_SYMBOL, INVALID_MARKET), errorView.description);
    }

    @Test
    public void givenInvalidTransactionNumberSell_whenPost_thenBadRequest() {
        InvalidTransactionNumberException exception = new InvalidTransactionNumberException(INVALID_TRANSACTION_NUMBER);
        willThrow(exception).given(transactionApplicationService).sellTransaction(any(), any());
        String transaction = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, INVALID_TRANSACTION_NUMBER_VALUE_STRING);

        Response response = postTransaction(transaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertTransactionError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_TRANSACTION_NUMBER", errorView.error);
        assertEquals("the transaction number is invalid", errorView.description);
        assertEquals(INVALID_TRANSACTION_NUMBER_VALUE_STRING, errorView.transactionNumber);
    }

    @Test
    public void givenNotEnoughStock_whenPost_thenBadRequest() {
        NotEnoughStockException exception = new NotEnoughStockException(TRANSACTION_NUMBER_SELL, INVALID_STOCK_ID);
        willThrow(exception).given(transactionApplicationService).sellTransaction(any(), any());
        String transaction = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, INVALID_STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);

        Response response = postTransaction(transaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertTransactionError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("NOT_ENOUGH_STOCK", errorView.error);
        assertEquals(String.format("not enough stock '%s:%s'", INVALID_SYMBOL, INVALID_MARKET), errorView.description);
        assertEquals(TRANSACTION_NUMBER_SELL_VALUE_STRING, errorView.transactionNumber);
    }

    @Test
    public void givenDateWithoutPriceSell_whenPost_thenBadRequest() {
        TransactionInvalidDateException exception = new TransactionInvalidDateException();
        willThrow(exception).given(transactionApplicationService).sellTransaction(any(), any());
        String invalidDateTransaction = makeTransactionBuyRequest(SELL, DATE_WITHOUT_PRICE_STRING, STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);

        Response response = postTransaction(invalidDateTransaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_DATE", errorView.error);
        assertEquals("the transaction date is invalid", errorView.description);
    }

    @Test
    public void givenNotEnoughCreditsSell_whenPost_thenBadRequest() {
        NotEnoughCreditsSellException exception = new NotEnoughCreditsSellException(TRANSACTION_NUMBER_SELL);
        willThrow(exception).given(transactionApplicationService).sellTransaction(any(), any());
        String transaction = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);

        Response response = postTransaction(transaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertTransactionError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("NOT_ENOUGH_CREDITS", errorView.error);
        assertEquals("not enough credits to pay the transaction fee", errorView.description);
        assertEquals(TRANSACTION_NUMBER_SELL_VALUE_STRING, errorView.transactionNumber);
    }

    @Test
    public void givenStockParametersDontMatch_whenPost_thenBadRequest() {
        StockParametersDontMatchException exception = new StockParametersDontMatchException(TRANSACTION_NUMBER_SELL);
        willThrow(exception).given(transactionApplicationService).sellTransaction(any(), any());
        String transaction = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);

        Response response = postTransaction(transaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertTransactionError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("STOCK_PARAMETERS_DONT_MATCH", errorView.error);
        assertEquals("stock parameters don't match existing ones", errorView.description);
        assertEquals(TRANSACTION_NUMBER_SELL_VALUE_STRING, errorView.transactionNumber);
    }

    @Test
    public void givenMarketClosedSell_whenPost_thenBadRequest() {
        MarketClosedException exception = new MarketClosedException(MARKET, TRANSACTION_NUMBER_SELL);
        willThrow(exception).given(transactionApplicationService).sellTransaction(any(), any());
        String transaction = makeTransactionBuyRequest(SELL, VALID_DATE_STRING, STOCK_ID, VALID_QUANTITY, TRANSACTION_NUMBER_BUY_VALUE_STRING);

        Response response = postTransaction(transaction);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertTransactionError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("MARKET_CLOSED", errorView.error);
        assertEquals(String.format("market '%s' is closed", MARKET), errorView.description);
        assertEquals(TRANSACTION_NUMBER_SELL_VALUE_STRING, errorView.transactionNumber);
    }

    @Test
    public void givenValidTransactionSellNumber_whenGet_thenOk() {
        TransactionDTO transactionDTO = getTransactionSellDTO();
        willReturn(transactionDTO).given(transactionApplicationService).getByTransactionNumber(ACCOUNT_NUMBER, TRANSACTION_NUMBER_SELL);

        Response response = getTransaction(TRANSACTION_NUMBER_SELL);
        String json = response.readEntity(String.class);
        TransactionView transactionView = TransactionView.from(json);

        List<String> expectedKeys = new ArrayList<>();
        expectedKeys.add("transactionNumber");
        expectedKeys.add("type");
        expectedKeys.add("date");
        expectedKeys.add("fees");
        expectedKeys.add("stock");
        expectedKeys.add("priceSold");
        expectedKeys.add("quantity");
        JsonObject jsonObject = toJsonObject(json);
        assertEquals(expectedKeys, new ArrayList<>(jsonObject.keySet()));
        expectedKeys = new ArrayList<>();
        expectedKeys.add("market");
        expectedKeys.add("symbol");
        JsonObject stock = jsonObject.getAsJsonObject("stock");
        assertEquals(expectedKeys, new ArrayList<>(stock.keySet()));

        assertEquals(OK.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals(TRANSACTION_NUMBER_SELL_VALUE_STRING, transactionView.transactionNumber);
        assertEquals(SELL, transactionView.type);
        assertEquals(VALID_DATE_STRING, transactionView.date);
        assertEquals(VALID_CREDITS_VALUE, transactionView.fees, DELTA);
        assertEquals(MARKET, transactionView.stock.market);
        assertEquals(SYMBOL, transactionView.stock.symbol);
        assertEquals(VALID_CREDITS_VALUE, transactionView.priceSold, DELTA);
        assertEquals(VALID_QUANTITY, transactionView.quantity);
    }

    private Response postTransaction(String json) {
        return target("accounts").path(ACCOUNT_NUMBER_VALUE)
                .path("transactions").request().post(Entity.json(json));
    }

    private Response getTransaction(TransactionNumber transactionNumber) {
        return target("accounts").path(ACCOUNT_NUMBER_VALUE).path("transactions")
                .path(transactionNumber.getValue().toString()).request().get();
    }

    private String makeTransactionBuyRequest(String type, String date, StockId stockId, long quantity, String transactionNumber) {
        JsonObject transaction = new JsonObject();
        transaction.addProperty("type", type);
        transaction.addProperty("transactionNumber", transactionNumber);
        transaction.addProperty("date", date);
        JsonObject stock = new JsonObject();
        stock.addProperty("market", stockId.getMarket());
        stock.addProperty("symbol", stockId.getSymbol());
        transaction.add("stock", stock);
        transaction.addProperty("quantity", quantity);
        return transaction.toString();
    }
}