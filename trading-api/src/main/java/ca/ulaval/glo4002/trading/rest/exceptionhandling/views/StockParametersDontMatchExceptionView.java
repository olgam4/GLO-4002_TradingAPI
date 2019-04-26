package ca.ulaval.glo4002.trading.rest.exceptionhandling.views;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.StockParametersDontMatchException;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.ExceptionMessage;

public class StockParametersDontMatchExceptionView extends BadRequest {

    private static final String ERROR = "STOCK_PARAMETERS_DONT_MATCH";
    private static final String DESCRIPTION = "stock parameters don't match existing ones";
    private final TransactionNumber transactionNumber;

    public StockParametersDontMatchExceptionView(StockParametersDontMatchException e) {
        this.transactionNumber = e.getTransactionNumber();
    }

    @Override
    public ExceptionMessage getMessage() {
        return new ExceptionMessage(ERROR, DESCRIPTION, transactionNumber);
    }

}
