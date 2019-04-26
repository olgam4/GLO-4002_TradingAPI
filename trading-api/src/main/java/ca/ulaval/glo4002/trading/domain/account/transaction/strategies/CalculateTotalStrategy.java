package ca.ulaval.glo4002.trading.domain.account.transaction.strategies;

import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.money.Money;

public interface CalculateTotalStrategy {

    Money calculateTotal(CalculateFeesStrategy feesStrategy, Transaction transaction);

}
