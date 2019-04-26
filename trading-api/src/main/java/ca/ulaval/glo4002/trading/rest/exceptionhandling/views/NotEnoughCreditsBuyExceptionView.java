package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.NotEnoughCreditsBuyException;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class NotEnoughCreditsBuyExceptionView extends BadRequest {

    private static final String ERROR = "NOT_ENOUGH_CREDITS";
    private static final String DESCRIPTION = "not enough credits in wallet";
    private final TransactionNumber transactionNumber;

    public NotEnoughCreditsBuyExceptionView(NotEnoughCreditsBuyException e) {
        this.transactionNumber = e.getTransactionNumber();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, DESCRIPTION, transactionNumber);
    }

}
