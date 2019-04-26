package ca.ulaval.glo4002.trading.infrastructure.currencyValue;

public enum CurrencyExchangeRate {
    CAD(1f), USD(1.31f), CHF(1.45f), JPY(0.01f), UNKNOWN(0f);

    private float rate;

    CurrencyExchangeRate(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }
}
