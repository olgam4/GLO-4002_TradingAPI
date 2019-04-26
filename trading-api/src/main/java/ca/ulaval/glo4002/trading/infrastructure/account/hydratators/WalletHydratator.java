package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.Wallet;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumbers;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedTransaction;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedTransactionHistory;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedWallet;

import java.util.HashMap;
import java.util.Map;

public class WalletHydratator {

    private TransactionHydratator transactionHydratator;
    private TransactionHistoryHydratator transactionHistoryHydratator;

    WalletHydratator() {
        this.transactionHydratator = new TransactionHydratator();
        this.transactionHistoryHydratator = new TransactionHistoryHydratator();
    }

    PersistedWallet dehydrate(Wallet wallet) {
        PersistedWallet persistedWallet = new PersistedWallet();
        Map<String, PersistedTransaction> transactions = new HashMap<>();
        for (Map.Entry<TransactionNumber, Transaction> transactionEntry : wallet.getTransactions().entrySet()) {
            transactions.put(transactionEntry.getKey().toString(), transactionHydratator.dehydrate(transactionEntry.getValue()));
        }
        persistedWallet.setTransactions(transactions);
        PersistedTransactionHistory persistedHistory = transactionHistoryHydratator.dehydrate(wallet.getHistory());
        persistedWallet.setHistory(persistedHistory);
        return persistedWallet;
    }

    Wallet hydrate(PersistedWallet persistedWallet) {
        Wallet wallet = new Wallet();
        Map<TransactionNumber, Transaction> transactions = new HashMap<>();
        for (Map.Entry<String, PersistedTransaction> transactionEntry : persistedWallet.getTransactions().entrySet()) {
            TransactionNumber transactionNumber = new TransactionNumber(transactionEntry.getKey());
            transactions.put(transactionNumber, transactionHydratator.hydrate(transactionEntry.getValue()));
        }
        wallet.setTransactions(transactions);
        Map<TransactionNumber, TransactionNumbers> history = transactionHistoryHydratator.hydrate(persistedWallet.getHistory());
        wallet.setHistory(history);
        return wallet;
    }

    public PersistedWallet update(Wallet wallet, PersistedWallet persistedWallet) {
        Map<String, PersistedTransaction> persistedTransactions = persistedWallet.getTransactions();
        for (Map.Entry<TransactionNumber, Transaction> transactionEntry : wallet.getTransactions().entrySet()) {
            String transactionNumberString = transactionEntry.getKey().toString();
            if (!persistedTransactions.keySet().contains(transactionNumberString)) {
                PersistedTransaction persistedTransaction = transactionHydratator.dehydrate(transactionEntry.getValue());
                persistedTransactions.put(transactionNumberString, persistedTransaction);
            }
        }
        persistedWallet.setTransactions(persistedTransactions);
        PersistedTransactionHistory persistedHistory = persistedWallet.getHistory();
        Map<TransactionNumber, TransactionNumbers> history = wallet.getHistory();
        PersistedTransactionHistory updatedPersistedTransactionHistory = transactionHistoryHydratator.update(history, persistedHistory);
        persistedWallet.setHistory(updatedPersistedTransactionHistory);
        return persistedWallet;
    }

}
