package ca.ulaval.glo4002.trading.domain.report;

import ca.ulaval.glo4002.trading.domain.commons.ValueObject;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportInvalidYearException;

import java.time.*;

public class Quarter extends ValueObject {

    private static Year MIN = Year.of(2015);
    private static Year MAX = Year.of(2018);

    private QuarterType quarterType;
    private LocalDateTime beginning;
    private LocalDateTime ending;

    public Quarter(LocalDateTime date) {
        this(Year.from(date), QuarterType.fromLocalDateTime(date));
    }

    public Quarter() {
        this(Year.now(), QuarterType.getCurrentQuarterType());
    }

    public Quarter(Year year, QuarterType quarterType) {
        checkIfYearIsSpan(year);
        this.quarterType = quarterType;
        MonthDay quarterTypeStart = quarterType.getBeginning();
        int quarterStartMonth = quarterTypeStart.getMonth().getValue();
        int quarterStartDay = quarterTypeStart.getDayOfMonth();
        LocalDate quarterStartDate = LocalDate.of(year.getValue(), quarterStartMonth, quarterStartDay);
        this.beginning = LocalDateTime.of(quarterStartDate, LocalTime.MIN);
        MonthDay quarterTypeEnd = quarterType.getEnding();
        int quarterEndMonth = quarterTypeEnd.getMonth().getValue();
        int quarterEndDay = quarterTypeEnd.getDayOfMonth();
        LocalDate quarterEndDate = LocalDate.of(year.getValue(), quarterEndMonth, quarterEndDay);
        this.ending = LocalDateTime.of(quarterEndDate, LocalTime.MAX);
    }

    private void checkIfYearIsSpan(Year year) {
        if(year.isAfter(MAX) || year.isBefore(MIN)) {
            throw new ReportInvalidYearException();
        }
    }

    public QuarterType getQuarterType() {
        return quarterType;
    }

    public LocalDateTime getBeginning() {
        return beginning;
    }

    public LocalDateTime getEnding() {
        return ending;
    }

}
