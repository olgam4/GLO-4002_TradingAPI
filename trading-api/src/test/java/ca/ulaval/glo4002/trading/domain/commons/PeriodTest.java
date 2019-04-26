package ca.ulaval.glo4002.trading.domain.commons;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeriodTest {

    private static final LocalDateTime START_TIME = LocalDateTime.of(2018, 1, 1, 12, 0);
    private static final long A_NUMBER_OF_HOURS = 2;
    private static final long A_NUMBER_OF_DAYS = 1;
    private static final LocalDateTime END_TIME = START_TIME.plusHours(A_NUMBER_OF_HOURS);
    private static final LocalDateTime NEXT_DAY_END_TIME = START_TIME.minusHours(A_NUMBER_OF_HOURS)
            .plusDays(A_NUMBER_OF_DAYS);

    private Period period;

    @Test
    public void givenValidTime_whenCheckingIfInPeriod_thenContained() {
        period = new Period(START_TIME, END_TIME);
        assertTrue(period.contains(START_TIME));
    }

    @Test
    public void givenInvalidTime_whenCheckingIfInPeriod_thenNotContained() {
        period = new Period(START_TIME, END_TIME);
        assertFalse(period.contains(START_TIME.plusHours(A_NUMBER_OF_HOURS + 1)));
    }

    @Test
    public void givenValidTime_whenCheckingIfInPeriodOverlappingTwoDays_thenContained() {
        period = new Period(START_TIME, NEXT_DAY_END_TIME);
        assertTrue(period.contains(START_TIME.plusHours(A_NUMBER_OF_HOURS)));
    }

    @Test
    public void givenInvalidTime_whenCheckingIfInPeriodOverlappingTwoDays_thenContained() {
        period = new Period(START_TIME, NEXT_DAY_END_TIME);
        assertFalse(period.contains(START_TIME.minusHours(1)));
    }

    @Test
    public void givenATimeMatchingAPeriodStartTime_whenCheckingIfInPeriod_thenContained() {
        period = new Period(START_TIME, END_TIME);
        assertTrue(period.contains(START_TIME));
    }

    @Test
    public void givenATimeMatchingAPeriodEndTime_whenCheckingIfInPeriod_thenContained() {
        period = new Period(START_TIME, END_TIME);
        assertTrue(period.contains(END_TIME));
    }

    @Test
    public void givenTimeMatchingAPeriodStartTime_whenCheckingIfInPeriodOverlappingTwoDays_thenContained() {
        period = new Period(START_TIME, NEXT_DAY_END_TIME);
        assertTrue(period.contains(START_TIME));
    }

    @Test
    public void givenTimeMatchingAPeriodEndTime_whenCheckingIfInPeriodOverlappingTwoDays_thenContained() {
        period = new Period(START_TIME, NEXT_DAY_END_TIME);
        assertTrue(period.contains(NEXT_DAY_END_TIME));
    }

    @Test
    public void givenTimeMatchingAnInvertedPeriod_whenCheckingIfInPeriod_thenContained() {
        period = new Period(NEXT_DAY_END_TIME, START_TIME);
        assertTrue(period.contains(NEXT_DAY_END_TIME));
    }

}
