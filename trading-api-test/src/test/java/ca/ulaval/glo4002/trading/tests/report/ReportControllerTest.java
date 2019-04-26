package ca.ulaval.glo4002.trading.tests.report;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.account.CreditDTO;
import ca.ulaval.glo4002.trading.application.dividend.DividendPaymentDTO;
import ca.ulaval.glo4002.trading.application.report.DailyReportDTO;
import ca.ulaval.glo4002.trading.application.report.ReportApplicationService;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.tests.ErrorView;
import ca.ulaval.glo4002.trading.tests.TestContext;
import ca.ulaval.glo4002.trading.tests.TradingApplicationTest;
import ca.ulaval.glo4002.trading.tests.transaction.TransactionControllerTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
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
public class ReportControllerTest extends TradingApplicationTest {

    @Mock
    private ReportApplicationService reportApplicationService;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ServiceLocator.reset();
        new TestContext().execute();
        ServiceLocator.register(ReportApplicationService.class, reportApplicationService);
    }
/*
    @Test
    public void givenValidAccountNumberTypeAndDate_whenGetDaily_thenCreated() {
        DailyReportDTO dailyReportDTO = getDailyReportDTO();
        willReturn(dailyReportDTO).given(reportApplicationService).generateDailyReport(any(), any());

        Response response = getDailyReport(ACCOUNT_NUMBER_VALUE, DAILY, VALID_DATE_STRING_REPORT);
        String json = response.readEntity(String.class);
        DailyReportView dailyReportView = DailyReportView.from(json);

        List<String> expectedKeys = new ArrayList<>();
        expectedKeys.add("date");
        expectedKeys.add("credits");
        expectedKeys.add("portfolioValue");
        expectedKeys.add("transactions");
        expectedKeys.add("stocks");
        JsonObject jsonObject = toJsonObject(json);
        assertEquals(expectedKeys, new ArrayList<>(jsonObject.keySet()));
        expectedKeys = new ArrayList<>();
        expectedKeys.add("market");
        expectedKeys.add("symbol");
        expectedKeys.add("marketprice");
        expectedKeys.add("dividends");
        JsonArray stocks = jsonObject.getAsJsonArray("stocks");
        for (JsonElement dividend : stocks) {
            JsonObject dividendPayment = toJsonObject(dividend.toString());
            assertEquals(expectedKeys, new ArrayList<>(dividendPayment.keySet()));
        }

        assertEquals(OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals(END_OF_THE_DAY_DATE_STRING, dailyReportView.date);
        assertEquals(VALID_CREDITS_VALUE, dailyReportView.credits, DELTA);
        assertEquals(VALID_CREDITS_VALUE, dailyReportView.portfolioValue, DELTA);
        assertEquals(3, dailyReportView.transactions.size());
        assertEquals(2, dailyReportView.stocks.size());
        DailyReportView.DividendPaymentView firstDividendPaymentView = dailyReportView.stocks.get(0);
        assertEquals(MARKET, firstDividendPaymentView.market);
        assertEquals(SYMBOL, firstDividendPaymentView.symbol);
        assertEquals(VALID_CREDITS_VALUE, firstDividendPaymentView.marketprice, DELTA);
        assertEquals(VALID_CREDITS_VALUE, firstDividendPaymentView.dividends, DELTA);
    }
*/
    @Test
    public void givenInvalidAccountNumber_whenGetDaily_thenNotFound() {
        AccountNotFoundException notFound = new AccountNotFoundException(INVALID_ACCOUNT_NUMBER);
        willThrow(notFound).given(reportApplicationService).generateDailyReport(any(), any());

        Response response = getDailyReport(INVALID_ACCOUNT_NUMBER_VALUE, DAILY, VALID_DATE_STRING_REPORT);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("ACCOUNT_NOT_FOUND", errorView.error);
        assertEquals(String.format("account with number %s not found", INVALID_ACCOUNT_NUMBER_VALUE), errorView.description);
    }

    @Test
    public void givenReportTypeUnsupported_whenGetDaily_thenBadRequest() {
        Response response = getDailyReport(ACCOUNT_NUMBER_VALUE, INVALID_REPORT_TYPE, VALID_DATE_STRING_REPORT);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("REPORT_TYPE_UNSUPPORTED", errorView.error);
        assertEquals(String.format("report '%s' is not supported", INVALID_REPORT_TYPE), errorView.description);
    }

    @Test
    public void givenMissingDate_whenGetDaily_thenBadRequest() {
        Response response = getDailyReport(ACCOUNT_NUMBER_VALUE, DAILY, MISSING_DATE);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("MISSING_DATE", errorView.error);
        assertEquals("date is missing", errorView.description);
    }

    @Test
    public void givenMissingReportType_whenGetDaily_thenBadRequest() {
        Response response = getDailyReport(ACCOUNT_NUMBER_VALUE, MISSING_REPORT_TYPE, VALID_DATE_STRING_REPORT);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("MISSING_REPORT_TYPE", errorView.error);
        assertEquals("report type is missing", errorView.description);
    }

    @Test
    public void givenInvalidDate_whenGetDaily_thenBadRequest() {
        invalidDateString(INVALID_DATE_STRING_REPORT);
    }

    @Test
    public void givenTodayDate_whenGetDaily_thenBadRequest() {
        invalidDate(TODAY_DATE);
    }

    @Test
    public void givenDateInFuture_whenGetDaily_thenBadRequest() {
        invalidDate(FUTURE_DATE);
    }

    private void invalidDate(LocalDate invalidDate) {
        String invalidDateString = invalidDate.toString();

        invalidDateString(invalidDateString);
    }

    private void invalidDateString(String invalidDateString) {
        Response response = getDailyReport(ACCOUNT_NUMBER_VALUE, DAILY, invalidDateString);
        String json = response.readEntity(String.class);
        ErrorView errorView = ErrorView.from(json);

        assertBaseError(json);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals("INVALID_DATE", errorView.error);
        assertEquals(String.format("date '%s' is invalid", invalidDateString), errorView.description);
    }

