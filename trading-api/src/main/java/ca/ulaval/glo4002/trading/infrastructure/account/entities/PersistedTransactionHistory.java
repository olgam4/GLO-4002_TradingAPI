package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Map;

@Entity
public class PersistedTransactionHistory extends PersistedBaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    Map<String, PersistedTransactionNumbers> history;

    public Map<String, PersistedTransactionNumbers> getHistory() {
        return history;
    }

    public void setHistory(Map<String, PersistedTransactionNumbers> history) {
        this.history = history;
    }

}
