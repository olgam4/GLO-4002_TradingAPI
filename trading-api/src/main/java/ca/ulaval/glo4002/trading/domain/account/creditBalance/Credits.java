package ca.ulaval.glo4002.trading.domain.account.creditBalance;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.domain.account.exceptions.InvalidAmountException;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.CurrencyExchanger;
import ca.ulaval.glo4002.trading.domain.money.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Credits {
    private CurrencyExchanger currencyExchanger;
    private Map<Currency, Money> moneyByCurrency;

    public Credits(List<Money> monies) {
        this(
                ServiceLocator.resolve(CurrencyExchanger.class)
        );
        checkIfInvalidAmount(monies);
        this.moneyByCurrency = new HashMap<>();
        monies.forEach(money ->{
            Money currentValue = moneyByCurrency.getOrDefault(money.getCurrency(), Money.ZERO_MONEY);
            currentValue.setCurrency(money.getCurrency());
            moneyByCurrency.put(money.getCurrency(), currentValue.add(money));
        });
    }

    Credits(CurrencyExchanger currencyExchanger) {
        this.currencyExchanger = currencyExchanger;
    }

    public Money getBy(Currency currency) {
        return moneyByCurrency.get(currency);
    }
    public void addTo(Currency currency, Money money) {
        moneyByCurrency.put(currency, money);
    }

    public Map<Currency, Money> getMoneyByCurrency() {
        return moneyByCurrency;
    }

    public void setMoneyByCurrency(Map<Currency, Money> moneyByCurrency) {
        this.moneyByCurrency = moneyByCurrency;
    }

    private void checkIfInvalidAmount(List<Money> credits) {
        for (Money creditAmount : credits) {
            if (isInvalidAmount(creditAmount)) {
                throw new InvalidAmountException();
            }
        }

    }

    private boolean isInvalidAmount(Money money) {
        return money.isLowerThanOrEqualTo(Money.ZERO_MONEY);
    }

    public Money getTotalCADValue() {
        return currencyExchanger.convertMapToCAD(moneyByCurrency);
    }
}
