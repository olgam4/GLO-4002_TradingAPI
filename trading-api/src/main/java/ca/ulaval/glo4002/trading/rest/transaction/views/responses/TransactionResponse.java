package ca.ulaval.glo4002.trading.rest.transaction.views.responses;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"transactionNumber", "type", "date", "fees", "stock", "purchasedPrice", "priceSold", "quantity"})
public class TransactionResponse {

    private TransactionNumber transactionNumber;
    private TransactionType type;
    private LocalDateTime date;
    private Money fees;
    private StockId stock;
    private Money price;
    private long quantity;

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

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

    public Money getFees() {
        return fees;
    }

    public void setFees(Money fees) {
        this.fees = fees;
    }

    public StockId getStock() {
        return stock;
    }

    public void setStock(StockId stock) {
        this.stock = stock;
    }

    @JsonProperty("purchasedPrice")
    public Money getPurchasedPrice() {
        if (type == TransactionType.BUY) {
            return price;
        } else {
            return null;
        }
    }

    @JsonProperty("priceSold")
    public Money getPriceSold() {
        if (type == TransactionType.SELL) {
            return price;
        } else {
            return null;
        }
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

}