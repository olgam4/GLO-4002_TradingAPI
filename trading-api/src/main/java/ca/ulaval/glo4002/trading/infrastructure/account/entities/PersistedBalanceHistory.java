package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Map;

@Entity
public class PersistedBalanceHistory extends PersistedBaseEntity {
    @OneToMany(cascade = CascadeType.ALL)
    private Map<String, PersistedCredits> history;

    public Map<String, PersistedCredits> getHistory() {
        return history;
    }

    public void setHistory(Map<String, PersistedCredits> balanceHistory) {
        this.history = balanceHistory;
    }

}
