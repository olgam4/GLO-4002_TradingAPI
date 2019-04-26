package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.creditBalance.BalanceHistory;
import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedBalanceHistory;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedCredits;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class BalanceHistoryHydratator {

    private CreditsHydratator creditsHydratator;

    public BalanceHistoryHydratator() {
        this.creditsHydratator = new CreditsHydratator();
    }

    PersistedBalanceHistory dehydrate(BalanceHistory balanceHistory) {
        PersistedBalanceHistory persistedBalanceHistory = new PersistedBalanceHistory();
        Map<String, PersistedCredits> persistedBalanceHistoryMap = new HashMap<>();
        for (Map.Entry<LocalDateTime, Credits> entry : balanceHistory.getHistory().entrySet()) {
            String key = entry.getKey().toString();
            PersistedCredits persistedCredits = creditsHydratator.deshydrate(entry.getValue());
            persistedBalanceHistoryMap.put(key, persistedCredits);
        }
        persistedBalanceHistory.setHistory(persistedBalanceHistoryMap);
        return persistedBalanceHistory;
    }

    BalanceHistory hydrate(PersistedBalanceHistory persistedBalanceHistory) {
        BalanceHistory balanceHistory = new BalanceHistory();
        NavigableMap<LocalDateTime, Credits> balanceHistoryMap = new TreeMap<>();
        for (Map.Entry<String, PersistedCredits> entry : persistedBalanceHistory.getHistory().entrySet()) {
            LocalDateTime key = LocalDateTime.parse(entry.getKey());
            Credits credits = creditsHydratator.rehydrate(entry.getValue());
            balanceHistoryMap.put(key, credits);
        }
        balanceHistory.setHistory(balanceHistoryMap);
        return balanceHistory;
    }

    public PersistedBalanceHistory update(BalanceHistory balanceHistory, PersistedBalanceHistory persistedBalanceHistory) {
        Map<String, PersistedCredits> persistedHistory = new HashMap<>();
        for (Map.Entry<LocalDateTime, Credits> historyEntry : balanceHistory.getHistory().entrySet()) {
            String dateString = historyEntry.getKey().toString();
            PersistedCredits persistedCredits = creditsHydratator.deshydrate(historyEntry.getValue());
            persistedHistory.put(dateString, persistedCredits);
        }
        persistedBalanceHistory.setHistory(persistedHistory);
        return persistedBalanceHistory;
    }

}
