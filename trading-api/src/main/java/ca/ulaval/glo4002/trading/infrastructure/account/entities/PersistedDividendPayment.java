package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class PersistedDividendPayment extends PersistedBaseEntity {

    @Column
    private String transactionNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private PersistedStockId stockId;
    @Column
    private String date;
    @Column
    private float marketPrice;
    @Column
    private float value;

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public PersistedStockId getStockId() {
        return stockId;
    }

    public void setStockId(PersistedStockId stockId) {
        this.stockId = stockId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
