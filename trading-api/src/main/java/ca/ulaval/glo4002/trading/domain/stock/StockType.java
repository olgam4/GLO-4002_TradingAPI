package ca.ulaval.glo4002.trading.domain.stock;

public enum StockType {
    COMMON(0f), PREFERRED(0.1f);

    private float rate;

    StockType(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }
}
