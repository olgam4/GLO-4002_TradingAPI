package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PersistedStockId extends PersistedBaseEntity {

    @Column
    private String market;
    @Column
    private String symbol;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
