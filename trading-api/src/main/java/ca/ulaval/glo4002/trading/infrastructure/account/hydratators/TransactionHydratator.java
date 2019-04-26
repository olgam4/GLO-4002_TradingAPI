package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedTransaction;

import java.time.LocalDateTime;

class TransactionHydratator {

    private StockIdHydratator stockIdHydratator;

    TransactionHydratator() {
        this.stockIdHydratator = new StockIdHydratator();
    }

    PersistedTransaction dehydrate(Transaction transaction) {
        PersistedTransaction persistedTransaction = new PersistedTransaction();
        persistedTransaction.setDate(transaction.getDate().toString());
        persistedTransaction.setPrice(transaction.getPrice().getAmount().floatValue());
        persistedTransaction.setQuantity(transaction.getQuantity());
        if (transaction.getReferencedTransactionNumber() != null) {
            persistedTransaction.setReferencedTransactionNumber(transaction.getReferencedTransactionNumber().toString());
        }
        persistedTransaction.setStockId(stockIdHydratator.dehydrate(transaction.getStockId()));
        persistedTransaction.setSubTotal(transaction.getSubTotal().getAmount().floatValue());
        persistedTransaction.setTotal(transaction.getTotal().getAmount().floatValue());
        persistedTransaction.setType(transaction.getType().toString());
        persistedTransaction.setTransactionNumber(transaction.getTransactionNumber().toString());
        return persistedTransaction;
    }

    Transaction hydrate(PersistedTransaction persistedTransaction) {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.parse(persistedTransaction.getDate()));
        transaction.setPrice(new Money(persistedTransaction.getPrice()));
        transaction.setQuantity(persistedTransaction.getQuantity());
        if (persistedTransaction.getReferencedTransactionNumber() != null) {
            transaction.setReferencedTransactionNumber(new TransactionNumber(persistedTransaction.getReferencedTransactionNumber()));
        }
        transaction.setStockId(stockIdHydratator.hydrate(persistedTransaction.getStockId()));
        transaction.setTransactionNumber(new TransactionNumber(persistedTransaction.getTransactionNumber()));
        transaction.setType(TransactionType.valueOf(persistedTransaction.getType()));
        transaction.setSubTotal(new Money(persistedTransaction.getSubTotal()));
        transaction.setTotal(new Money(persistedTransaction.getTotal()));
        return transaction;
    }

}
