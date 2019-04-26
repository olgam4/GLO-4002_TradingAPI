package ca.ulaval.glo4002.trading.application.report;

import ca.ulaval.glo4002.trading.domain.report.StockMarketReturn;

public class StockMarketReturnDomainAssembler {

    StockMarketReturnDTO from(StockMarketReturn stockMarketReturn) {
        StockMarketReturnDTO stockMarketReturnDTO = new StockMarketReturnDTO();
        stockMarketReturnDTO.setStockId(stockMarketReturn.getStockId());
        stockMarketReturnDTO.setRateOfReturn(stockMarketReturn.getRateOfReturn());
        stockMarketReturnDTO.setTotalDividends(stockMarketReturn.getTotalDividends());
        stockMarketReturnDTO.setQuantity(stockMarketReturn.getQuantity());
        return stockMarketReturnDTO;
    }

}
