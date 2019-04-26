package ca.ulaval.glo4002.trading.rest.transaction.views.requests;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.rest.databind.deserializers.QuantityDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

public class TransactionRequest {

    private TransactionType type;
    private LocalDateTime date;
    private StockId stock;
    private TransactionNumber transactionNumber;
    @JsonDeserialize(using = QuantityDeserializer.class)
    private long quantity;

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StockId getStock() {
        return stock;
    }

    public void setStock(StockId stock) {
        this.stock = stock;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
