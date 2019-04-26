package ca.ulaval.glo4002.trading.domain.account.dividend;

import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.exceptions.TransactionInvalidDateException;
import ca.ulaval.glo4002.trading.domain.commons.InvalidDateException;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.time.LocalDateTime;

public class Dividend {

    private Stock stock;
    private LocalDateTime date;
    private float dividendPerShare;

    public Dividend(Stock stock, LocalDateTime localDateTime, float dividendPerShare) {
        this.date = localDateTime;
        this.dividendPerShare = dividendPerShare;
        this.stock = stock;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public StockId getStockId() {
        return stock.getStockId();
    }

    public DividendPayment convertToPayment(TransactionNumber transactionNumber, long quantity) {
        Money marketPrice;
        try {
            marketPrice = stock.getPrice(date);
        } catch (InvalidDateException e) {
            throw new TransactionInvalidDateException();
        }
        Money value = calculate(quantity, marketPrice);
        return new DividendPayment(transactionNumber, stock.getStockId(), date, marketPrice, value);
    }

    private Money calculate(long quantity, Money price) {
        Money commonDividendValue = new Money(quantity * dividendPerShare, price.getCurrency());
        Money preferredDividendValue = price.multiply(quantity * stock.getDividendRate());
        return commonDividendValue.add(preferredDividendValue);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

}
