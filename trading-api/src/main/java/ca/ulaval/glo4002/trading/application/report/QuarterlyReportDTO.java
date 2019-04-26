package ca.ulaval.glo4002.trading.application.report;

import ca.ulaval.glo4002.trading.domain.report.Quarter;

import java.util.ArrayList;
import java.util.List;

public class QuarterlyReportDTO {

    private Quarter quarter;
    private List<StockMarketReturnDTO> stocksAccountDTOS = new ArrayList<>();

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }

    public List<StockMarketReturnDTO> getStocksAccountDTOS() {
        return stocksAccountDTOS;
    }

    public void setStocksAccountDTOS(List<StockMarketReturnDTO> stocksAccountDTOS) {
        this.stocksAccountDTOS = stocksAccountDTOS;
    }

}
