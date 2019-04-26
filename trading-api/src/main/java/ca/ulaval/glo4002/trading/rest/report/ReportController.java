package ca.ulaval.glo4002.trading.rest.report;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.report.DailyReportDTO;
import ca.ulaval.glo4002.trading.application.report.QuarterlyReportDTO;
import ca.ulaval.glo4002.trading.application.report.ReportApplicationService;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.report.Quarter;
import ca.ulaval.glo4002.trading.domain.report.QuarterType;
import ca.ulaval.glo4002.trading.domain.report.ReportType;
import ca.ulaval.glo4002.trading.rest.account.AccountController;
import ca.ulaval.glo4002.trading.rest.report.assemblers.DailyReportViewAssembler;
import ca.ulaval.glo4002.trading.rest.report.assemblers.QuarterlyReportViewAssembler;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportInvalidDateException;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportMissingDateException;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportMissingTypeException;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryDate;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryQuarterType;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryReportType;
import ca.ulaval.glo4002.trading.rest.report.parameters.QueryYear;
import ca.ulaval.glo4002.trading.rest.report.views.responses.DailyReportResponse;
import ca.ulaval.glo4002.trading.rest.report.views.responses.QuarterlyReportResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Year;


@Path(AccountController.ACCOUNTS_PATH + "/" + AccountController.ACCOUNT_NUMBER_PATH_PARAM + "/" + ReportController.REPORTS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class ReportController {

    public static final String REPORTS_PATH = "reports";
    private static final String TYPE_PARAM = "type";
    private static final String DATE_PARAM = "date";
    private static final String YEAR_PARAM = "year";
    private static final String QUARTER_PARAM = "quarter";

    private final ReportApplicationService reportApplicationService;
    private final DailyReportViewAssembler dailyReportViewAssembler;
    private final QuarterlyReportViewAssembler quarterlyReportViewAssembler;

    public ReportController() {
        this(
                ServiceLocator.resolve(ReportApplicationService.class),
                new DailyReportViewAssembler(),
                new QuarterlyReportViewAssembler()
        );
    }

    ReportController(ReportApplicationService reportApplicationService, DailyReportViewAssembler dailyReportViewAssembler,
                     QuarterlyReportViewAssembler quarterlyReportViewAssembler) {
        this.reportApplicationService = reportApplicationService;
        this.dailyReportViewAssembler = dailyReportViewAssembler;
        this.quarterlyReportViewAssembler = quarterlyReportViewAssembler;
    }

    @GET
    public Response getReport(@PathParam(AccountController.ACCOUNT_NUMBER_PARAM) AccountNumber accountNumber,
                              @QueryParam(TYPE_PARAM) QueryReportType queryReportType,
                              @QueryParam(DATE_PARAM) QueryDate queryDate,
                              @DefaultValue("") @QueryParam(YEAR_PARAM) QueryYear queryYear,
                              @DefaultValue("") @QueryParam(QUARTER_PARAM) QueryQuarterType queryQuarterType) {
        checkIfMissingReportType(queryReportType);
        ReportType reportType = queryReportType.getValue();
        if (reportType == ReportType.DAILY) {
            checkIfMissingDate(queryDate);
            LocalDate date = queryDate.getValue();
            checkIfValidDate(date);
            DailyReportDTO dailyReportDTO = reportApplicationService.generateDailyReport(accountNumber, date);
            DailyReportResponse dailyReportResponse = dailyReportViewAssembler.from(dailyReportDTO);
            return Response.ok(dailyReportResponse, MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            Year year = queryYear.getValue();
            QuarterType quarterType = queryQuarterType.getValue();
            Quarter quarter = new Quarter(year, quarterType);
            QuarterlyReportDTO quarterlyReportDTO = reportApplicationService.generateQuarterlyReport(accountNumber, quarter);
            QuarterlyReportResponse quarterlyReportResponse = quarterlyReportViewAssembler.from(quarterlyReportDTO);
            return Response.ok(quarterlyReportResponse, MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    private void checkIfMissingReportType(QueryReportType queryReportType) {
        if (queryReportType == null) {
            throw new ReportMissingTypeException();
        }
    }

    private void checkIfMissingDate(QueryDate queryDate) {
        if (queryDate == null || queryDate.getValue() == null) {
            throw new ReportMissingDateException();
        }
    }

    private void checkIfValidDate(LocalDate date) {
        LocalDate now = LocalDate.now();
        if (date.isEqual(now) || date.isAfter(now)) {
            throw new ReportInvalidDateException(date);
        }
    }

}
