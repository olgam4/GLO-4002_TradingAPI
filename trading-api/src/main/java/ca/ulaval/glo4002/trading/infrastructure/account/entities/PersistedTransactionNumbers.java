package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class PersistedTransactionNumbers extends PersistedBaseEntity {

    @ElementCollection
    private List<String> transactionNumbers;

    public List<String> getTransactionNumbers() {
        return transactionNumbers;
    }

    public void setTransactionNumbers(List<String> transactionHistory) {
        this.transactionNumbers = transactionHistory;
    }

}
