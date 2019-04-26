package ca.ulaval.glo4002.trading.domain.account.transaction;

import ca.ulaval.glo4002.trading.application.transaction.strategies.CalculateFeesMinorMajorStrategy;
import ca.ulaval.glo4002.trading.application.transaction.strategies.CalculateTotalPurchaseStrategy;
import ca.ulaval.glo4002.trading.application.transaction.strategies.CalculateTotalSaleStrategy;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateFeesStrategy;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateTotalStrategy;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TransactionTest {

    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final long QUANTITY = 15L;
    private static final StockId STOCK_ID = new StockId("NASDAQ", "MSFT");

    private static final float MINOR_FEE_PER_SHARE = 0.25f;
    private static final float MAJOR_FEE_PER_SHARE = 0.20f;
    private static final float MINOR_AMOUNT_RATE = 0.00f;
    private static final float MAJOR_AMOUNT_RATE = 0.03f;

    private Transaction transaction;

    @Test
    public void givenABuyTransaction_whenCalculatingTotal_thenReturnsSubTotalPlusFees() {
        Money price = new Money(1);
        transaction = createTransaction(QUANTITY, price, TransactionType.BUY);
        Money subTotal = transaction.getSubTotal();
        Money expectedTotal = subTotal.add(transaction.getFees());
        assertEquals(expectedTotal, transaction.getTotal());
    }

    @Test
    public void givenASellTransaction_whenCalculatingTotal_thenReturnsSubTotalMinusFees() {
        Money price = new Money(1);
        transaction = createTransaction(QUANTITY, price, TransactionType.SELL);
        Money subTotal = transaction.getSubTotal();
        Money expectedTotal = subTotal.subtract(transaction.getFees());
        assertEquals(expectedTotal, transaction.getTotal());
    }

    @Test
    public void givenTransactionWithMinorQtyAndMinorAmt_whenComputeFees_thenMinorFeePerShareAndMinorRateApplied() {
        long shareQuantity = 100L;
        Money price = new Money(1);
        transaction = createTransaction(shareQuantity, price, TransactionType.BUY);
        Money expectedTransactionFees = computeTotalFees(MINOR_FEE_PER_SHARE, MINOR_AMOUNT_RATE);
        assertFeesEquals(expectedTransactionFees);
    }

    @Test
    public void givenTransactionWithMajorQtyAndMinorAmt_whenComputeFees_thenMajorFeePerShareAndMinorRateApplied() {
        long shareQuantity = 101L;
        Money price = new Money(1);
        transaction = createTransaction(shareQuantity, price, TransactionType.BUY);
        Money expectedTransactionFees = computeTotalFees(MAJOR_FEE_PER_SHARE, MINOR_AMOUNT_RATE);
        assertFeesEquals(expectedTransactionFees);
    }

    @Test
    public void givenTransactionWithMinorQtyAndMajorAmt_whenComputeFees_thenMinorFeePerShareAndMajorRateApplied() {
        long shareQuantity = 100L;
        Money price = new Money(51);
        transaction = createTransaction(shareQuantity, price, TransactionType.BUY);
        Money expectedTransactionFees = computeTotalFees(MINOR_FEE_PER_SHARE, MAJOR_AMOUNT_RATE);
        assertFeesEquals(expectedTransactionFees);
    }

    @Test
    public void givenTransactionWithMajorQtyAndMajorAmt_whenComputeFees_thenMajorFeePerShareAndMajorRateApplied() {
        long shareQuantity = 101L;
        Money price = new Money(51);
        transaction = createTransaction(shareQuantity, price, TransactionType.BUY);
        Money expectedTransactionFees = computeTotalFees(MAJOR_FEE_PER_SHARE, MAJOR_AMOUNT_RATE);
        assertFeesEquals(expectedTransactionFees);
    }

    private Transaction createTransaction(long quantity, Money price, TransactionType transactionType) {
        CalculateFeesStrategy calculateFeesStrategy = new CalculateFeesMinorMajorStrategy();
        CalculateTotalStrategy calculateTotalStrategy;
        if (transactionType == TransactionType.BUY) {
            calculateTotalStrategy = new CalculateTotalPurchaseStrategy();
        } else {
            calculateTotalStrategy = new CalculateTotalSaleStrategy();
        }
        return new Transaction(null,
                STOCK_ID, price, quantity, DATE, transactionType, calculateFeesStrategy, calculateTotalStrategy);
    }

    private Money computeTotalFees(float feePerShare, float feeAmountRate) {
        long quantity = transaction.getQuantity();
        Money stockPrice = transaction.getPrice();
        Money feesSubAmount = stockPrice.multiply(feeAmountRate * quantity);
        Money feesPerShare = new Money(feePerShare * quantity);
        return feesSubAmount.add(feesPerShare);
    }

    private void assertFeesEquals(Money expectedTransactionFees) {
        assertEquals(expectedTransactionFees, transaction.getFees());
    }

}