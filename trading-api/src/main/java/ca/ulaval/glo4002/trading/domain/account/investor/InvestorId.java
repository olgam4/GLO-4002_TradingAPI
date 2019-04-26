package ca.ulaval.glo4002.trading.domain.account.investor;

import ca.ulaval.glo4002.trading.domain.commons.ValueObject;

public class InvestorId extends ValueObject {

    private final Long value;

    public InvestorId(String value) {
        this(Long.parseLong(value));
    }

    public InvestorId(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

}
