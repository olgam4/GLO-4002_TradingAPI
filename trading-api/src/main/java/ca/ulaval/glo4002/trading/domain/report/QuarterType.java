package ca.ulaval.glo4002.trading.domain.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;

public enum QuarterType {

    Q1(MonthDay.of(Month.JANUARY, 1), MonthDay.of(Month.MARCH, 31)),
    Q2(MonthDay.of(Month.APRIL, 1), MonthDay.of(Month.JUNE, 30)),
    Q3(MonthDay.of(Month.JULY, 1), MonthDay.of(Month.SEPTEMBER, 30)),
    Q4(MonthDay.of(Month.OCTOBER, 1), MonthDay.of(Month.DECEMBER, 31));

    private final MonthDay beginning;
    private final MonthDay ending;

    QuarterType(MonthDay quarterBeginning, MonthDay quarterEnd) {
        this.beginning = quarterBeginning;
        this.ending = quarterEnd;
    }

    public MonthDay getBeginning() {
        return beginning;
    }

    public MonthDay getEnding() {
        return ending;
    }

    public static QuarterType getCurrentQuarterType() {
        LocalDate now = LocalDate.now();
        return getQuarterTypeFromMonth(now.getMonth());
    }

    public static QuarterType fromLocalDateTime(LocalDateTime date) {
        return getQuarterTypeFromMonth(date.getMonth());
    }

    private static QuarterType getQuarterTypeFromMonth(Month month) {
        int monthIndex = month.getValue();
        int quarterIndex = ((monthIndex - 1) / 3) + 1;
        String quarterName = String.format("Q%d", quarterIndex);
        return QuarterType.valueOf(quarterName);
    }
}
