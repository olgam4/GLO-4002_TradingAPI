package ca.ulaval.glo4002.trading.rest.report.assemblers;

import ca.ulaval.glo4002.trading.application.report.StockMarketReturnDTO;
import ca.ulaval.glo4002.trading.rest.report.views.responses.StockMarketReturnResponse;

public class StockMarketReturnViewAssembler {

    StockMarketReturnResponse from(StockMarketReturnDTO stockMarketReturnDTO) {
        StockMarketReturnResponse stockMarketReturnResponse = new StockMarketReturnResponse();
        stockMarketReturnResponse.setMarket(stockMarketReturnDTO.getStockId().getMarket());
        stockMarketReturnResponse.setSymbol(stockMarketReturnDTO.getStockId().getSymbol());
        stockMarketReturnResponse.setRateOfReturn(stockMarketReturnDTO.getRateOfReturn());
        stockMarketReturnResponse.setTotalDividends(stockMarketReturnDTO.getTotalDividends());
        stockMarketReturnResponse.setQuantity(stockMarketReturnDTO.getQuantity());
        return stockMarketReturnResponse;
    }

}
