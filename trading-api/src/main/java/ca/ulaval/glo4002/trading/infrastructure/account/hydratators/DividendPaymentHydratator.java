package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedDividendPayment;

import java.time.LocalDateTime;

public class DividendPaymentHydratator {

    private StockIdHydratator stockIdHydratator;

    DividendPaymentHydratator() {
        this.stockIdHydratator = new StockIdHydratator();
    }

    PersistedDividendPayment dehydrate(DividendPayment dividendPayment) {
        PersistedDividendPayment persistedDividendPayment = new PersistedDividendPayment();
        persistedDividendPayment.setTransactionNumber(dividendPayment.getTransactionNumber().getValue().toString());
        persistedDividendPayment.setStockId(stockIdHydratator.dehydrate(dividendPayment.getStockId()));
        persistedDividendPayment.setDate(dividendPayment.getDate().toString());
        persistedDividendPayment.setMarketPrice(dividendPayment.getMarketPrice().getAmount().floatValue());
        persistedDividendPayment.setValue(dividendPayment.getValue().getAmount().floatValue());
        return persistedDividendPayment;
    }

    DividendPayment hydrate(PersistedDividendPayment persistedDividendPayment) {
        DividendPayment dividendPayment = new DividendPayment();
        String transactionNumberString = persistedDividendPayment.getTransactionNumber();
        dividendPayment.setTransactionNumber(new TransactionNumber(transactionNumberString));
        dividendPayment.setStockId(stockIdHydratator.hydrate(persistedDividendPayment.getStockId()));
        dividendPayment.setDate(LocalDateTime.parse(persistedDividendPayment.getDate()));
        dividendPayment.setMarketPrice(new Money(persistedDividendPayment.getMarketPrice()));
        dividendPayment.setValue(new Money(persistedDividendPayment.getValue()));
        return dividendPayment;
    }

}
