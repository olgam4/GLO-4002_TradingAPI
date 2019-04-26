package ca.ulaval.glo4002.trading.domain.report;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.InvestmentPosition;
import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.CurrencyExchanger;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;
import ca.ulaval.glo4002.trading.rest.report.exceptions.ReportInvalidDateException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reporter {

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final MarketRepository marketRepository;
    private final CurrencyExchanger currencyExchanger;

    public Reporter() {
        this(
                ServiceLocator.resolve(AccountRepository.class),
                ServiceLocator.resolve(StockRepository.class),
                ServiceLocator.resolve(MarketRepository.class),
                ServiceLocator.resolve(CurrencyExchanger.class)
        );
    }

    Reporter(AccountRepository accountRepository,
             StockRepository stockRepository,
             MarketRepository marketRepository,
             CurrencyExchanger currencyExchanger) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.marketRepository = marketRepository;
        this.currencyExchanger = currencyExchanger;
    }

    public HistoryReport generateHistoryReport(AccountNumber accountNumber, Period period) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        LocalDateTime ending = period.getEnding();
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsAsOf(ending);
        Money portfolioValue = computePortfolioValue(ending, investmentPositions);
        Credits balance = account.getBalanceAsOf(ending);
        List<Transaction> transactions = account.getTransactionsOver(period);
        List<DividendPayment> dividendPayments = account.getDividendPaymentsOver(period);
        return new HistoryReport(period, balance, portfolioValue, transactions, dividendPayments);
    }

    private Money computePortfolioValue(LocalDateTime date, List<InvestmentPosition> investmentPositions) {
        Money totalPortfolioValue = Money.ZERO_MONEY;
        totalPortfolioValue.setCurrency(Currency.CAD);
        for (InvestmentPosition investmentPosition : investmentPositions) {
            StockId stockId = investmentPosition.getStockId();
            long quantity = investmentPosition.getQuantity();
            Stock stock = stockRepository.findByStockId(stockId);
            Market market = marketRepository.findByMarket(stockId.getMarket());
            Money price;
            try {
                price = stock.getPrice(date);
            } catch (InvalidDateException e) {
                throw new ReportInvalidDateException(date.toLocalDate());
            }
            price.setCurrency(market.getCurrency());
            Money stockValue = price.multiply(quantity);
            Money stockValueInCad = currencyExchanger.convertToCAD(stockValue);
            totalPortfolioValue = totalPortfolioValue.add(stockValueInCad);
        }
        return totalPortfolioValue;
    }

    public StockMarketReturnReport generateStockMarketReport(AccountNumber accountNumber, Period period) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        Map<StockId, Map<Money, Long>> aggregatedInvestmentPositions = getAggregatedInvestmentPositions(account, period);
        Map<StockId, Map<Money, Money>> aggregatedDividendPayments = getAggregatedDividendPayments(account, period);
        List<StockMarketReturn> stockMarketReturns = new ArrayList<>();
        for (Map.Entry<StockId, Map<Money, Long>> position : aggregatedInvestmentPositions.entrySet()) {
            StockId stockId = position.getKey();
            Map<Money, Long> quantityMap = position.getValue();
            for (Map.Entry<Money, Long> quantityMapEntry : quantityMap.entrySet()) {
                Money purchasePrice = quantityMapEntry.getKey();
                long quantity = quantityMapEntry.getValue();
                float rateOfReturn = computeRateOfReturn(stockId, period);
                Map<Money, Money> totalDividendPayments = aggregatedDividendPayments.getOrDefault(stockId, new HashMap<>());
                Money totalDividendPayment = totalDividendPayments.getOrDefault(purchasePrice, Money.ZERO_MONEY);
                StockMarketReturn stockMarketReturn = new StockMarketReturn(stockId, rateOfReturn, totalDividendPayment, quantity);
                stockMarketReturns.add(stockMarketReturn);
            }
        }
        return new StockMarketReturnReport(period, stockMarketReturns);
    }

    private Map<StockId, Map<Money, Long>> getAggregatedInvestmentPositions(Account account, Period period) {
        List<InvestmentPosition> investmentPositions = account.getInvestmentPositionsOver(period);
        Map<StockId, Map<Money, Long>> aggregatedInvestmentPositions = new HashMap<>();
        for (InvestmentPosition investmentPosition : investmentPositions) {
            StockId stockId = investmentPosition.getStockId();
            TransactionNumber transactionNumber = investmentPosition.getTransactionNumber();
            Transaction transaction = account.getTransaction(transactionNumber);
            Money purchasePrice = transaction.getPrice();
            Map<Money, Long> quantityMap = aggregatedInvestmentPositions.getOrDefault(stockId, new HashMap<>());
            long currentQuantity = quantityMap.getOrDefault(purchasePrice, 0L);
            long investmentPositionQuantity = investmentPosition.getQuantity();
            long updatedQuantity = currentQuantity + investmentPositionQuantity;
            quantityMap.put(purchasePrice, updatedQuantity);
            aggregatedInvestmentPositions.put(stockId, quantityMap);
        }
        return aggregatedInvestmentPositions;
    }

    private Map<StockId, Map<Money, Money>> getAggregatedDividendPayments(Account account, Period period) {
        List<DividendPayment> dividendPayments = account.getDividendPaymentsOver(period);
        Map<StockId, Map<Money, Money>> aggregatedDividendPayments = new HashMap<>();
        for (DividendPayment dividendPayment : dividendPayments) {
            StockId stockId = dividendPayment.getStockId();
            TransactionNumber transactionNumber = dividendPayment.getTransactionNumber();
            Transaction transaction = account.getTransaction(transactionNumber);
            Money purchasePrice = transaction.getPrice();
            Map<Money, Money> totalDividendPaymentsMap = aggregatedDividendPayments.getOrDefault(stockId, new HashMap<>());
            Money currentValue = totalDividendPaymentsMap.getOrDefault(purchasePrice, Money.ZERO_MONEY);
            Money dividendPaymentValue = dividendPayment.getValue();
            Money updatedValue = currentValue.add(dividendPaymentValue);
            totalDividendPaymentsMap.put(purchasePrice, updatedValue);
            aggregatedDividendPayments.put(stockId, totalDividendPaymentsMap);
        }
        return aggregatedDividendPayments;
    }

    private float computeRateOfReturn(StockId stockId, Period period) {
        LocalDateTime beginning = period.getBeginning();
        LocalDateTime ending = period.getEnding();
        Stock stock = stockRepository.findByStockId(stockId);
        float stockPriceBegin;
        try {
            stockPriceBegin = stock.getPrice(beginning).getAmount().floatValue();
        } catch (InvalidDateException e) {
            throw new ReportInvalidDateException(beginning.toLocalDate());
        }
        float stockPriceEnd;
        try {
            stockPriceEnd = stock.getPrice(ending).getAmount().floatValue();
        } catch (InvalidDateException e) {
            throw new ReportInvalidDateException(ending.toLocalDate());
        }
        float rateOfReturn = (stockPriceEnd - stockPriceBegin) / stockPriceBegin * 100;
        return BigDecimal.valueOf(rateOfReturn).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

}
