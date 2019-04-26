package ca.ulaval.glo4002.trading.application.transaction.strategies;

import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateFeesStrategy;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateTotalStrategy;
import ca.ulaval.glo4002.trading.domain.money.Money;

public class CalculateTotalSaleStrategy implements CalculateTotalStrategy {

    @Override
    public Money calculateTotal(CalculateFeesStrategy feesStrategy, Transaction transaction) {
        Money fees = feesStrategy.calculateFees(transaction);
        return transaction.getSubTotal().subtract(fees);
    }

}
