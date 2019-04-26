package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class PersistedInvestor extends PersistedBaseEntity {

    @Column
    private Long id;
    @Column
    private String type;
    @ElementCollection
    private List<String> focusAreas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getFocusAreas() {
        return focusAreas;
    }

    public void setFocusAreas(List<String> focusAreas) {
        this.focusAreas = focusAreas;
    }

}