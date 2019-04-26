package ca.ulaval.glo4002.trading.domain.account.creditBalance;

import ca.ulaval.glo4002.trading.domain.money.Money;

import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;

public class BalanceHistory {

    private NavigableMap<LocalDateTime, Credits> history = new TreeMap<>();

    public BalanceHistory() {
        // For hibernate
    }

    public BalanceHistory(Credits initialCreditsAmount) {
        this();
        history.put(LocalDateTime.MIN, initialCreditsAmount);
    }

    public NavigableMap<LocalDateTime, Credits> getHistory() {
        return history;
    }

    public void setHistory(NavigableMap<LocalDateTime, Credits> history) {
        this.history = history;
    }

    public Credits getBalance() {
        return history.lastEntry().getValue();
    }

    public Credits getBalance(LocalDateTime date) {
        return history.floorEntry(date).getValue();
    }

    public void addCredits(LocalDateTime date, Money money) {
        Money updatedAmount = getBalance().getBy(money.getCurrency()).add(money);
        Credits newBalance = getBalance();
        newBalance.addTo(money.getCurrency(), updatedAmount);
        history.put(date, newBalance);
    }

    public void removeCredits(LocalDateTime date, Money money) {
        Money updatedAmount = getBalance().getBy(money.getCurrency()).subtract(money);
        Credits newBalance = getBalance();
        newBalance.addTo(money.getCurrency(), updatedAmount);
        history.put(date, newBalance);
    }

}
