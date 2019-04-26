package ca.ulaval.glo4002.trading.domain.account;

import ca.ulaval.glo4002.trading.domain.account.creditBalance.BalanceHistory;
import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.account.dividend.Dividend;
import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.domain.account.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4002.trading.domain.account.investor.Investor;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorType;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.NotEnoughCreditsBuyException;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.NotEnoughCreditsSellException;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Account {

    private static long count = 0;

    private Investor investor;
    private AccountNumber accountNumber;
    private Wallet wallet;
    private BalanceHistory balanceHistory;
    private List<DividendPayment> dividendPayments = new ArrayList<>();

    public Account() {
        // For hibernate
    }

    public Account(InvestorId investorId, String investorName, Credits credits) {
        this.investor = new Investor(investorId);
        String accountNumberString = computeAccountNumber(investorName);
        this.accountNumber = new AccountNumber(accountNumberString);
        this.balanceHistory = new BalanceHistory(credits);
        this.wallet = new Wallet();
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public BalanceHistory getBalanceHistory() {
        return balanceHistory;
    }

    public void setBalanceHistory(BalanceHistory balanceHistory) {
        this.balanceHistory = balanceHistory;
    }

    public List<DividendPayment> getDividendPayments() {
        return dividendPayments;
    }

    public void setDividendPayments(List<DividendPayment> dividendPayments) {
        this.dividendPayments = dividendPayments;
    }

    public InvestorId getInvestorId() {
        return investor.getId();
    }

    public InvestorType getInvestorType() {
        return investor.getType();
    }

    public List<String> getFocusAreas() {
        return investor.getFocusAreas();
    }

    private static synchronized long generateNumber() {
        return count++;
    }

    private String computeAccountNumber(String investorName) {
        return findInitials(investorName) + '-' + generateNumber();
    }

    private String findInitials(String investorName) {
        String[] partsOfName = investorName.split(" ");
        return partsOfName[0].substring(0, 1) + partsOfName[1].substring(0, 1);
    }

    public Transaction getTransaction(TransactionNumber transactionNumber) {
        return wallet.getTransaction(transactionNumber);
    }

    public void buy(Transaction transactionBuy) {
        Money transactionMoney = transactionBuy.getTotal();
        TransactionNumber transactionNumber = transactionBuy.getTransactionNumber();
        checkIfEnoughCreditsPurchase(transactionNumber, transactionMoney);
        LocalDateTime transactionDate = transactionBuy.getDate();
        removeCredits(transactionDate, transactionMoney);
        wallet.addPurchaseTransaction(transactionBuy);
    }

    private void checkIfEnoughCreditsPurchase(TransactionNumber transactionNumber, Money total) {
        try {
            checkIfEnoughCredits(total);
        } catch (NotEnoughCreditsException e) {
            throw new NotEnoughCreditsBuyException(transactionNumber);
        }
    }

    private void checkIfEnoughCredits(Money money) throws NotEnoughCreditsException {
        if (getBalance().getBy(money.getCurrency()).isLowerThan(money)) {
            throw new NotEnoughCreditsException();
        }
    }

    public void sell(Transaction transactionSell) {
        Transaction transactionBuy = wallet.getPurchaseTransaction(transactionSell);
        wallet.checkIfEnoughStocksToProceedSale(transactionBuy, transactionSell);
        Money total = transactionSell.getTotal();
        if (total.isLowerThan(Money.ZERO_MONEY)) {
            handleNegativeSale(transactionSell, total);
        } else {
            handlePositiveSale(transactionSell, total);
        }
        wallet.addSaleTransaction(transactionBuy, transactionSell);
    }

    private void handleNegativeSale(Transaction transactionSell, Money total) {
        Money negatedTotal = total.negate();
        TransactionNumber transactionNumber = transactionSell.getTransactionNumber();
        checkIfEnoughCreditsSale(transactionNumber, negatedTotal);
        LocalDateTime transactionDate = transactionSell.getDate();
        removeCredits(transactionDate, negatedTotal);
    }

    private void handlePositiveSale(Transaction transactionSell, Money total) {
        LocalDateTime transactionDate = transactionSell.getDate();
        addCredits(transactionDate, total);
    }

    private void checkIfEnoughCreditsSale(TransactionNumber transactionNumber, Money total) {
        try {
            checkIfEnoughCredits(total);
        } catch (NotEnoughCreditsException e) {
            throw new NotEnoughCreditsSellException(transactionNumber);
        }
    }

    private void addCredits(LocalDateTime date, Money money) {
        balanceHistory.addCredits(date, money);
    }

    private void removeCredits(LocalDateTime date, Money money) {
        balanceHistory.removeCredits(date, money);
    }

    public Credits getBalance() {
        return balanceHistory.getBalance();
    }

    public Credits getBalanceAsOf(LocalDateTime date) {
        return balanceHistory.getBalance(date);
    }

    public List<Transaction> getTransactionsOver(Period period) {
        return wallet.getTransactionsOver(period);
    }

    public List<InvestmentPosition> getInvestmentPositionsAsOf(LocalDateTime date) {
        return wallet.getInvestmentPositionsAsOf(date);
    }

    public List<InvestmentPosition> getInvestmentPositionsOver(Period period) {
        return wallet.getInvestmentPositionsOver(period);
    }

    public void applyDividend(Dividend dividend) {
        LocalDateTime date = dividend.getDate();
        List<InvestmentPosition> investmentPositions = getInvestmentPositionsAsOf(date);
        StockId stockId = dividend.getStockId();
        List<InvestmentPosition> filteredInvestmentPositions = filterInvestmentPositionsByStockId(stockId, investmentPositions);
        for (InvestmentPosition investmentPosition : filteredInvestmentPositions) {
            TransactionNumber transactionNumber = investmentPosition.getTransactionNumber();
            long quantity = investmentPosition.getQuantity();
            DividendPayment dividendPayment = dividend.convertToPayment(transactionNumber, quantity);
            dividendPayments.add(dividendPayment);
            Money dividendPaymentValue = dividendPayment.getValue();
            addCredits(date, dividendPaymentValue);
        }
    }

    private List<InvestmentPosition> filterInvestmentPositionsByStockId(StockId stockId, List<InvestmentPosition> investmentPositions) {
        List<InvestmentPosition> filteredInvestmentPositions = new ArrayList<>();
        for (InvestmentPosition investmentPosition : investmentPositions) {
            if (investmentPosition.getStockId().equals(stockId)) {
                filteredInvestmentPositions.add(investmentPosition);
            }
        }
        return filteredInvestmentPositions;
    }

    public List<DividendPayment> getDividendPaymentsOver(Period period) {
        List<DividendPayment> filteredDividendPayments = new ArrayList<>();
        for (DividendPayment dividendPayment : dividendPayments) {
            LocalDateTime dividendPaymentDate = dividendPayment.getDate();
            if (period.contains(dividendPaymentDate)) {
                filteredDividendPayments.add(dividendPayment);
            }
        }
        return filteredDividendPayments;
    }

    public Money getTotalBalanceAsOf(LocalDateTime date) {
        Credits credits = balanceHistory.getBalance(date);
        return credits.getTotalCADValue();
    }

    public Money getTotalBalance() {
        Credits credits = balanceHistory.getBalance();
        return credits.getTotalCADValue();
    }
}
