package ca.ulaval.glo4002.trading.domain.report;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.InvestmentPosition;
import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.CurrencyExchanger;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ReporterTest {

    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber("Wailord Nihilego");
    private static final LocalDateTime DATE = LocalDateTime.of(2018, 5, 12, 3, 43, 0);
    private static final LocalDateTime PERIOD_BEGIN = DATE;
    private static final LocalDateTime PERIOD_END = DATE.plusDays(8);
    private static final Period PERIOD = new Period(PERIOD_BEGIN, PERIOD_END);
    private static final long QUANTITY = 3L;
    private static final StockId STOCK_ID = new StockId("A", "STOCKID");
    private static final long ANOTHER_QUANTITY = 4L;
    private static final StockId ANOTHER_STOCK_ID = new StockId("ANOTHER", "STOCKID");
    private static final Money PRICE = new Money(5f, Currency.CAD);
    private static final Money ANOTHER_PRICE = new Money(10f, Currency.CAD);
    private static final Money START_PRICE = new Money(30f);
    private static final Money END_PRICE = new Money(10f);
    private static final float DELTA = 0.001f;
    private static final TransactionNumber TRANSACTION_NUMBER = new TransactionNumber();
    private static final TransactionNumber ANOTHER_TRANSACTION_NUMBER = new TransactionNumber();
    private static final float RATE = 1f;

    private Reporter reporter;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private CurrencyExchanger currencyExchanger;

    @Mock
    private Account account;

    @Mock
    private Transaction transaction;

    @Mock
    private Transaction anotherTransaction;

    @Mock
    private Stock stock;

    @Mock
    private Stock anotherStock;

    @Mock
    private Market market;

    @Mock
    private Market anotherMarket;

    @Before
    public void setUp() {
        reporter = new Reporter(accountRepository, stockRepository, marketRepository, currencyExchanger);
        willReturn(account).given(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void whenGeneratingHistoryReport_thenActivePositionAtTheEndOfThePeriodAreUsed() {
        reporter.generateHistoryReport(ACCOUNT_NUMBER, PERIOD);
        verify(account).getInvestmentPositionsAsOf(PERIOD_END);
    }

    @Test
    public void whenGeneratingHistoryReport_thenBalanceAtTheEndOfThePeriodIsRetrieved() {
        reporter.generateHistoryReport(ACCOUNT_NUMBER, PERIOD);
        verify(account).getBalanceAsOf(PERIOD_END);
    }

    @Test
    public void whenGeneratingHistoryReport_thenTransactionsOverThePeriodAreRetrieved() {
        reporter.generateHistoryReport(ACCOUNT_NUMBER, PERIOD);
        verify(account).getTransactionsOver(PERIOD);
    }

    @Test
    public void whenGeneratingHistoryReport_thenDividendPaymentsOverThePeriodAreRetrieved() {
        reporter.generateHistoryReport(ACCOUNT_NUMBER, PERIOD);
        verify(account).getDividendPaymentsOver(PERIOD);
    }

    @Test
    public void givenAnAccountWithoutActivePosition_whenGeneratingHistoryReport_thenPortfolioValueIsZero() {
        willReturn(new ArrayList<>()).given(account).getInvestmentPositionsAsOf(PERIOD_END);
        HistoryReport historyReport = reporter.generateHistoryReport(ACCOUNT_NUMBER, PERIOD);
        assertEquals(Money.ZERO_MONEY, historyReport.getPortfolioValue());
    }

    @Test
    public void givenAnAccountWithActivePositions_whenGeneratingHistoryReport_thenPortfolioValueIsTheCumulativeValue() throws InvalidDateException {
        List<InvestmentPosition> investmentPositions = new ArrayList<>();
        InvestmentPosition investmentPosition = new InvestmentPosition(TRANSACTION_NUMBER, STOCK_ID, QUANTITY);
        investmentPositions.add(investmentPosition);
        InvestmentPosition anotherInvestmentPosition = new InvestmentPosition(ANOTHER_TRANSACTION_NUMBER, ANOTHER_STOCK_ID, ANOTHER_QUANTITY);
        investmentPositions.add(anotherInvestmentPosition);
        willReturn(investmentPositions).given(account).getInvestmentPositionsAsOf(PERIOD_END);
        willReturn(new ArrayList<>()).given(account).getDividendPaymentsOver(PERIOD);
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        willReturn(anotherStock).given(stockRepository).findByStockId(ANOTHER_STOCK_ID);
        willReturn(PRICE).given(stock).getPrice(PERIOD_END);
        willReturn(ANOTHER_PRICE).given(anotherStock).getPrice(PERIOD_END);
        willReturn(market).given(marketRepository).findByMarket(STOCK_ID.getMarket());
        willReturn(market).given(marketRepository).findByMarket(ANOTHER_STOCK_ID.getMarket());
        willReturn(Currency.CAD).given(market).getCurrency();
        Money stockValue = PRICE.multiply(QUANTITY);
        Money anotherStockValue = ANOTHER_PRICE.multiply(ANOTHER_QUANTITY);
        willReturn(stockValue).given(currencyExchanger).convertToCAD(stockValue);
        willReturn(anotherStockValue).given(currencyExchanger).convertToCAD(anotherStockValue);
        Money expectedPortfolioValue = stockValue.add(anotherStockValue);
        HistoryReport historyReport = reporter.generateHistoryReport(ACCOUNT_NUMBER, PERIOD);
        assertEquals(expectedPortfolioValue, historyReport.getPortfolioValue());
    }

    @Test
    public void whenGeneratingStockMarketReturnReport_thenInvestmentPositionsOverThePeriodAreUsed() {
        reporter.generateStockMarketReport(ACCOUNT_NUMBER, PERIOD);
        verify(account).getInvestmentPositionsOver(PERIOD);
    }

    @Test
    public void whenGeneratingStockMarketReturnReport_thenAggregatedDividendPaymentsOverThePeriodAreRetrieved() {
        reporter.generateStockMarketReport(ACCOUNT_NUMBER, PERIOD);
        verify(account).getDividendPaymentsOver(PERIOD);
    }

    @Test
    public void givenTwoDividendPaymentsOnTheSameStockWithSamePurchasePrice_whenGeneratingStockMarketReturnReport_thenDividendPaymentsAreAggregated() throws InvalidDateException {
        List<InvestmentPosition> investmentPositions = new ArrayList<>();
        investmentPositions.add(new InvestmentPosition(TRANSACTION_NUMBER, STOCK_ID, QUANTITY));
        investmentPositions.add(new InvestmentPosition(ANOTHER_TRANSACTION_NUMBER, STOCK_ID, QUANTITY));
        willReturn(investmentPositions).given(account).getInvestmentPositionsOver(PERIOD);
        List<DividendPayment> dividendPayments = new ArrayList<>();
        dividendPayments.add(new DividendPayment(TRANSACTION_NUMBER, STOCK_ID, PERIOD_BEGIN, PRICE, PRICE));
        dividendPayments.add(new DividendPayment(ANOTHER_TRANSACTION_NUMBER, STOCK_ID, PERIOD_BEGIN, PRICE, PRICE));
        willReturn(dividendPayments).given(account).getDividendPaymentsOver(PERIOD);
        willReturn(PRICE).given(transaction).getPrice();
        willReturn(PRICE).given(anotherTransaction).getPrice();
        willReturn(PRICE).given(stock).getPrice(PERIOD_BEGIN);
        willReturn(PRICE).given(stock).getPrice(PERIOD_END);
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        willReturn(transaction).given(account).getTransaction(TRANSACTION_NUMBER);
        willReturn(anotherTransaction).given(account).getTransaction(ANOTHER_TRANSACTION_NUMBER);
        StockMarketReturnReport stockMarketReturnReport = reporter.generateStockMarketReport(ACCOUNT_NUMBER, PERIOD);
        Money expectedValue = PRICE.multiply(2);
        long expectedQuantity = QUANTITY * 2;
        List<StockMarketReturn> stockMarketReturns = stockMarketReturnReport.getStockMarketReturns();
        assertEquals(1, stockMarketReturns.size());
        StockMarketReturn stockMarketReturn = stockMarketReturns.get(0);
        assertEquals(expectedValue, stockMarketReturn.getTotalDividends());
        assertEquals(expectedQuantity, stockMarketReturn.getQuantity());
    }

    @Test
    public void givenTwoDividendPaymentsOnTheSameStockWithDifferentPurchasePrice_whenGeneratingStockMarketReturnReport_thenDividendPaymentsAreNotAggregated() throws InvalidDateException {
        List<InvestmentPosition> investmentPositions = new ArrayList<>();
        investmentPositions.add(new InvestmentPosition(TRANSACTION_NUMBER, STOCK_ID, QUANTITY));
        investmentPositions.add(new InvestmentPosition(ANOTHER_TRANSACTION_NUMBER, STOCK_ID, QUANTITY));
        willReturn(investmentPositions).given(account).getInvestmentPositionsOver(PERIOD);
        List<DividendPayment> dividendPayments = new ArrayList<>();
        dividendPayments.add(new DividendPayment(TRANSACTION_NUMBER, STOCK_ID, PERIOD_BEGIN, PRICE, PRICE));
        dividendPayments.add(new DividendPayment(ANOTHER_TRANSACTION_NUMBER, STOCK_ID, PERIOD_BEGIN, PRICE, PRICE));
        willReturn(dividendPayments).given(account).getDividendPaymentsOver(PERIOD);
        willReturn(PRICE).given(transaction).getPrice();
        willReturn(ANOTHER_PRICE).given(anotherTransaction).getPrice();
        willReturn(PRICE).given(stock).getPrice(PERIOD_BEGIN);
        willReturn(PRICE).given(stock).getPrice(PERIOD_END);
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        willReturn(transaction).given(account).getTransaction(TRANSACTION_NUMBER);
        willReturn(anotherTransaction).given(account).getTransaction(ANOTHER_TRANSACTION_NUMBER);
        StockMarketReturnReport stockMarketReturnReport = reporter.generateStockMarketReport(ACCOUNT_NUMBER, PERIOD);
        List<StockMarketReturn> stockMarketReturns = stockMarketReturnReport.getStockMarketReturns();
        assertEquals(2, stockMarketReturns.size());
        StockMarketReturn firstStockMarketReturn = stockMarketReturns.get(0);
        assertEquals(PRICE, firstStockMarketReturn.getTotalDividends());
        assertEquals(QUANTITY, firstStockMarketReturn.getQuantity());
        StockMarketReturn secondStockMarketReturn = stockMarketReturns.get(1);
        assertEquals(PRICE, secondStockMarketReturn.getTotalDividends());
        assertEquals(QUANTITY, secondStockMarketReturn.getQuantity());
    }

    @Test
    public void givenAnAccountWithInvestmentPositions_whenGeneratingStockMarketReturnReport_thenRateOfReturnIsComputed() throws InvalidDateException {
        List<InvestmentPosition> investmentPositions = new ArrayList<>();
        InvestmentPosition investmentPosition = new InvestmentPosition(TRANSACTION_NUMBER, STOCK_ID, QUANTITY);
        investmentPositions.add(investmentPosition);
        willReturn(investmentPositions).given(account).getInvestmentPositionsOver(PERIOD);
        List<DividendPayment> dividendPayments = new ArrayList<>();
        dividendPayments.add(new DividendPayment(TRANSACTION_NUMBER, STOCK_ID, DATE, PRICE, PRICE));
        willReturn(dividendPayments).given(account).getDividendPaymentsOver(PERIOD);
        willReturn(transaction).given(account).getTransaction(TRANSACTION_NUMBER);
        willReturn(PRICE).given(transaction).getPrice();
        willReturn(stock).given(stockRepository).findByStockId(STOCK_ID);
        willReturn(START_PRICE).given(stock).getPrice(PERIOD_BEGIN);
        willReturn(END_PRICE).given(stock).getPrice(PERIOD_END);
        StockMarketReturnReport stockMarketReturnReport = reporter.generateStockMarketReport(ACCOUNT_NUMBER, PERIOD);
        float startPriceFloat = START_PRICE.getAmount().floatValue();
        float endPriceFloat = END_PRICE.getAmount().floatValue();
        float rateOfReturn = (endPriceFloat - startPriceFloat) / startPriceFloat * 100;
        float rateOfReturnRounded = BigDecimal.valueOf(rateOfReturn).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        StockMarketReturn stockMarketReturn = stockMarketReturnReport.getStockMarketReturns().get(0);
        assertEquals(rateOfReturnRounded, stockMarketReturn.getRateOfReturn(), DELTA);
    }

}