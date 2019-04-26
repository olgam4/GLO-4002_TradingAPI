package ca.ulaval.glo4002.trading.rest.dividend;

import ca.ulaval.glo4002.trading.application.dividend.DividendDTO;
import ca.ulaval.glo4002.trading.application.dividend.DividendPaymentDTO;
import ca.ulaval.glo4002.trading.rest.dividend.views.requests.DividendRequest;
import ca.ulaval.glo4002.trading.rest.dividend.views.responses.DividendPaymentResponse;

public class DividendViewAssembler {

    public DividendDTO from(DividendRequest dividendRequest) {
        DividendDTO dividendDTO = new DividendDTO();
        dividendDTO.setDate(dividendRequest.getDate());
        dividendDTO.setStockId(dividendRequest.getStock());
        dividendDTO.setDividendPerShare(dividendRequest.getDps());
        return dividendDTO;
    }

    public DividendPaymentResponse from(DividendPaymentDTO dividendPaymentDTO) {
        DividendPaymentResponse dividendPaymentResponse = new DividendPaymentResponse();
        dividendPaymentResponse.setMarket(dividendPaymentDTO.getStockId().getMarket());
        dividendPaymentResponse.setSymbol(dividendPaymentDTO.getStockId().getSymbol());
        dividendPaymentResponse.setMarketprice(dividendPaymentDTO.getMarketPrice());
        dividendPaymentResponse.setDividends(dividendPaymentDTO.getValue());
        return dividendPaymentResponse;
    }

}
