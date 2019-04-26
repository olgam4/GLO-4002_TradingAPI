package ca.ulaval.glo4002.trading.domain.account;

import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumbers;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.InvalidTransactionNumberException;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.NotEnoughStockException;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.StockParametersDontMatchException;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.TransactionNotFoundException;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;
import java.util.*;


public class Wallet {

    private Map<TransactionNumber, Transaction> transactions = new HashMap<>();
    private Map<TransactionNumber, TransactionNumbers> history = new HashMap<>();

    public Wallet() {
        // For hibernate
    }

    public Map<TransactionNumber, Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<TransactionNumber, Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<TransactionNumber, TransactionNumbers> getHistory() {
        return history;
    }

    public void setHistory(Map<TransactionNumber, TransactionNumbers> history) {
        this.history = history;
    }

    Transaction getPurchaseTransaction(Transaction transactionSell) {
        try {
            TransactionNumber referencedTransactionNumber = transactionSell.getReferencedTransactionNumber();
            return getTransaction(referencedTransactionNumber);
        } catch (TransactionNotFoundException e) {
            TransactionNumber transactionNumber = transactionSell.getTransactionNumber();
            throw new InvalidTransactionNumberException(transactionNumber);
        }
    }

    Transaction getTransaction(TransactionNumber transactionNumber) {
        Transaction transaction = transactions.get(transactionNumber);
        if (transaction == null) {
            throw new TransactionNotFoundException(transactionNumber);
        }
        return transaction;
    }

    void checkIfEnoughStocksToProceedSale(Transaction transactionBuy, Transaction transactionSell) {
        checkIfStocksMatch(transactionBuy, transactionSell);
        checkIfEnoughStocks(transactionSell, transactionBuy);
    }

    private void checkIfStocksMatch(Transaction transactionBuy, Transaction transactionSell) {
        StockId stockPurchase = transactionBuy.getStockId();
        StockId stockSale = transactionSell.getStockId();
        if (!stockPurchase.equals(stockSale)) {
            TransactionNumber transactionNumber = transactionBuy.getTransactionNumber();
            throw new StockParametersDontMatchException(transactionNumber);
        }
    }

    private void checkIfEnoughStocks(Transaction transactionSell, Transaction transactionBuy) {
        long remainingQuantity = getRemainingQuantity(transactionBuy);
        if (remainingQuantity < transactionSell.getQuantity()) {
            StockId stockId = transactionSell.getStockId();
            TransactionNumber transactionNumber = transactionSell.getTransactionNumber();
            throw new NotEnoughStockException(transactionNumber, stockId);
        }
    }

    private long getRemainingQuantity(Transaction referencedTransaction) {
        TransactionNumber referencedTransactionNumber = referencedTransaction.getTransactionNumber();
        TransactionNumbers transactionHistory = history.get(referencedTransactionNumber);
        long remainingQuantity = referencedTransaction.getQuantity();
        for (TransactionNumber transactionNumber : transactionHistory.getTransactionNumberList()) {
            Transaction transaction = transactions.get(transactionNumber);
            remainingQuantity -= transaction.getQuantity();
        }
        return remainingQuantity;
    }

    void addPurchaseTransaction(Transaction transactionBuy) {
        recordTransaction(transactionBuy);
        registerTransactionHistory(transactionBuy);
    }

    private void recordTransaction(Transaction transaction) {
        TransactionNumber transactionNumber = transaction.getTransactionNumber();
        transactions.put(transactionNumber, transaction);
    }

    private void registerTransactionHistory(Transaction transaction) {
        TransactionNumber transactionNumber = transaction.getTransactionNumber();
        history.put(transactionNumber, new TransactionNumbers());
    }

    void addSaleTransaction(Transaction transactionBuy, Transaction transactionSell) {
        recordTransaction(transactionSell);
        updateTransactionHistory(transactionBuy, transactionSell);
    }

