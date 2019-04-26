package ca.ulaval.glo4002.trading.domain.account.dividend;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.ValueObject;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;

public class DividendPayment extends ValueObject {

    private TransactionNumber transactionNumber;
    private StockId stockId;
    private LocalDateTime date;
    private Money marketPrice;
    private Money value;

    public DividendPayment() {
        // For hibernate
    }

    public DividendPayment(TransactionNumber transactionNumber, StockId stockId, LocalDateTime date, Money marketPrice, Money value) {
        this.transactionNumber = transactionNumber;
        this.stockId = stockId;
        this.date = date;
        this.marketPrice = marketPrice;
        this.value = value;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Money getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Money marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

}
