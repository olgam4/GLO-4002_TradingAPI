package ca.ulaval.glo4002.trading.domain.account.transaction;

import ca.ulaval.glo4002.trading.domain.commons.ValueObject;

import java.util.UUID;

public class TransactionNumber extends ValueObject {

    private final UUID value;

    public TransactionNumber() {
        this(UUID.randomUUID());
    }

    public TransactionNumber(String stringUUID) {
        this(UUID.fromString(stringUUID));
    }

    public TransactionNumber(UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

}