    private void updateTransactionHistory(Transaction referencedTransaction, Transaction transaction) {
        TransactionNumber referencedTransactionTransactionNumber = referencedTransaction.getTransactionNumber();
        TransactionNumbers transactionHistory = history.get(referencedTransactionTransactionNumber);
        TransactionNumber transactionNumber = transaction.getTransactionNumber();
        transactionHistory.getTransactionNumberList().add(transactionNumber);
        history.put(referencedTransactionTransactionNumber, transactionHistory);
    }

    List<Transaction> getTransactionsOver(Period period) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Map.Entry<TransactionNumber, Transaction> entry : this.transactions.entrySet()) {
            Transaction transaction = entry.getValue();
            LocalDateTime transactionDate = transaction.getDate();
            if (period.contains(transactionDate)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    List<InvestmentPosition> getInvestmentPositionsAsOf(LocalDateTime date) {
        Period period = new Period(LocalDateTime.MIN, date);
        List<InvestmentPosition> investmentPositions = new ArrayList<>();
        for (TransactionNumber transactionNumber : history.keySet()) {
            Transaction transaction = transactions.get(transactionNumber);
            LocalDateTime transactionDate = transaction.getDate();
            long remainingQuantity = getRemainingQuantityAsOf(transaction, date);
            if (period.contains(transactionDate) && remainingQuantity > 0) {
                StockId stockId = transaction.getStockId();
                InvestmentPosition investmentPosition = new InvestmentPosition(transactionNumber, stockId, remainingQuantity);
                investmentPositions.add(investmentPosition);
            }
        }
        return investmentPositions;
    }

    private long getRemainingQuantityAsOf(Transaction referencedTransaction, LocalDateTime date) {
        LocalDateTime referencedTransactionDate = referencedTransaction.getDate();
        if (referencedTransactionDate.isAfter(date)) {
            return 0;
        }
        Period period = new Period(LocalDateTime.MIN, date);
        TransactionNumber referencedTransactionNumber = referencedTransaction.getTransactionNumber();
        long remainingQuantity = referencedTransaction.getQuantity();
        TransactionNumbers referencedTransactionNumbers = history.get(referencedTransactionNumber);
        for (TransactionNumber transactionNumber : referencedTransactionNumbers.getTransactionNumberList()) {
            Transaction transaction = transactions.get(transactionNumber);
            LocalDateTime transactionDate = transaction.getDate();
            if (period.contains(transactionDate)) {
                remainingQuantity -= transaction.getQuantity();
            }
        }
        return remainingQuantity;
    }

    List<InvestmentPosition> getInvestmentPositionsOver(Period period) {
        List<InvestmentPosition> marketPositionBeginning = getInvestmentPositionsAsOf(period.getBeginning());
        List<InvestmentPosition> marketPositionEnding = getInvestmentPositionsAsOf(period.getEnding());
        return retainAll(marketPositionEnding, marketPositionBeginning);
    }

    private List<InvestmentPosition> retainAll(List<InvestmentPosition> source, List<InvestmentPosition> filter) {
        Set<StockId> stockIdsFilter = new HashSet<>();
        Set<TransactionNumber> transactionNumbersFilter = new HashSet<>();
        for (InvestmentPosition investmentPosition : filter) {
            stockIdsFilter.add(investmentPosition.getStockId());
            transactionNumbersFilter.add(investmentPosition.getTransactionNumber());
        }
        List<InvestmentPosition> filteredInvestmentPositions = new ArrayList<>();
        for (InvestmentPosition investmentPosition : source) {
            TransactionNumber transactionNumber = investmentPosition.getTransactionNumber();
            StockId stockId = investmentPosition.getStockId();
            if (transactionNumbersFilter.contains(transactionNumber) && stockIdsFilter.contains(stockId)) {
                filteredInvestmentPositions.add(investmentPosition);
            }
        }
        return filteredInvestmentPositions;
    }

}


