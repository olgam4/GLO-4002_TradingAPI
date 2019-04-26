package ca.ulaval.glo4002.trading.application.transaction;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateFeesStrategy;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateTotalStrategy;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;

public class TransactionDTO {

    private TransactionNumber transactionNumber;
    private TransactionNumber referencedTransactionNumber;
    private LocalDateTime date;
    private StockId stockId;
    private Money price;
    private long quantity;
    private Money fees;
    private TransactionType type;
    private CalculateFeesStrategy calculateFeesStrategy;
    private CalculateTotalStrategy calculateTotalStrategy;

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public TransactionNumber getReferencedTransactionNumber() {
        return referencedTransactionNumber;
    }

    public void setReferencedTransactionNumber(TransactionNumber referencedTransactionNumber) {
        this.referencedTransactionNumber = referencedTransactionNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Money getFees() {
        return fees;
    }

    public void setFees(Money fees) {
        this.fees = fees;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public CalculateFeesStrategy getCalculateFeesStrategy() {
        return calculateFeesStrategy;
    }

    public void setCalculateFeesStrategy(CalculateFeesStrategy calculateFeesStrategy) {
        this.calculateFeesStrategy = calculateFeesStrategy;
    }

    public CalculateTotalStrategy getCalculateTotalStrategy() {
        return calculateTotalStrategy;
    }

    public void setCalculateTotalStrategy(CalculateTotalStrategy calculateTotalStrategy) {
        this.calculateTotalStrategy = calculateTotalStrategy;
    }

}
