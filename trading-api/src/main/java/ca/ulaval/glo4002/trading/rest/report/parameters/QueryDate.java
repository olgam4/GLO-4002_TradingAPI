package ca.ulaval.glo4002.trading.rest.report.parameters;

import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportInvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class QueryDate {

    private LocalDate date;

    public QueryDate(String value) {
        if (value == null || value.isEmpty()) {
            this.date = null;
        } else {
            this.date = parseDate(value);
        }
    }

    private LocalDate parseDate(String dateValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            LocalDate date = LocalDate.parse(dateValue, formatter);
            checkIfValidDate(date);
            return date;
        } catch (DateTimeParseException e) {
            throw new ReportInvalidDateException(dateValue);
        }
    }

    private void checkIfValidDate(LocalDate date) {
        LocalDate now = LocalDate.now();
        if (date.equals(now) || date.isAfter(now)) {
            throw new ReportInvalidDateException(date);
        }
    }

    public LocalDate getValue() {
        return date;
    }

}
