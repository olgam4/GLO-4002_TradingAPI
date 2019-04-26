package ca.ulaval.glo4002.trading.application.transaction.strategies;

import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateFeesStrategy;
import ca.ulaval.glo4002.trading.domain.money.Money;

public class CalculateFeesMinorMajorStrategy implements CalculateFeesStrategy {

    private static final long MINOR_QUANTITY_UPPER_LIMIT = 100L;
    private static final float MINOR_FEE_PER_SHARE = 0.25f;
    private static final float MAJOR_FEE_PER_SHARE = 0.20f;
    private static final Money MINOR_AMOUNT_UPPER_LIMIT = new Money(5000f);
    private static final float MINOR_AMOUNT_RATE = 0f;
    private static final float MAJOR_AMOUNT_RATE = 0.03f;

    @Override
    public Money calculateFees(Transaction transaction) {
        Money feesPerShare = computeFeesPerShare(transaction);
        Money feesSubAmount = computeFeesSubAmount(transaction);
        return feesPerShare.add(feesSubAmount);
    }

    private Money computeFeesPerShare(Transaction transaction) {
        float intermediateResult = getFeePerShare(transaction) * transaction.getQuantity();
        return new Money(intermediateResult);
    }

    private float getFeePerShare(Transaction transaction) {
        if (transaction.getQuantity() <= MINOR_QUANTITY_UPPER_LIMIT) {
            return MINOR_FEE_PER_SHARE;
        } else {
            return MAJOR_FEE_PER_SHARE;
        }
    }

    private Money computeFeesSubAmount(Transaction transaction) {
        Money subTotal = transaction.getSubTotal();
        return subTotal.multiply(getFeeAmountRate(transaction));
    }

    private float getFeeAmountRate(Transaction transaction) {
        if (transaction.getSubTotal().isLowerThanOrEqualTo(MINOR_AMOUNT_UPPER_LIMIT)) {
            return MINOR_AMOUNT_RATE;
        } else {
            return MAJOR_AMOUNT_RATE;
        }
    }

}
