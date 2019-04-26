package ca.ulaval.glo4002.trading.domain.account.transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionNumbers {

    private List<TransactionNumber> transactionNumberList = new ArrayList<>();

    public TransactionNumbers() {
        // For hibernate
    }

    public List<TransactionNumber> getTransactionNumberList() {
        return transactionNumberList;
    }

    public void setTransactionNumberList(List<TransactionNumber> transactionNumberList) {
        this.transactionNumberList = transactionNumberList;
    }

}