//    @Test
//    public void givenValidAccountNumberTypeAndYearAndQuarter_whenGetQuarterly_thenCreated() {
//        QuarterlyReportDTO quarterlyReportDTO = getQuarterlyReportDTO();
//        willReturn(quarterlyReportDTO).given(reportApplicationService).generateStockMarketReport(any(), any(), any(), any());
//
//    }

    private Response getDailyReport(String accountNumberValue, String type, String date) {
        return target().path("accounts").path(accountNumberValue).path("reports")
                .queryParam("type", type)
                .queryParam("date", date)
                .request().get();
    }

    private DailyReportDTO getDailyReportDTO() {
        DailyReportDTO reportDTO = new DailyReportDTO();
        CreditDTO creditDTO = new CreditDTO();
        List<CreditDTO> creditDTOS = new ArrayList<>();
        creditDTOS.add(creditDTO);
        reportDTO.setDate(END_OF_THE_DAY_DATE);
        reportDTO.setBalance(creditDTOS);
        reportDTO.setPortfolioValue(VALID_MONEY);
        ArrayList<TransactionDTO> transactionDTOS = new ArrayList<>();
        transactionDTOS.add(TransactionControllerTest.getTransactionBuyDTO());
        transactionDTOS.add(TransactionControllerTest.getTransactionSellDTO());
        transactionDTOS.add(TransactionControllerTest.getTransactionBuyDTO());
        reportDTO.setTransactionDTOS(transactionDTOS);
        ArrayList<DividendPaymentDTO> dividendPaymentDTOS = new ArrayList<>();
        dividendPaymentDTOS.add(getDividendPaymentDTO());
        dividendPaymentDTOS.add(getDividendPaymentDTO());
        reportDTO.setDividendPaymentDTOS(dividendPaymentDTOS);
        return reportDTO;
    }

    private DividendPaymentDTO getDividendPaymentDTO() {
        DividendPaymentDTO dividendPaymentDTO = new DividendPaymentDTO();
        dividendPaymentDTO.setStockId(STOCK_ID);
        dividendPaymentDTO.setMarketPrice(VALID_MONEY);
        dividendPaymentDTO.setValue(VALID_MONEY);
        return dividendPaymentDTO;
    }

    private Response getQuarterlyReport(String accountNumberValue, String type, String year, String quarter) {
        return target().path("accounts").path(accountNumberValue).path("reports")
                .queryParam("type", type)
                .queryParam("year", year)
                .queryParam("quarter", quarter)
                .request().get();
    }

    private Response getQuarterlyReportWithoutQuarter(String accountNumberValue, String type, String year) {
        return target().path("accounts").path(accountNumberValue).path("reports")
                .queryParam("type", type)
                .queryParam("year", year)
                .request().get();
    }

    private Response getQuarterlyReportWithoutYear(String accountNumberValue, String type, String quarter) {
        return target().path("accounts").path(accountNumberValue).path("reports")
                .queryParam("type", type)
                .queryParam("quarter", quarter)
                .request().get();
    }

    private Response getQuarterlyReportWithoutYearAndQuarter(String accountNumberValue, String type) {
        return target().path("accounts").path(accountNumberValue).path("reports")
                .queryParam("type", type)
                .request().get();
    }

//    private QuarterlyReportDTO getQuarterlyReportDTO() {
//        QuarterlyReportDTO reportDTO = new QuarterlyReportDTO();
//        reportDTO.setTimeslot(END_OF_THE_DAY_DATE);
//        ArrayList<StockAccountDTO> stockAccountDTOS = new ArrayList<>();
//        stockAccountDTOS.add(getStockAccountDTO());
//        stockAccountDTOS.add(getStockAccountDTO());
//        stockAccountDTOS.add(getStockAccountDTO());
//        reportDTO.setStockAccountDTOS(stockAccountDTOS);
//        return reportDTO;
//    }
//
//    private StockAccountDTO getStockAccountDTO() {
//        StockAccountDTO stockAccountDTO = new StockAccountDTO();
//        stockAccountDTO.setMarket(MARKET);
//        stockAccountDTO.setSymbol(SYMBOL);
//        stockAccountDTO.setRateOfReturn(RATE_OF_RETURN);
//        stockAccountDTO.setTotalDividends(VALID_CREDITS);
//        stockAccountDTO.setQuantity(VALID_QUANTITY);
//        return stockAccountDTO;
//    }
}