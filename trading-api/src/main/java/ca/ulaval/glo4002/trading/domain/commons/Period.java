package ca.ulaval.glo4002.trading.domain.commons;

import java.time.LocalDateTime;

public class Period extends ValueObject {

    private LocalDateTime beginning;
    private LocalDateTime ending;

    public Period(LocalDateTime beginning, LocalDateTime ending) {
        this.beginning = beginning;
        this.ending = ending;
    }

    public boolean contains(LocalDateTime date) {
        if (beginning.isAfter(ending)) {
            return !(date.isBefore(beginning) && date.isAfter(ending));
        }
        return !date.isBefore(beginning) && !date.isAfter(ending);
    }

    public LocalDateTime getBeginning() {
        return beginning;
    }

    public LocalDateTime getEnding() {
        return ending;
    }

}
