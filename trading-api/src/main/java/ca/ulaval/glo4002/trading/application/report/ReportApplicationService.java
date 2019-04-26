package ca.ulaval.glo4002.trading.application.report;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.report.HistoryReport;
import ca.ulaval.glo4002.trading.domain.report.Quarter;
import ca.ulaval.glo4002.trading.domain.report.Reporter;
import ca.ulaval.glo4002.trading.domain.report.StockMarketReturnReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class ReportApplicationService {

    private final Reporter reporter;
    private final ReportConverter reportConverter;

    public ReportApplicationService() {
        this(
                ServiceLocator.resolve(Reporter.class),
                ServiceLocator.resolve(ReportConverter.class)
        );
    }

    ReportApplicationService(Reporter reporter,
                             ReportConverter reportConverter) {
        this.reporter = reporter;
        this.reportConverter = reportConverter;
    }

    public DailyReportDTO generateDailyReport(AccountNumber accountNumber, LocalDate date) {
        Period period = getPeriodFromLocalDate(date);
        HistoryReport historyReport = reporter.generateHistoryReport(accountNumber, period);
        return reportConverter.convertToDailyReportDTO(historyReport);
    }

    private Period getPeriodFromLocalDate(LocalDate date) {
        LocalDateTime beginningOfDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
        return new Period(beginningOfDay, endOfDay);
    }

    public QuarterlyReportDTO generateQuarterlyReport(AccountNumber accountNumber, Quarter quarter) {
        Period period = getPeriodFromQuarter(quarter);
        StockMarketReturnReport stockMarketReturnReport = reporter.generateStockMarketReport(accountNumber, period);
        return reportConverter.convertToQuarterlyReportDTO(stockMarketReturnReport);
    }

    private Period getPeriodFromQuarter(Quarter quarter) {
        LocalDateTime quarterBeginning = quarter.getBeginning();
        LocalDateTime quarterEnding = quarter.getEnding();
        LocalDateTime now = LocalDateTime.now();
        if (quarterEnding.isAfter(now)) {
            quarterEnding = now;
        }
        return new Period(quarterBeginning, quarterEnding);
    }

}
