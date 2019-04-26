package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class PersistedTransaction extends PersistedBaseEntity {

    @Column
    private String transactionNumber;
    @Column
    private String referencedTransactionNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private PersistedStockId stockId;
    @Column
    private long quantity;
    @Column
    private String date;
    @Column
    private String type;
    @Column
    private float price;
    @Column
    private float subTotal;
    @Column
    private float total;

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getReferencedTransactionNumber() {
        return referencedTransactionNumber;
    }

    public void setReferencedTransactionNumber(String referencedTransactionNumber) {
        this.referencedTransactionNumber = referencedTransactionNumber;
    }

    public PersistedStockId getStockId() {
        return stockId;
    }

    public void setStockId(PersistedStockId stockId) {
        this.stockId = stockId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
