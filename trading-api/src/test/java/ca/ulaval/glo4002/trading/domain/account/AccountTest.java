package ca.ulaval.glo4002.trading.domain.account;

import ca.ulaval.glo4002.trading.application.account.AccountDTO;
import ca.ulaval.glo4002.trading.application.account.CreditDTO;
import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.account.dividend.Dividend;
import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.domain.account.exceptions.InvalidAmountException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.*;
import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.domain.stock.StockType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;


@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    private static final InvestorId INVESTOR_ID = new InvestorId(1L);
    private static final String INVESTOR_NAME = "Farfetch'd Quagsire";
    private static final Money ACCOUNT_MONEY = new Money(100f);
    private static final TransactionNumber TRANSACTION_BUY_NUMBER = new TransactionNumber(UUID.randomUUID());
    private static final TransactionNumber TRANSACTION_SELL_NUMBER = new TransactionNumber(UUID.randomUUID());
    private static final Money PRICE_SOLD = new Money(10f);
    private static final Money PRICE_BOUGHT = new Money(10f);
    private static final Money HUGE_AMOUNT_OF_MONEY = new Money(1000000f);
    private static final Money INVALID_MONEY = Money.ZERO_MONEY;
    private static final Money NEGATIVE_MONEY = new Money(-324f);
    private static final Money VALID_MONEY = new Money(897f);
    private static final long QUANTITY_BOUGHT = 4;
    private static final StockId STOCK_ID = new StockId("A", "STOCK ID");
    private static final StockId ANOTHER_STOCK_ID = new StockId("ANOTHER", "STOCK ID");
    private static final TransactionNumber ANOTHER_TRANSACTION_NUMBER = new TransactionNumber();
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final float DIVIDEND_PER_SHARE = 10f;
    private static final Money ANOTHER_PRICE = new Money(6f);
    private static final LocalDateTime ANOTHER_DATE = LocalDateTime.now().minusWeeks(34);
    private static final long ANOTHER_QUANTITY = 60L;
    private static final LocalDateTime BEFORE_CURRENT_DATE = DATE.minusDays(1);
    private static final LocalDateTime PERIOD_START = DATE;
    private static final LocalDateTime PERIOD_END = DATE.plusMonths(3);
    private static final Period PERIOD = new Period(PERIOD_START, PERIOD_END);
    private static final LocalDateTime DATE_IN_PERIOD = PERIOD_START.plusDays(10);
    private static final LocalDateTime DATE_BEFORE_PERIOD = PERIOD_START.minusDays(1);
    private static final LocalDateTime DATE_AFTER_PERIOD = PERIOD_END.plusDays(1);

    @Mock
    private Transaction transactionBuy;

    @Mock
    private Transaction anotherTransactionBuy;

    @Mock
    private Transaction transactionSell;

    @Mock
    private Stock stock;

    @Mock
    private Credits credits;

    @Mock
    private Stock anotherStock;

    private Account account;
    private Dividend dividend;
    private Dividend anotherDividend;
    private AccountDTO accountDTO = new AccountDTO();
    private Map<StockId, Money> stockPrices;
    private CreditDTO creditDTO = new CreditDTO();
    private List<CreditDTO> creditDTOS = new ArrayList<>();

    @Before
    public void setUp() {
        creditDTOS.add(creditDTO);
        account = new Account(INVESTOR_ID, INVESTOR_NAME, credits);
        accountDTO.setInvestorId(INVESTOR_ID);
        accountDTO.setInvestorName(INVESTOR_NAME);
        accountDTO.setCredits(creditDTOS);
        stockPrices = new HashMap<>();
    }

    @Test
    public void givenATransaction_whenBuying_thenTransactionIsRecorded() {
        willReturn(new Money(100000f)).given(credits).getBy(any());
        buyTransaction();
        assertNotNull(account.getTransaction(TRANSACTION_BUY_NUMBER));
    }

    @Test
    public void givenATransaction_whenSelling_thenTransactionIsRecorded() {
        willReturn(new Money(100000f)).given(credits).getBy(any());
        buyTransaction();
        sellTransaction();
        assertNotNull(account.getTransaction(TRANSACTION_SELL_NUMBER));
    }

    @Test(expected = TransactionNotFoundException.class)
    public void givenUnrecordedTransaction_whenRetrieving_thenThrows() {
        TransactionNumber unexistingTransactionNumber = new TransactionNumber(UUID.randomUUID());
        account.getTransaction(unexistingTransactionNumber);
    }

    @Test(expected = InvalidTransactionNumberException.class)
    public void givenUnrecordedTransaction_whenSelling_thenThrows() {
        TransactionNumber unexistingTransactionNumber = new TransactionNumber(UUID.randomUUID());
        willReturn(unexistingTransactionNumber).given(transactionSell).getReferencedTransactionNumber();
        account.sell(transactionSell);
    }
