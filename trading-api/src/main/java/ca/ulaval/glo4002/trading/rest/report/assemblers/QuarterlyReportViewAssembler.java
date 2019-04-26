package ca.ulaval.glo4002.trading.rest.report.assemblers;

import ca.ulaval.glo4002.trading.application.report.QuarterlyReportDTO;
import ca.ulaval.glo4002.trading.application.report.StockMarketReturnDTO;
import ca.ulaval.glo4002.trading.rest.report.views.responses.QuarterlyReportResponse;
import ca.ulaval.glo4002.trading.rest.report.views.responses.StockMarketReturnResponse;

import java.util.ArrayList;
import java.util.List;

public class QuarterlyReportViewAssembler {

    private StockMarketReturnViewAssembler stockMarketReturnViewAssembler;

    public QuarterlyReportViewAssembler() {
        this.stockMarketReturnViewAssembler = new StockMarketReturnViewAssembler();
    }

    public QuarterlyReportResponse from(QuarterlyReportDTO quarterlyReportDTO) {
        QuarterlyReportResponse quarterlyReportResponse = new QuarterlyReportResponse();
        quarterlyReportResponse.setPeriod(quarterlyReportDTO.getQuarter());
        List<StockMarketReturnResponse> stockMarketReturnResponses = new ArrayList<>();
        for (StockMarketReturnDTO stockMarketReturnDTO : quarterlyReportDTO.getStocksAccountDTOS()) {
            StockMarketReturnResponse stockMarketReturnResponse = stockMarketReturnViewAssembler.from(stockMarketReturnDTO);
            stockMarketReturnResponses.add(stockMarketReturnResponse);
        }
        quarterlyReportResponse.setStocksAccount(stockMarketReturnResponses);
        return quarterlyReportResponse;
    }

}
