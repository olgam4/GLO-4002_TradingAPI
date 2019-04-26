package ca.ulaval.glo4002.trading.rest.report.parameters;

import ca.ulaval.glo4002.trading.domain.report.QuarterType;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportInvalidQuarterException;

import static ca.ulaval.glo4002.trading.domain.report.QuarterType.getCurrentQuarterType;

public class QueryQuarterType {

    private QuarterType quarterType;

    public QueryQuarterType(String value) {
        if (value.isEmpty()) {
            this.quarterType = getCurrentQuarterType();
        } else {
            this.quarterType = parseQuarterType(value);
        }
    }

    private QuarterType parseQuarterType(String quarterTypeValue) {
        try {
            return QuarterType.valueOf(quarterTypeValue);
        } catch (IllegalArgumentException e) {
            throw new ReportInvalidQuarterException();
        }
    }

    public QuarterType getValue() {
        return quarterType;
    }

}
