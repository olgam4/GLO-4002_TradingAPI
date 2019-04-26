package ca.ulaval.glo4002.trading.rest.dividend;

import ca.ulaval.glo4002.trading.application.dividend.DividendApplicationService;
import ca.ulaval.glo4002.trading.application.dividend.DividendDTO;
import ca.ulaval.glo4002.trading.rest.dividend.views.requests.DividendRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DividendControllerTest {

    private static final DividendDTO DIVIDEND_DTO = new DividendDTO();
    private static final DividendRequest DIVIDEND_REQUEST = new DividendRequest();


    private DividendController dividendController;

    @Mock
    private DividendApplicationService dividendApplicationService;

    @Mock
    private DividendViewAssembler dividendViewAssembler;

    @Before
    public void setUp() {
        dividendController = new DividendController(dividendApplicationService, dividendViewAssembler);
        willReturn(DIVIDEND_DTO).given(dividendViewAssembler).from(DIVIDEND_REQUEST);
    }

    @Test
    public void whenCreateDividend_thenResponseIsAsExpected() {
        Response response = dividendController.postDividend(DIVIDEND_REQUEST);
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        assertNull(response.getLocation());
    }

    @Test
    public void whenCreateDividend_thenDividendIsCreated() {
        dividendController.postDividend(DIVIDEND_REQUEST);
        verify(dividendApplicationService).createDividend(DIVIDEND_DTO);
    }

}