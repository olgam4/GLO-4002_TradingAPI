package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumbers;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedTransactionHistory;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedTransactionNumbers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TransactionHistoryHydratator {

    private TransactionNumbersHydratator transactionNumbersHydratator;

    TransactionHistoryHydratator() {
        this.transactionNumbersHydratator = new TransactionNumbersHydratator();
    }

    PersistedTransactionHistory dehydrate(Map<TransactionNumber, TransactionNumbers> history) {
        PersistedTransactionHistory persistedHistory = new PersistedTransactionHistory();
        Map<String, PersistedTransactionNumbers> persistedHistoryMap = new HashMap<>();
        history.entrySet().forEach(historyEntry -> {
            List<TransactionNumber> transactionNumbers = historyEntry.getValue().getTransactionNumberList();
            PersistedTransactionNumbers persistedTransactionNumbers = transactionNumbersHydratator.dehydrate(transactionNumbers);
            String transactionNumberString = historyEntry.getKey().toString();
            persistedHistoryMap.put(transactionNumberString, persistedTransactionNumbers);
        });
        persistedHistory.setHistory(persistedHistoryMap);
        return persistedHistory;
    }

    Map<TransactionNumber, TransactionNumbers> hydrate(PersistedTransactionHistory persistedHistory) {
        Map<TransactionNumber, TransactionNumbers> history = new HashMap<>();
        Map<String, PersistedTransactionNumbers> persistedHistoryMap = persistedHistory.getHistory();
        persistedHistoryMap.entrySet().forEach(persistedHistoryEntry -> {
            List<TransactionNumber> transactionNumbers = transactionNumbersHydratator.hydrate(persistedHistoryEntry.getValue());
            TransactionNumbers transactionHistory = new TransactionNumbers();
            transactionHistory.setTransactionNumberList(transactionNumbers);
            TransactionNumber transactionNumber = new TransactionNumber(persistedHistoryEntry.getKey());
            history.put(transactionNumber, transactionHistory);
        });
        return history;
    }

    public PersistedTransactionHistory update(Map<TransactionNumber, TransactionNumbers> history, PersistedTransactionHistory persistedHistory) {
        Map<String, PersistedTransactionNumbers> persistedHistoryMap = persistedHistory.getHistory();
        for (Map.Entry<TransactionNumber, TransactionNumbers> historyEntry : history.entrySet()) {
            String transactionNumberString = historyEntry.getKey().toString();
            List<TransactionNumber> transactionHistory = historyEntry.getValue().getTransactionNumberList();
            if (persistedHistoryMap.get(transactionNumberString) == null) {
                PersistedTransactionNumbers persistedTransactionNumbers = transactionNumbersHydratator.dehydrate(transactionHistory);
                persistedHistoryMap.put(transactionNumberString, persistedTransactionNumbers);
            } else {
                PersistedTransactionNumbers persistedTransactionNumbers = persistedHistoryMap.get(transactionNumberString);
                PersistedTransactionNumbers updatedPersistedTransactionNumbers = transactionNumbersHydratator.update(transactionHistory, persistedTransactionNumbers);
                persistedHistoryMap.put(transactionNumberString, updatedPersistedTransactionNumbers);
            }
        }
        persistedHistory.setHistory(persistedHistoryMap);
        return persistedHistory;
    }

}
