package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.investor.Investor;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorType;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedInvestor;

public class InvestorHydratator {

    PersistedInvestor dehydrate(Investor investor) {
        PersistedInvestor persistedInvestor = new PersistedInvestor();
        persistedInvestor.setFocusAreas(investor.getFocusAreas());
        persistedInvestor.setId(investor.getId().getValue());
        persistedInvestor.setType(investor.getType().toString());
        return persistedInvestor;
    }

    Investor hydrate(PersistedInvestor persistedInvestor) {
        InvestorId investorId = new InvestorId(persistedInvestor.getId());
        Investor investor = new Investor();
        investor.setId(investorId);
        InvestorType type = InvestorType.valueOf(persistedInvestor.getType());
        investor.setType(type);
        investor.setFocusAreas(persistedInvestor.getFocusAreas());
        return investor;
    }

    public PersistedInvestor update(Investor investor, PersistedInvestor persistedInvestor) {
        persistedInvestor.setFocusAreas(investor.getFocusAreas());
        persistedInvestor.setId(investor.getId().getValue());
        persistedInvestor.setType(investor.getType().toString());
        return persistedInvestor;
    }

}