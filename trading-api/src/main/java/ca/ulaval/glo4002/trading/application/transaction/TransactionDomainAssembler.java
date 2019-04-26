package ca.ulaval.glo4002.trading.application.transaction;

import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateFeesStrategy;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateTotalStrategy;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDateTime;

public class TransactionDomainAssembler {

    public Transaction from(TransactionDTO transactionDTO) {
        TransactionNumber referencedTransactionNumber = transactionDTO.getReferencedTransactionNumber();
        StockId stockId = transactionDTO.getStockId();
        Money price = transactionDTO.getPrice();
        long quantity = transactionDTO.getQuantity();
        LocalDateTime date = transactionDTO.getDate();
        TransactionType type = transactionDTO.getType();
        CalculateFeesStrategy feesStrategy = transactionDTO.getCalculateFeesStrategy();
        CalculateTotalStrategy totalStrategy = transactionDTO.getCalculateTotalStrategy();
        return new Transaction(referencedTransactionNumber,
                stockId, price, quantity, date, type, feesStrategy, totalStrategy);
    }

    public TransactionDTO from(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionNumber(transaction.getTransactionNumber());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setFees(transaction.getFees());
        transactionDTO.setStockId(transaction.getStockId());
        transactionDTO.setPrice(transaction.getPrice());
        transactionDTO.setReferencedTransactionNumber(transaction.getReferencedTransactionNumber());
        transactionDTO.setQuantity(transaction.getQuantity());
        return transactionDTO;
    }

}
