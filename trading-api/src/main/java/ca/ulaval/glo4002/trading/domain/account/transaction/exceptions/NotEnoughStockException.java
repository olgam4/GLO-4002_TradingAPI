package ca.ulaval.glo4002.trading.domain.account.transaction.exceptions;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

public class NotEnoughStockException extends TransactionException {

    private final StockId stockId;

    public NotEnoughStockException(TransactionNumber transactionNumber, StockId stockId) {
        super(transactionNumber);
        this.stockId = stockId;
    }

    public StockId getStockId() {
        return stockId;
    }

}