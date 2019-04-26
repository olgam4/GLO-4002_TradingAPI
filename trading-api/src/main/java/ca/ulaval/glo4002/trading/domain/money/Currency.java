package ca.ulaval.glo4002.trading.domain.money;

public enum Currency {
    CAD, USD, JPY, CHF, UNKNOWN;

    public static Currency create(String value) {
        try {
            return Currency.valueOf(value);
        } catch (Exception e) {
            return Currency.UNKNOWN;
        }
    }
}
