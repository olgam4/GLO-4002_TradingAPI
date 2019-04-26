package ca.ulaval.glo4002.trading.application.dividend;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.dividend.Dividend;
import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DividendApplicationServiceTest {

    private static final StockId STOCK_ID = new StockId("MARKET", "SYMBOL");
    private static final float DIVIDEND_PER_SHARE = 0.5f;
    private static final LocalDateTime DATE = LocalDateTime.of(2016, 4, 5, 22, 22, 22);

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private Account account1;

    @Mock
    private Account account2;

    @Mock
    private Account account3;

    @Mock
    private Stock stock;

    @Mock
    private Market market;

    private Dividend dividend;
    private DividendApplicationService dividendApplicationService;
    private DividendDomainAssembler dividendDomainAssembler;
    private DividendDTO dividendDTO;

    @Before
    public void setUp() {
        dividendDomainAssembler = new DividendDomainAssembler();
        dividendApplicationService = new DividendApplicationService(accountRepository, stockRepository, dividendDomainAssembler, marketRepository);
        dividendDTO = new DividendDTO();
        dividendDTO.setStockId(STOCK_ID);
        dividendDTO.setDate(DATE);
        dividendDTO.setStock(stock);
        dividendDTO.setDividendPerShare(DIVIDEND_PER_SHARE);
        List<Account> accounts = Arrays.asList(account1, account2, account3);
        willReturn(accounts).given(accountRepository).getAll();
        willReturn(market).given(marketRepository).findByMarket(anyString());
        willReturn(Currency.USD).given(market).getCurrency();
    }

    @Test
    public void whenCreatingDividend_thenDividendIsAppliedToAllAccounts() {
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        dividend = dividendDomainAssembler.from(dividendDTO);
        dividendApplicationService.createDividend(dividendDTO);
        verify(account1).applyDividend(dividend);
        verify(account2).applyDividend(dividend);
        verify(account3).applyDividend(dividend);
    }

    @Test
    public void whenCreatingDividend_thenAccountsAreUpdated() {
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        dividendApplicationService.createDividend(dividendDTO);
        verify(accountRepository).update(account1);
        verify(accountRepository).update(account2);
        verify(accountRepository).update(account3);
    }

}