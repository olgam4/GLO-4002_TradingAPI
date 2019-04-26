package ca.ulaval.glo4002.trading.rest.report.parameters;

import ca.ulaval.glo4002.trading.domain.report.ReportType;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportUnsupportedTypeException;

public class QueryReportType {

    private ReportType reportType;

    public QueryReportType(String reportType) {
        this.reportType = parseReportType(reportType);
    }

    private ReportType parseReportType(String reportType) {
        try {
            return ReportType.valueOf(reportType);
        } catch (IllegalArgumentException e) {
            throw new ReportUnsupportedTypeException(reportType);
        }
    }

    public ReportType getValue() {
        return reportType;
    }

}
