package ca.ulaval.glo4002.trading.domain.account;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.commons.ValueObject;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

public class InvestmentPosition extends ValueObject {

    private TransactionNumber transactionNumber;
    private StockId stockId;
    private Long quantity;

    public InvestmentPosition(TransactionNumber transactionNumber, StockId stockId, long quantity) {
        this.transactionNumber = transactionNumber;
        this.stockId = stockId;
        this.quantity = quantity;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
