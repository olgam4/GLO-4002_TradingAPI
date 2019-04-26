package ca.ulaval.glo4002.trading.domain.account.exceptions;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.commons.TradingApiException;

public class AccountNotFoundException extends TradingApiException {

    private final AccountNumber accountNumber;

    public AccountNotFoundException(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

}
