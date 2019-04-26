package ca.ulaval.glo4002.trading.application.report;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.report.HistoryReport;
import ca.ulaval.glo4002.trading.domain.report.Quarter;
import ca.ulaval.glo4002.trading.domain.report.Reporter;
import ca.ulaval.glo4002.trading.domain.report.StockMarketReturnReport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ReportApplicationServiceTest {

    private static final String INVESTOR_NAME = "Rick Sanchez";
    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber(INVESTOR_NAME);
    private static final LocalDate DATE = LocalDate.now();
    private static final LocalDateTime PERIOD_START = LocalDateTime.of(DATE, LocalTime.now());
    private static final LocalDateTime PERIOD_END = PERIOD_START.plusDays(5);
    private static final Period PERIOD = new Period(PERIOD_START, PERIOD_END);
    private static final Money PORTFOLIO_VALUE = new Money(65f);

    private ReportApplicationService reportApplicationService;
    private ReportConverter reportConverter;
    private HistoryReport historyReport;
    private StockMarketReturnReport stockMarketReturnReport;

    @Mock
    private Reporter reporter;

    @Mock
    private Credits credits;

    @Before
    public void setUp() {
        historyReport = new HistoryReport(PERIOD, credits, PORTFOLIO_VALUE, new ArrayList<>(), new ArrayList<>());
        stockMarketReturnReport = new StockMarketReturnReport(PERIOD, new ArrayList<>());
        reportConverter = new ReportConverter();
        reportApplicationService = new ReportApplicationService(reporter, reportConverter);
    }

    @Test
    public void whenGeneratingDailyReport_thenDailyReportIsGenerated() {
        willReturn(historyReport).given(reporter).generateHistoryReport(eq(ACCOUNT_NUMBER), any(Period.class));
        reportApplicationService.generateDailyReport(ACCOUNT_NUMBER, DATE);
        verify(reporter).generateHistoryReport(eq(ACCOUNT_NUMBER), any(Period.class));
    }

    @Test
    public void whenGeneratingQuarterlyReport_thenQuarterlyReportIsGenerated() {
        willReturn(stockMarketReturnReport).given(reporter).generateStockMarketReport(eq(ACCOUNT_NUMBER), any(Period.class));
        reportApplicationService.generateQuarterlyReport(ACCOUNT_NUMBER, new Quarter());
        verify(reporter).generateStockMarketReport(eq(ACCOUNT_NUMBER), any(Period.class));
    }

}