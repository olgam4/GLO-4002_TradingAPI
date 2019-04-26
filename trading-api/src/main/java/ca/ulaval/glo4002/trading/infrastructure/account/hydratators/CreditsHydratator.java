package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedCredits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditsHydratator {

    public PersistedCredits deshydrate(Credits credits) {
        PersistedCredits persistedCredits = new PersistedCredits();
        Map<String, Double> persistedCreditsMap = new HashMap<>();
        credits.getMoneyByCurrency().forEach((currency, money) -> {
            persistedCreditsMap.put(currency.toString(), money.getAmount().doubleValue());
        });
        persistedCredits.setCredits(persistedCreditsMap);
        return persistedCredits;
    }

    public Credits rehydrate(PersistedCredits persistedCredits) {
        List<Money> monies = new ArrayList<>();
        persistedCredits.getCredits().forEach((persistedCurrency, persistedAmount) -> {
            monies.add(new Money(persistedAmount, Currency.valueOf(persistedCurrency)));
        });
        return new Credits(monies);

    }

    public PersistedCredits update(Credits credits, PersistedCredits persistedCredits) {
        Map<String, Double> persistedCreditsMap = new HashMap<>();
        credits.getMoneyByCurrency().forEach((currency, money) -> {
            persistedCreditsMap.put(currency.toString(), money.getAmount().doubleValue());
        });
        persistedCredits.setCredits(persistedCreditsMap);
        return persistedCredits;
    }
}
