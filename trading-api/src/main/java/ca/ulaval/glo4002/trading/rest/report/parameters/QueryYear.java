package ca.ulaval.glo4002.trading.rest.report.parameters;

import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportInvalidYearException;

import java.time.DateTimeException;
import java.time.Year;

public class QueryYear {

    private Year year;

    public QueryYear(String value) {
        if (value.isEmpty()) {
            this.year = Year.now();
        } else {
            this.year = parseYear(value);
        }
    }

    private Year parseYear(String yearValue) {
        try {
            int yearNumber = Integer.parseInt(yearValue);
            return Year.of(yearNumber);
        } catch (NumberFormatException | DateTimeException e) {
            throw new ReportInvalidYearException();
        }
    }

    public Year getValue() {
        return year;
    }

}
