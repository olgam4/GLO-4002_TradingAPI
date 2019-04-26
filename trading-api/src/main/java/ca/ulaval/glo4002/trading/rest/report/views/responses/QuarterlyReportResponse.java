package ca.ulaval.glo4002.trading.rest.report.views.responses;

import ca.ulaval.glo4002.trading.domain.report.Quarter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"period", "stocksAccount"})
public class QuarterlyReportResponse {

    private Quarter period;
    private List<StockMarketReturnResponse> stocksAccount = new ArrayList<>();

    public Quarter getPeriod() {
        return period;
    }

    public void setPeriod(Quarter period) {
        this.period = period;
    }

    public List<StockMarketReturnResponse> getStocksAccount() {
        return stocksAccount;
    }

    public void setStocksAccount(List<StockMarketReturnResponse> stocksAccount) {
        this.stocksAccount = stocksAccount;
    }

}
