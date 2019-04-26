package ca.ulaval.glo4002.trading.domain.account.transaction;

import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateFeesStrategy;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateTotalStrategy;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;

public class Transaction {

    private TransactionNumber transactionNumber;
    private TransactionNumber referencedTransactionNumber;
    private StockId stockId;
    private long quantity;
    private LocalDateTime date;
    private TransactionType type;
    private Money price;
    private Money subTotal;
    private Money total;

    public Transaction() {
        // for hibernate
    }

    public Transaction(TransactionNumber referencedTransactionNumber,
                       StockId stockId, Money price, long quantity, LocalDateTime date, TransactionType type,
                       CalculateFeesStrategy calculateFeesStrategy,
                       CalculateTotalStrategy calculateTotalStrategy) {
        this.transactionNumber = new TransactionNumber();
        this.referencedTransactionNumber = referencedTransactionNumber;
        this.stockId = stockId;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.type = type;
        this.subTotal = price.multiply(quantity);
        this.total = calculateTotalStrategy.calculateTotal(calculateFeesStrategy, this);
    }

    public void setTransactionNumber(TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setReferencedTransactionNumber(TransactionNumber referencedTransactionNumber) {
        this.referencedTransactionNumber = referencedTransactionNumber;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public void setSubTotal(Money subTotal) {
        this.subTotal = subTotal;
    }

    public void setTotal(Money total) {
        this.total = total;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public TransactionNumber getReferencedTransactionNumber() {
        return referencedTransactionNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Money getFees() {
        return total.subtract(subTotal).abs();
    }

    public StockId getStockId() {
        return stockId;
    }

    public Money getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public TransactionType getType() {
        return type;
    }

    public Money getTotal() {
        return total;
    }

}
