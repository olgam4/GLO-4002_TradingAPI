package ca.ulaval.glo4002.trading.rest.report;

import ca.ulaval.glo4002.trading.application.report.ReportApplicationService;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.report.Quarter;
import ca.ulaval.glo4002.trading.rest.report.assemblers.DailyReportViewAssembler;
import ca.ulaval.glo4002.trading.rest.report.assemblers.QuarterlyReportViewAssembler;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportInvalidDateException;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportMissingDateException;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryDate;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryQuarterType;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryReportType;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryYear;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {

    private static final String ACCOUNT_NUMBER_VALUE = "SC-9";
    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber(ACCOUNT_NUMBER_VALUE);
    private static final QueryDate DAILY_REPORT_DATE = new QueryDate("2018-05-12");
    private static final QueryReportType DAILY_REPORT_TYPE = new QueryReportType("DAILY");
    private static final QueryReportType QUARTERLY_REPORT_TYPE = new QueryReportType("QUARTERLY");
    private static final QueryYear QUARTERLY_REPORT_YEAR = new QueryYear("2018");
    private static final QueryQuarterType QUARTER_TYPE = new QueryQuarterType("Q2");

    private ReportController reportController;

    @Mock
    private ReportApplicationService reportApplicationService;

    @Mock
    private DailyReportViewAssembler dailyReportViewAssembler;

    @Mock
    private QuarterlyReportViewAssembler quarterlyReportViewAssembler;

    @Before
    public void setUp() {
        reportController = new ReportController(reportApplicationService, dailyReportViewAssembler,
                quarterlyReportViewAssembler);
    }

    @Test
    public void whenRetrievingADailyReport_thenResponseTypeIsAsExpected() {
        Response response = reportController.getReport(ACCOUNT_NUMBER, DAILY_REPORT_TYPE, DAILY_REPORT_DATE, null,
                null);
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    public void whenRetrievingADailyReport_thenReportIsRetrieved() {
        reportController.getReport(ACCOUNT_NUMBER, DAILY_REPORT_TYPE, DAILY_REPORT_DATE, null, null);
        verify(reportApplicationService).generateDailyReport(ACCOUNT_NUMBER, DAILY_REPORT_DATE.getValue());
    }

    @Test
    public void whenRetrievingAQuarterlyReport_thenResponseTypeIsAsExpected() {
        Response response = reportController.getReport(ACCOUNT_NUMBER, QUARTERLY_REPORT_TYPE, null,
                QUARTERLY_REPORT_YEAR, QUARTER_TYPE);
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    public void whenRetrievingAQuarterlyReport_thenReportIsRetrieved() {
        reportController.getReport(ACCOUNT_NUMBER, QUARTERLY_REPORT_TYPE, null, QUARTERLY_REPORT_YEAR, QUARTER_TYPE);
        Quarter quarter = new Quarter(QUARTERLY_REPORT_YEAR.getValue(), QUARTER_TYPE.getValue());
        verify(reportApplicationService).generateQuarterlyReport(ACCOUNT_NUMBER, quarter);
    }

    @Test(expected = ReportInvalidDateException.class)
    public void givenAnUnfinishedDay_whenGettingADailyReport_thenThrows() {
        reportController.getReport(ACCOUNT_NUMBER, DAILY_REPORT_TYPE, new QueryDate(LocalDate.now().toString()), null, null);
    }

    @Test(expected = ReportInvalidDateException.class)
    public void givenAFutureDay_whenGettingADailyReport_thenThrows() {
        reportController.getReport(ACCOUNT_NUMBER, DAILY_REPORT_TYPE, new QueryDate(LocalDate.now().plusDays(1).toString()), null, null);
    }

    @Test(expected = ReportMissingDateException.class)
    public void givingNoDate_whenGettingADailyReport_thenThrows() {
        reportController.getReport(ACCOUNT_NUMBER, DAILY_REPORT_TYPE, null, null, null);
    }

}
