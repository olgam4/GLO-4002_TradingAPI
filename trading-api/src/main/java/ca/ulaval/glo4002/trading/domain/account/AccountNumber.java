package ca.ulaval.glo4002.trading.domain.account;

import ca.ulaval.glo4002.trading.domain.commons.ValueObject;

public class AccountNumber extends ValueObject {

    private final String value;

    public AccountNumber(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
