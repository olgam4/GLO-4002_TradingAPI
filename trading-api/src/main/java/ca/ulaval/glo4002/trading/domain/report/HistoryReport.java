package ca.ulaval.glo4002.trading.domain.report;

import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.Period;

import java.util.List;

public class HistoryReport {

    private final Period period;
    private final Credits balance;
    private final Money portfolioValue;
    private final List<Transaction> transactions;
    private final List<DividendPayment> stocks;

    public HistoryReport(Period period, Credits balance, Money portfolioValue, List<Transaction> transactions, List<DividendPayment> stocks) {
        this.period = period;
        this.balance = balance;
        this.portfolioValue = portfolioValue;
        this.transactions = transactions;
        this.stocks = stocks;
    }

    public Period getPeriod() {
        return period;
    }

    public Credits getBalance() {
        return balance;
    }

    public Money getPortfolioValue() {
        return portfolioValue;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<DividendPayment> getStocks() {
        return stocks;
    }

}
