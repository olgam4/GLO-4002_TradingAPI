package ca.ulaval.glo4002.trading.infrastructure.currencyValue;

import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.CurrencyValueRepository;

public class LocalCurrencyValueRepository implements CurrencyValueRepository {
    @Override
    public float getCurrencyValueInCAD(Currency currency) {
        return CurrencyExchangeRate.valueOf(currency.toString()).getRate();
    }
}