package ca.ulaval.glo4002.trading.domain.money;

import ca.ulaval.glo4002.trading.application.ServiceLocator;

import java.util.Map;

public class CurrencyExchanger {
    CurrencyValueRepository currencyValueRepository;

    public CurrencyExchanger() {
        this(ServiceLocator.resolve(CurrencyValueRepository.class));
    }

    private CurrencyExchanger(CurrencyValueRepository currencyValueRepository) {
        this.currencyValueRepository = currencyValueRepository;
    }

    public Money convertToCAD(Money money) {
        float rate = currencyValueRepository.getCurrencyValueInCAD(money.getCurrency());
        Money cad = money.multiply(rate);
        cad.setCurrency(Currency.CAD);
        return cad;
    }

    public Money convertMapToCAD(Map<Currency, Money> moneyByCurrency) {
        float total = 0f;
        for (Map.Entry<Currency, Money> entry : moneyByCurrency.entrySet()) {
            total += convertToFloatCAD(entry.getValue());
        }
        return new Money(total, Currency.CAD);
    }

    private float convertToFloatCAD(Money money) {
        float rate = currencyValueRepository.getCurrencyValueInCAD(money.getCurrency());
        return money.multiply(rate).getAmount().floatValue();
    }
}
