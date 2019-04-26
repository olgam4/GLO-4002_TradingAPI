package ca.ulaval.glo4002.trading.application.transaction;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.transaction.strategies.CalculateFeesMinorMajorStrategy;
import ca.ulaval.glo4002.trading.application.transaction.strategies.CalculateTotalPurchaseStrategy;
import ca.ulaval.glo4002.trading.application.transaction.strategies.CalculateTotalSaleStrategy;
import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.TransactionInvalidDateException;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateFeesStrategy;
import ca.ulaval.glo4002.trading.domain.account.transaction.strategies.CalculateTotalStrategy;
import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.market.exceptions.MarketClosedException;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;

import java.time.LocalDateTime;

public class TransactionApplicationService {

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final MarketRepository marketRepository;
    private final TransactionDomainAssembler transactionDomainAssembler;

    public TransactionApplicationService() {
        this(
                ServiceLocator.resolve(AccountRepository.class),
                ServiceLocator.resolve(StockRepository.class),
                ServiceLocator.resolve(MarketRepository.class),
                ServiceLocator.resolve(TransactionDomainAssembler.class)
        );
    }

    TransactionApplicationService(AccountRepository accountRepository,
                                  StockRepository stockRepository,
                                  MarketRepository marketRepository,
                                  TransactionDomainAssembler transactionDomainAssembler) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.marketRepository = marketRepository;
        this.transactionDomainAssembler = transactionDomainAssembler;
    }

    public TransactionNumber purchaseTransaction(AccountNumber accountNumber, TransactionDTO transactionDTO) {
        CalculateFeesStrategy calculateFeesStrategy = new CalculateFeesMinorMajorStrategy();
        CalculateTotalStrategy calculateTotalStrategy = new CalculateTotalPurchaseStrategy();
        Transaction transaction = createTransaction(transactionDTO,
                calculateFeesStrategy, calculateTotalStrategy);
        Account account = accountRepository.findByAccountNumber(accountNumber);
        account.buy(transaction);
        accountRepository.update(account);
        return transaction.getTransactionNumber();
    }

    public TransactionNumber sellTransaction(AccountNumber accountNumber, TransactionDTO transactionDTO) {
        CalculateFeesStrategy calculateFeesStrategy = new CalculateFeesMinorMajorStrategy();
        CalculateTotalStrategy calculateTotalStrategy = new CalculateTotalSaleStrategy();
        Transaction transaction = createTransaction(transactionDTO,
                calculateFeesStrategy, calculateTotalStrategy);
        Account account = accountRepository.findByAccountNumber(accountNumber);
        account.sell(transaction);
        accountRepository.update(account);
        return transaction.getTransactionNumber();
    }

    private Transaction createTransaction(TransactionDTO transactionDTO,
                                          CalculateFeesStrategy calculateFeesStrategy,
                                          CalculateTotalStrategy calculateTotalStrategy) {
        StockId stockId = transactionDTO.getStockId();
        LocalDateTime date = transactionDTO.getDate();
        Money price = getPrice(stockId, date);
        price.setCurrency(marketRepository.findByMarket(stockId.getMarket()).getCurrency());
        transactionDTO.setPrice(price);
        transactionDTO.setCalculateFeesStrategy(calculateFeesStrategy);
        transactionDTO.setCalculateTotalStrategy(calculateTotalStrategy);
        Transaction transaction = transactionDomainAssembler.from(transactionDTO);
        String market = stockId.getMarket();
        checkIfMarketIsClose(market, date, transaction);
        return transaction;
    }

    private Money getPrice(StockId stockId, LocalDateTime date) {
        try {
            Stock stock = stockRepository.findByStockId(stockId);
            return stock.getPrice(date);
        } catch (InvalidDateException e) {
            throw new TransactionInvalidDateException();
        }
    }

    private void checkIfMarketIsClose(String marketString, LocalDateTime date, Transaction transaction) {
        Market market = marketRepository.findByMarket(marketString);
        if (market.isMarketClose(date)) {
            String marketName = market.getMarketSymbol();
            TransactionNumber transactionNumber = transaction.getTransactionNumber();
            throw new MarketClosedException(marketName, transactionNumber);
        }
    }

    public TransactionDTO getByTransactionNumber(AccountNumber accountNumber,
                                                 TransactionNumber transactionNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        Transaction transaction = account.getTransaction(transactionNumber);
        return transactionDomainAssembler.from(transaction);
    }

}
