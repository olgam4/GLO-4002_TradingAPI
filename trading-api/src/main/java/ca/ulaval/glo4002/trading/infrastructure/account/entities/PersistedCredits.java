package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Map;

@Entity
public class PersistedCredits extends PersistedBaseEntity {
    @ElementCollection
    private Map<String, Double> credits;

    public Map<String, Double> getCredits() {
        return credits;
    }

    public void setCredits(Map<String, Double> credits) {
        this.credits = credits;
    }
}
