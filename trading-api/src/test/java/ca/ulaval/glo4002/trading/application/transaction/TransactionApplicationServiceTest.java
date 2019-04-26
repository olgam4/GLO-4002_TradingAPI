package ca.ulaval.glo4002.trading.application.transaction;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.StockNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.TransactionInvalidDateException;
import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.market.exceptions.MarketClosedException;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class TransactionApplicationServiceTest {

    private static final UUID TRANSACTION_NUMBER_VALUE = UUID.randomUUID();
    private static final TransactionNumber TRANSACTION_NUMBER = new TransactionNumber(TRANSACTION_NUMBER_VALUE);
    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber("SN-909090");
    private static final Money FEES = Money.ZERO_MONEY;
    private static final Money PRICE = new Money(3453.2345f);
    private static final long QUANTITY = 223L;
    private static final String INVALID_MARKET = "INVALID_MARKET";
    private static final String INVALID_SYMBOL = "INVALID_SYMBOL";
    private static final String SYMBOL = "MSFT";
    private static final String MARKET = "NASDAQ";
    private static final LocalDateTime DATE = LocalDateTime.of(2018, 8, 10, 2, 18, 20, 142000000);
    private static final LocalDateTime INVALID_DATE = LocalDateTime.of(2020, 8, 10, 2, 18, 20, 142000000);
    private static final StockId STOCK_ID = new StockId(MARKET, SYMBOL);
    private static final StockId INVALID_STOCK_ID = new StockId(INVALID_MARKET, INVALID_SYMBOL);

    @Mock
    private TransactionDomainAssembler transactionDomainAssembler;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private Account account;

    @Mock
    private Transaction transaction;

    @Mock
    private Stock stock;

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private Market market;

    private TransactionApplicationService transactionApplicationService;
    private TransactionDTO transactionDTO = new TransactionDTO();

    @Before
    public void setUp() throws InvalidDateException {
        transactionApplicationService = new TransactionApplicationService(
                accountRepository,
                stockRepository,
                marketRepository,
                transactionDomainAssembler);
        willReturn(account).given(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
        willReturn(stock).given(stockRepository).findByStockId(new StockId(MARKET, SYMBOL));
        willThrow(new StockNotFoundException(INVALID_STOCK_ID)).given(stockRepository).findByStockId(INVALID_STOCK_ID);
        willReturn(STOCK_ID).given(stock).getStockId();
        willReturn(PRICE).given(stock).getPrice(DATE);
        willReturn(transaction).given(transactionDomainAssembler).from(transactionDTO);
        willReturn(market).given(marketRepository).findByMarket(MARKET);
        willReturn(true).given(market).isMarketOpen(any(LocalDateTime.class));
        transactionDTO.setStockId(STOCK_ID);
        transactionDTO.setFees(FEES);
        transactionDTO.setQuantity(QUANTITY);
        transactionDTO.setDate(DATE);
        transactionDTO.setTransactionNumber(TRANSACTION_NUMBER);
        transactionDTO.setType(TransactionType.BUY);
        transactionDTO.setPrice(PRICE);
    }

    @Test(expected = StockNotFoundException.class)
    public void givenInvalidStock_whenPurchaseTransaction_thenThrows() {
        transactionDTO.setStockId(INVALID_STOCK_ID);
        transactionApplicationService.purchaseTransaction(ACCOUNT_NUMBER, transactionDTO);
    }

    @Test(expected = TransactionInvalidDateException.class)
    public void givenInvalidDate_whenCreatingTransaction_thenThrows() throws InvalidDateException {
        transactionDTO.setDate(INVALID_DATE);
        InvalidDateException exception = new InvalidDateException();
        willThrow(exception).given(stock).getPrice(INVALID_DATE);
        transactionApplicationService.purchaseTransaction(ACCOUNT_NUMBER, transactionDTO);
    }

    @Test
    public void whenRetrievingTransaction_thenFindTransactionInAccount() {
        willReturn(transaction).given(account).getTransaction(TRANSACTION_NUMBER);
        transactionApplicationService.getByTransactionNumber(ACCOUNT_NUMBER, TRANSACTION_NUMBER);
        verify(account).getTransaction(TRANSACTION_NUMBER);
    }

    @Test
    public void whenPurchaseTransaction_thenAccountBuy() {
        transactionDTO.setType(TransactionType.BUY);
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        transactionApplicationService.purchaseTransaction(ACCOUNT_NUMBER, transactionDTO);
        verify(account).buy(transaction);
    }

    @Test
    public void whenSellTransaction_thenAccountSell() {
        transactionDTO.setType(TransactionType.SELL);
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        transactionApplicationService.sellTransaction(ACCOUNT_NUMBER, transactionDTO);
        verify(account).sell(transaction);
    }

    @Test(expected = MarketClosedException.class)
    public void givenClosedMarket_whenPurchaseTransaction_thenThrows() {
        setupMarketClosed();
        transactionApplicationService.purchaseTransaction(ACCOUNT_NUMBER, transactionDTO);
    }

    @Test(expected = MarketClosedException.class)
    public void givenClosedMarket_whenSellingTransaction_thenThrows() {
        setupMarketClosed();
        transactionApplicationService.sellTransaction(ACCOUNT_NUMBER, transactionDTO);
    }

    private void setupMarketClosed() {
        willReturn(true).given(market).isMarketClose(any(LocalDateTime.class));
        willReturn(STOCK_ID).given(transaction).getStockId();
    }

}
