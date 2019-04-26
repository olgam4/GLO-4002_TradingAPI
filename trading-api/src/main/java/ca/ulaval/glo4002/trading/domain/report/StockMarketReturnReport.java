package ca.ulaval.glo4002.trading.domain.report;

import ca.ulaval.glo4002.trading.domain.commons.Period;

import java.util.List;

public class StockMarketReturnReport {

    private final Period period;
    private final List<StockMarketReturn> stockMarketReturns;

    public StockMarketReturnReport(Period period, List<StockMarketReturn> stockMarketReturns) {
        this.period = period;
        this.stockMarketReturns = stockMarketReturns;
    }

    public Period getPeriod() {
        return period;
    }

    public List<StockMarketReturn> getStockMarketReturns() {
        return stockMarketReturns;
    }

}
