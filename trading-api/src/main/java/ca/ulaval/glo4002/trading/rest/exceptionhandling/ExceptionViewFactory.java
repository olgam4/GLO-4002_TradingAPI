package ca.ulaval.glo4002.trading.rest.exceptionhandling;

import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountAlreadyOpenException;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.exceptions.InvalidAmountException;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.*;
import ca.ulaval.glo4002.trading.domain.commons.TradingApiException;
import ca.ulaval.glo4002.trading.domain.market.exceptions.MarketClosedException;
import ca.ulaval.glo4002.trading.rest.exceptionhandling.views.*;

import javax.ws.rs.core.Response.Status;

public class ExceptionViewFactory {

    public ExceptionView createExceptionView(TradingApiException e) {
        if (e instanceof AccountAlreadyOpenException) {
            return new AccountAlreadyOpenExceptionView((AccountAlreadyOpenException) e);
        } else if (e instanceof AccountNotFoundException) {
            return new AccountNotFoundExceptionView((AccountNotFoundException) e);
        } else if (e instanceof InvalidAmountException) {
            return new InvalidAmountExceptionView();
        } else if (e instanceof InvalidTransactionNumberException) {
            return new InvalidTransactionNumberExceptionView((InvalidTransactionNumberException) e);
        } else if (e instanceof MarketClosedException) {
            return new MarketClosedExceptionView((MarketClosedException) e);
        } else if (e instanceof NotEnoughCreditsBuyException) {
            return new NotEnoughCreditsBuyExceptionView((NotEnoughCreditsBuyException) e);
        } else if (e instanceof NotEnoughCreditsSellException) {
            return new NotEnoughCreditsSellExceptionView((NotEnoughCreditsSellException) e);
        } else if (e instanceof NotEnoughStockException) {
            return new NotEnoughStockExceptionView((NotEnoughStockException) e);
        } else if (e instanceof StockNotFoundException) {
            return new StockNotFoundExceptionView((StockNotFoundException) e);
        } else if (e instanceof StockParametersDontMatchException) {
            return new StockParametersDontMatchExceptionView((StockParametersDontMatchException) e);
        } else if (e instanceof TransactionInvalidDateException) {
            return new TransactionInvalidDateExceptionView();
        } else if (e instanceof TransactionNotFoundException) {
            return new TransactionNotFoundExceptionView((TransactionNotFoundException) e);
        }

        return new ExceptionView() {
            @Override
            public Status getStatus() {
                return Status.INTERNAL_SERVER_ERROR;
            }

            @Override
            public ExceptionMessage getMessage() {
                String error = "INTERNAL_SERVER_ERROR";
                String description = e.toString();
                return new ExceptionMessage(error, description);
            }
        };
    }

}
