package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Map;

@Entity
public class PersistedWallet extends PersistedBaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private Map<String, PersistedTransaction> transactions;
    @OneToOne(cascade = CascadeType.ALL)
    private PersistedTransactionHistory history;

    public Map<String, PersistedTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<String, PersistedTransaction> transactions) {
        this.transactions = transactions;
    }

    public PersistedTransactionHistory getHistory() {
        return history;
    }

    public void setHistory(PersistedTransactionHistory history) {
        this.history = history;
    }

}
