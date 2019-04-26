package ca.ulaval.glo4002.trading.domain.report;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class QuarterTypeTest {

    private QuarterType quarterType;

    private static final LocalDateTime DATE_IN_Q1 = LocalDateTime.of(2018, 2, 8, 0, 0);
    private static final LocalDateTime DATE_IN_Q2 = LocalDateTime.of(2018, 6, 21, 0, 0);
    private static final LocalDateTime DATE_IN_Q3 = LocalDateTime.of(2018, 7, 7, 0, 0);
    private static final LocalDateTime DATE_IN_Q4 = LocalDateTime.of(2018, 12, 5, 0, 0);

    @Test
    public void whenGettingQuarterTypeFromDateInQ1_thenQuarterTypeQ1() {
        quarterType = QuarterType.fromLocalDateTime(DATE_IN_Q1);
        assertEquals(QuarterType.Q1, quarterType);
    }

    @Test
    public void whenGettingQuarterTypeFromDateInQ2_thenQuarterTypeQ2() {
        quarterType = QuarterType.fromLocalDateTime(DATE_IN_Q2);
        assertEquals(QuarterType.Q2, quarterType);
    }

    @Test
    public void whenGettingQuarterTypeFromDateInQ3_thenQuarterTypeQ3() {
        quarterType = QuarterType.fromLocalDateTime(DATE_IN_Q3);
        assertEquals(QuarterType.Q3, quarterType);
    }

    @Test
    public void whenGettingQuarterTypeFromDateInQ4_thenQuarterTypeQ4() {
        quarterType = QuarterType.fromLocalDateTime(DATE_IN_Q4);
        assertEquals(QuarterType.Q4, quarterType);
    }

}