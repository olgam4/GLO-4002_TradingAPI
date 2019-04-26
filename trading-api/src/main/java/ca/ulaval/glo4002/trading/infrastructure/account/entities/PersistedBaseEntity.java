package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class PersistedBaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long persistedId;

    public Long getPersistedId() {
        return persistedId;
    }

    public void setPersistedId(Long persistedId) {
        this.persistedId = persistedId;
    }

}