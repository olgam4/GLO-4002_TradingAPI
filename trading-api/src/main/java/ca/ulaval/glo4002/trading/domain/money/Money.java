package ca.ulaval.glo4002.trading.domain.money;

import ca.ulaval.glo4002.trading.domain.commons.ValueObject;

import java.math.BigDecimal;

public class Money extends ValueObject {

    public static final Money ZERO_MONEY = new Money(0, Currency.UNKNOWN);

    private final BigDecimal amount;

    private Currency currency;

    public Money(double amount, Currency currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

    public Money(double amount) {
        this(BigDecimal.valueOf(amount), Currency.UNKNOWN);
    }

    private Money(BigDecimal bigDecimal, Currency currency) {
        this.amount = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(Money otherMoney) {
        Currency currencyToUse = verifyCurrency(this.currency, otherMoney.currency);
        return new Money(amount.add(otherMoney.amount), currencyToUse);
    }

    public Money subtract(Money otherMoney) {
        Currency currencyToUse = verifyCurrency(this.currency, otherMoney.currency);
        return new Money(amount.subtract(otherMoney.amount), currencyToUse);
    }

    public Money multiply(float number) {
        return new Money(amount.floatValue() * number, this.currency);
    }

    public Money divide(Money otherMoney) {
        return new Money(amount.divide(otherMoney.amount, 2, BigDecimal.ROUND_HALF_UP), this.currency);
    }

    public Money negate() {
        return new Money(amount.negate(), this.currency);
    }

    public boolean isGreaterThan(Money otherMoney) {
        return this.compareTo(otherMoney) > 0;
    }

    public boolean isGreaterThanOrEqualTo(Money otherMoney) {
        return isGreaterThan(otherMoney) || equals(otherMoney);
    }

    public boolean isLowerThan(Money otherMoney) {
        return this.compareTo(otherMoney) < 0;
    }

    public boolean isLowerThanOrEqualTo(Money otherMoney) {
        return isLowerThan(otherMoney) || equals(otherMoney);
    }

    public Money abs() {
        return new Money(amount.abs(), this.currency);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    private Currency verifyCurrency(Currency currency, Currency otherCurrency) {
        Currency currencyToUse;
        if(currency == Currency.UNKNOWN) {
            if(otherCurrency == Currency.UNKNOWN) {
                currencyToUse = Currency.UNKNOWN;
            } else {
                currencyToUse = otherCurrency;
            }
        } else {
            currencyToUse = currency;
        }
        return currencyToUse;
    }
}
