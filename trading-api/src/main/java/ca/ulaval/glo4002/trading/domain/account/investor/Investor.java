package ca.ulaval.glo4002.trading.domain.account.investor;

import java.util.ArrayList;
import java.util.List;

public class Investor {

    private InvestorId id;
    private InvestorType type;
    private List<String> focusAreas = new ArrayList<>();

    public Investor() {
        // For hibernate
    }

    public Investor(InvestorId investorId) {
        this.id = investorId;
        this.type = InvestorType.CONSERVATIVE;
    }

    public InvestorId getId() {
        return id;
    }

    public void setId(InvestorId id) {
        this.id = id;
    }

    public InvestorType getType() {
        return type;
    }

    public void setType(InvestorType type) {
        this.type = type;
    }

    public List<String> getFocusAreas() {
        return focusAreas;
    }

    public void setFocusAreas(List<String> focusAreas) {
        this.focusAreas = focusAreas;
    }

}