/*
    @Test(expected = NotEnoughCreditsBuyException.class)
    public void givenATransactionWithTotalHigherThanAccountCredits_whenBuyingThatTransaction_thenThrows() {
        willReturn(HUGE_AMOUNT_OF_MONEY).given(transactionBuy).getTotal();
        account.buy(transactionBuy);
    }

    @Test(expected = NotEnoughCreditsSellException.class)
    public void givenATransactionWithTotalHigherThanAccountCredits_whenSellingThatTransaction_thenThrows() {
        buyTransaction();
        setUpTransaction(transactionSell, TRANSACTION_SELL_NUMBER, TRANSACTION_BUY_NUMBER,
                HUGE_AMOUNT_OF_MONEY.negate(), QUANTITY_BOUGHT, STOCK_ID, DATE);
        account.sell(transactionSell);
    }

    @Test(expected = NotEnoughStockException.class)
    public void givenAPurchaseWithAQuantityOfStocks_whenSellingABiggerQuantity_thenThrows() {
        buyTransaction();
        setUpTransaction(transactionSell, TRANSACTION_SELL_NUMBER, TRANSACTION_BUY_NUMBER,
                PRICE_SOLD, QUANTITY_BOUGHT + 1, STOCK_ID, DATE);
        account.sell(transactionSell);
    }

    @Test(expected = NotEnoughStockException.class)
    public void givenAPurchaseWithARemainingQuantityOfStocks_whenSellingABiggerQuantity_thenThrows() {
        buyTransaction();
        long halfOfQuantityBought = QUANTITY_BOUGHT / 2;
        setUpTransaction(transactionSell, TRANSACTION_SELL_NUMBER, TRANSACTION_BUY_NUMBER,
                PRICE_SOLD, halfOfQuantityBought, STOCK_ID, DATE);
        account.sell(transactionSell);
        setUpTransaction(transactionSell, TRANSACTION_SELL_NUMBER, TRANSACTION_BUY_NUMBER,
                PRICE_SOLD, halfOfQuantityBought + 1, STOCK_ID, DATE);
        account.sell(transactionSell);
    }

    @Test(expected = StockParametersDontMatchException.class)
    public void givenAPurchase_whenSellingADifferentStock_thenThrows() {
        buyTransaction();
        setUpTransaction(transactionSell, TRANSACTION_SELL_NUMBER, TRANSACTION_BUY_NUMBER,
                PRICE_SOLD, QUANTITY_BOUGHT, ANOTHER_STOCK_ID, DATE);
        account.sell(transactionSell);
    }

    @Test
    public void givenAnAccountWithoutHistory_whenRetrievingInvestmentPositions_thenReturnsNoActivePosition() {
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsAsOf(DATE);
        List<InvestmentPosition> expected = new ArrayList<>();
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithOneTransactionInHistory_whenRetrievingInvestmentPositions_thenReturnsActivePositionInformation() {
        buyTransaction();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsAsOf(DATE);
        List<InvestmentPosition> expected = new ArrayList<>();
        expected.add(new InvestmentPosition(TRANSACTION_BUY_NUMBER, STOCK_ID, QUANTITY_BOUGHT));
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithOneTransactionCompletelySoldInHistory_whenRetrievingInvestmentPositions_thenReturnsNoActivePosition() {
        buyTransaction();
        sellTransaction();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsAsOf(DATE);
        List<InvestmentPosition> expected = new ArrayList<>();
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithTwoPurchaseOfDifferentStocks_whenRetrievingInvestmentPositions_thenReturnsQuantityOfEachStock() {
        buyTransaction();
        buyAnotherTransaction();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsAsOf(DATE);
        List<InvestmentPosition> expected = new ArrayList<>();
        InvestmentPosition investmentPosition = new InvestmentPosition(TRANSACTION_BUY_NUMBER, STOCK_ID, QUANTITY_BOUGHT);
        expected.add(investmentPosition);
        InvestmentPosition anotherInvestmentPosition = new InvestmentPosition(ANOTHER_TRANSACTION_NUMBER, ANOTHER_STOCK_ID, ANOTHER_QUANTITY);
        expected.add(anotherInvestmentPosition);
        Collections.sort(expected);
        Collections.sort(investmentPositions);
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenATransactionInTheFuture_whenRetrievingInvestmentPositions_thenItIsNotTakenInCount() {
        buyTransaction();
        willReturn(DATE.plusDays(1)).given(transactionBuy).getDate();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsAsOf(DATE);
        List<InvestmentPosition> expected = new ArrayList<>();
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithoutHistory_whenRetrievingInvestmentPositionsInPeriod_thenReturnsNoActivePosition() {
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsOver(PERIOD);
        List<InvestmentPosition> expected = new ArrayList<>();
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithOneActivePositionTakenBeforeAPeriod_whenRetrievingInvestmentPositionsOverThatPeriod_thenReturnsActivePositionInformation() {
        buyTransaction();
        willReturn(DATE_BEFORE_PERIOD).given(transactionBuy).getDate();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsOver(PERIOD);
        List<InvestmentPosition> expected = new ArrayList<>();
        InvestmentPosition investmentPosition = new InvestmentPosition(TRANSACTION_BUY_NUMBER, STOCK_ID, QUANTITY_BOUGHT);
        expected.add(investmentPosition);
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithOneActivePositionTakenDuringAPeriod_whenRetrievingInvestmentPositionsOverThatPeriod_thenReturnsNoActivePosition() {
        buyTransaction();
        willReturn(DATE_IN_PERIOD).given(transactionBuy).getDate();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsOver(PERIOD);
        List<InvestmentPosition> expected = new ArrayList<>();
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithOneActivePositionTakenAfterAPeriod_whenRetrievingInvestmentPositionsOverThatPeriod_thenReturnsNoActivePosition() {
        buyTransaction();
        willReturn(DATE_AFTER_PERIOD).given(transactionBuy).getDate();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsOver(PERIOD);
        List<InvestmentPosition> expected = new ArrayList<>();
        assertEquals(expected, investmentPositions);
    }

    @Test
    public void givenAnAccountWithOneActivePositionTakenBeforeAPeriodAndCompletelySoldDuringTheSamePeriod_whenRetrievingInvestmentPositionsOverThatPeriod_thenReturnsNoActivePosition() {
        buyTransaction();
        willReturn(DATE_BEFORE_PERIOD).given(transactionBuy).getDate();
        sellTransaction();
        willReturn(DATE_IN_PERIOD).given(transactionSell).getDate();
        List<InvestmentPosition> marketPosition = account.getInvestmentPositionsOver(PERIOD);
        List<InvestmentPosition> expected = new ArrayList<>();
        assertEquals(expected, marketPosition);
    }

    @Test
    public void givenAnAccountWithOneActivePositionTakenBeforeAPeriodAndCompletelySoldAfterThatPeriod_whenRetrievingInvestmentPositionsOverThatPeriod_thenReturnsActivePositionInformation() {
        buyTransaction();
        willReturn(DATE_BEFORE_PERIOD).given(transactionBuy).getDate();
        sellTransaction();
        willReturn(DATE_AFTER_PERIOD).given(transactionSell).getDate();
        List<InvestmentPosition> marketPosition = account.getInvestmentPositionsOver(PERIOD);
        List<InvestmentPosition> expected = new ArrayList<>();
        InvestmentPosition investmentPosition = new InvestmentPosition(TRANSACTION_BUY_NUMBER, STOCK_ID, QUANTITY_BOUGHT);
        expected.add(investmentPosition);
        assertEquals(expected, marketPosition);
    }

    @Test
    public void givenAnAccountWithoutActiveTransactionsOnDividendStock_whenApplyingTheDividend_thenCreditsDontChange() throws InvalidDateException {
        dividend = setUpDividend(stock, STOCK_ID, StockType.COMMON, PRICE_BOUGHT, DATE, DIVIDEND_PER_SHARE);
        Money moneyBefore = account.getBalance();
        account.applyDividend(dividend);
        Money moneyAfter = account.getBalance();
        assertEquals(moneyBefore, moneyAfter);
    }

    @Test
    public void givenADividendOnCommonStock_whenApplyingIt_thenTheCreditsAreChangedAccordingly() throws InvalidDateException {
        buyTransaction();
        dividend = setUpDividend(stock, STOCK_ID, StockType.COMMON, PRICE_BOUGHT, DATE, DIVIDEND_PER_SHARE);
        Money moneyBeforeDividend = account.getBalance();
        account.applyDividend(dividend);
        Money dividendValue = new Money(QUANTITY_BOUGHT * DIVIDEND_PER_SHARE);
        Money expectedMoney = moneyBeforeDividend.add(dividendValue);
        assertEquals(expectedMoney, account.getBalance());
    }

    @Test
    public void givenADividendOnPreferredStock_whenApplyingIt_thenTheCreditsAreChangedAccordingly() throws InvalidDateException {
        buyTransaction();
        dividend = setUpDividend(stock, STOCK_ID, StockType.PREFERRED, PRICE_BOUGHT, DATE, DIVIDEND_PER_SHARE);
        Money moneyBeforeDividend = account.getBalance();
        account.applyDividend(dividend);
        Money commonDividendValue = new Money(QUANTITY_BOUGHT * DIVIDEND_PER_SHARE);
        Money preferredDividendValue = PRICE_BOUGHT.multiply(QUANTITY_BOUGHT * StockType.PREFERRED.getRate());
        Money totalDividendValue = commonDividendValue.add(preferredDividendValue);
        Money expectedMoney = moneyBeforeDividend.add(totalDividendValue);
        assertEquals(expectedMoney, account.getBalance());
    }
*/
    @Test
    public void givenNoDividendAppliedOnSpecificDate_whenRetrievingDividendPaymentsForThatDate_thenNoDividendPaymentAreReturned() {
        List<DividendPayment> dividendPayments = account.getDividendPaymentsOver(PERIOD);
        assertTrue(dividendPayments.isEmpty());
    }
