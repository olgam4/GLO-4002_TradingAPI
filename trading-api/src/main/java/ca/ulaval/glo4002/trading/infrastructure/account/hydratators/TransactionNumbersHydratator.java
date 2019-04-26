package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedTransactionNumbers;

import java.util.ArrayList;
import java.util.List;

public class TransactionNumbersHydratator {

    public PersistedTransactionNumbers dehydrate(List<TransactionNumber> transactionNumbers) {
        PersistedTransactionNumbers persistedTransactionNumbers = new PersistedTransactionNumbers();
        List<String> persistedTransactionNumbersList = new ArrayList<>();
        transactionNumbers.forEach(transactionNumber -> persistedTransactionNumbersList.add(transactionNumber.toString()));
        persistedTransactionNumbers.setTransactionNumbers(persistedTransactionNumbersList);
        return persistedTransactionNumbers;
    }

    public List<TransactionNumber> hydrate(PersistedTransactionNumbers persistedTransactionNumbers) {
        List<TransactionNumber> transactionNumbers = new ArrayList<>();
        List<String> persistedTransactionNumbersList = persistedTransactionNumbers.getTransactionNumbers();
        persistedTransactionNumbersList.forEach(transactionNumber -> {
            TransactionNumber transactionNumberEntity = new TransactionNumber(transactionNumber);
            transactionNumbers.add(transactionNumberEntity);
        });
        return transactionNumbers;
    }

    public PersistedTransactionNumbers update(List<TransactionNumber> transactionNumbers, PersistedTransactionNumbers persistedTransactionNumbers) {
        List<String> persistedTransactionNumbersList = persistedTransactionNumbers.getTransactionNumbers();
        if (persistedTransactionNumbersList == null) {
            return dehydrate(transactionNumbers);
        } else {
            List<TransactionNumber> rehydratedTransactionNumbers = hydrate(persistedTransactionNumbers);
            transactionNumbers.removeAll(rehydratedTransactionNumbers);
            transactionNumbers.forEach(transactionNumber -> persistedTransactionNumbersList.add(transactionNumber.toString()));
            persistedTransactionNumbers.setTransactionNumbers(persistedTransactionNumbersList);
            return persistedTransactionNumbers;
        }
    }

}
