package ca.ulaval.glo4002.trading.domain.account.investor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class InvestorTest {

    private static final InvestorId INVESTOR_ID = new InvestorId(134L);
    private static final InvestorType DEFAULT_INVESTOR_TYPE = InvestorType.CONSERVATIVE;

    private Investor investor;

    @Before
    public void setUp() {
        investor = new Investor(INVESTOR_ID);
    }

    @Test
    public void whenCreated_thenTypeIsConservative() {
        assertEquals(DEFAULT_INVESTOR_TYPE, investor.getType());
    }

    @Test
    public void whenCreated_thenHasEmptyFocusAreas() {
        assertTrue(investor.getFocusAreas().isEmpty());
    }

}