/*
    @Test
    public void givenADividendAppliedOnSpecificDate_whenRetrievingDividendPaymentsForThatDate_thenCanConsultTheDividendValuePaidIntoTheAccount() throws InvalidDateException {
        buyTransaction();
        dividend = setUpDividend(stock, STOCK_ID, StockType.COMMON, PRICE_BOUGHT, DATE_IN_PERIOD, DIVIDEND_PER_SHARE);
        account.applyDividend(dividend);
        List<DividendPayment> dividendPayments = account.getDividendPaymentsOver(PERIOD);
        Money dividendValue = new Money(QUANTITY_BOUGHT * DIVIDEND_PER_SHARE);
        assertEquals(dividendValue, dividendPayments.get(0).getValue());
    }

    @Test
    public void givenTwoDividendsAppliedOnSpecificDateOnDifferentStock_whenRetrievingDividendPaymentsForThatDate_thenCanConsultDetailsForEachDividendPayment() throws InvalidDateException {
        buyTransaction();
        buyAnotherTransaction();
        dividend = setUpDividend(stock, STOCK_ID, StockType.COMMON, PRICE_BOUGHT, DATE_IN_PERIOD, DIVIDEND_PER_SHARE);
        account.applyDividend(dividend);
        anotherDividend = setUpDividend(anotherStock, ANOTHER_STOCK_ID, StockType.COMMON, ANOTHER_PRICE, DATE_IN_PERIOD.plusSeconds(1), DIVIDEND_PER_SHARE);
        account.applyDividend(anotherDividend);
        List<DividendPayment> dividendPayments = account.getDividendPaymentsOver(PERIOD);
        List<DividendPayment> expected = new ArrayList<>();
        expected.add(dividend.convertToPayment(TRANSACTION_BUY_NUMBER, QUANTITY_BOUGHT));
        expected.add(anotherDividend.convertToPayment(ANOTHER_TRANSACTION_NUMBER, ANOTHER_QUANTITY));
        assertEquals(expected, dividendPayments);
    }

    @Test
    public void givenTwoDividendsAppliedOnDifferentDate_whenRetrievingDividendPaymentsForOneOfThoseDate_thenCanConsultDetailsForOnlyOneOfThoseDividendPayments() throws InvalidDateException {
        buyTransaction();
        buyAnotherTransaction();
        dividend = setUpDividend(stock, STOCK_ID, StockType.COMMON, PRICE_BOUGHT, DATE_IN_PERIOD, DIVIDEND_PER_SHARE);
        account.applyDividend(dividend);
        anotherDividend = setUpDividend(anotherStock, ANOTHER_STOCK_ID, StockType.COMMON, ANOTHER_PRICE, DATE_AFTER_PERIOD, DIVIDEND_PER_SHARE);
        account.applyDividend(anotherDividend);
        List<DividendPayment> dividendPayments = account.getDividendPaymentsOver(PERIOD);
        List<DividendPayment> expected = new ArrayList<>();
        expected.add(dividend.convertToPayment(TRANSACTION_BUY_NUMBER, QUANTITY_BOUGHT));
        assertEquals(expected, dividendPayments);
    }

    @Test
    public void givenAnAccountWithoutDividendPaymentDuringAGivenPeriod_whenRetrievingTotalDividendPaymentsForThatPeriod_thenNoDividendPaymentAreReturned() throws InvalidDateException {
        setUpTransaction(transactionBuy, TRANSACTION_BUY_NUMBER, null,
                PRICE_BOUGHT, QUANTITY_BOUGHT, STOCK_ID, PERIOD_START.plusMonths(2));
        account.buy(transactionBuy);
        dividend = setUpDividend(stock, STOCK_ID, StockType.COMMON, PRICE_BOUGHT, PERIOD_START.plusMonths(1), DIVIDEND_PER_SHARE);
        account.applyDividend(dividend);
        List<DividendPayment> dividendPayments = account.getDividendPaymentsOver(PERIOD);
        assertTrue(dividendPayments.isEmpty());
    }

    @Test
    public void givenNoTransactionsForASpecificPeriod_whenRetrievingTransactions_thenNoTransactionOnThatPeriod() {
        List<Transaction> transactions = account.getTransactionsOver(PERIOD);
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void givenATransactionOnADifferentPeriod_whenRetrievingTransactions_thenNoTransactionOnThatPeriod() {
        buyTransaction();
        willReturn(BEFORE_CURRENT_DATE).given(transactionBuy).getDate();
        List<Transaction> transactions = account.getTransactionsOver(PERIOD);
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void givenATransactionOnSamePeriod_whenRetrievingTransactions_thenTheTransactionIsReturned() {
        buyTransaction();
        List<Transaction> transactions = account.getTransactionsOver(PERIOD);
        assertFalse(transactions.isEmpty());
        assertEquals(1, transactions.size());
        assertEquals(transactionBuy, transactions.get(0));
    }
*/
    private Account createAccount(AccountDTO accountDTO) {
        InvestorId investorId = accountDTO.getInvestorId();
        String investorName = accountDTO.getInvestorName();
        return new Account(investorId, investorName, credits);
    }

    private void buyTransaction() {
        setUpTransaction(transactionBuy, TRANSACTION_BUY_NUMBER, null,
                PRICE_BOUGHT, QUANTITY_BOUGHT, STOCK_ID, DATE);
        account.buy(transactionBuy);
        stockPrices.put(STOCK_ID, PRICE_BOUGHT);
    }

    private void buyAnotherTransaction() {
        setUpTransaction(anotherTransactionBuy, ANOTHER_TRANSACTION_NUMBER, null,
                ANOTHER_PRICE, ANOTHER_QUANTITY, ANOTHER_STOCK_ID, ANOTHER_DATE);
        account.buy(anotherTransactionBuy);
        stockPrices.put(ANOTHER_STOCK_ID, ANOTHER_PRICE);
    }

    private void sellTransaction() {
        setUpTransaction(transactionSell, TRANSACTION_SELL_NUMBER, TRANSACTION_BUY_NUMBER,
                PRICE_SOLD, QUANTITY_BOUGHT, STOCK_ID, DATE);
        account.sell(transactionSell);
    }

    private void setUpTransaction(Transaction transaction,
                                  TransactionNumber transactionNumber,
                                  TransactionNumber referencedTransactionNumber,
                                  Money price, long quantity, StockId stockId, LocalDateTime date) {
        willReturn(transactionNumber).given(transaction).getTransactionNumber();
        willReturn(referencedTransactionNumber).given(transaction).getReferencedTransactionNumber();
        willReturn(price).given(transaction).getTotal();
        willReturn(price).given(transaction).getPrice();
        willReturn(quantity).given(transaction).getQuantity();
        willReturn(stockId).given(transaction).getStockId();
        willReturn(date).given(transaction).getDate();
    }

    private Dividend setUpDividend(Stock stock, StockId stockId, StockType stockType,
                                   Money price, LocalDateTime date, float dps) throws InvalidDateException {
        willReturn(stockType.getRate()).given(stock).getDividendRate();
        willReturn(stockId).given(stock).getStockId();
        willReturn(price).given(stock).getPrice(date);
        return new Dividend(stock, date, dps);
    }
}