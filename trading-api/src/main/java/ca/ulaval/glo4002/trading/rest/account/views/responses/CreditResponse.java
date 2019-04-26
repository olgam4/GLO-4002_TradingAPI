package ca.ulaval.glo4002.trading.rest.account.views.responses;

import ca.ulaval.glo4002.trading.domain.money.Money;

public class CreditResponse {
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
