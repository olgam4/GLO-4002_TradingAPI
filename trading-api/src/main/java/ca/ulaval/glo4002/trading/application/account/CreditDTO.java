package ca.ulaval.glo4002.trading.application.account;

import ca.ulaval.glo4002.trading.domain.money.Money;

public class CreditDTO {
    private String currency;
    private Money amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
