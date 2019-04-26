package ca.ulaval.glo4002.trading.rest.account.views.requests;

import ca.ulaval.glo4002.trading.domain.money.Money;

public class CreditRequest {
    private String currency;
    private Money amount;

    public String getCurrency() {
        return currency;
    }

    public Money getAmount() {
        return amount;
    }
}
