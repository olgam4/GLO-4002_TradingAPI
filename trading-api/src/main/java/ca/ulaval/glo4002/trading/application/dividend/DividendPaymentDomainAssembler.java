package ca.ulaval.glo4002.trading.application.dividend;

import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;

public class DividendPaymentDomainAssembler {

    public DividendPaymentDTO from(DividendPayment dividendPayment) {
        DividendPaymentDTO dividendPaymentDTO = new DividendPaymentDTO();
        dividendPaymentDTO.setDate(dividendPayment.getDate());
        dividendPaymentDTO.setStockId(dividendPayment.getStockId());
        dividendPaymentDTO.setMarketPrice(dividendPayment.getMarketPrice());
        dividendPaymentDTO.setValue(dividendPayment.getValue());
        return dividendPaymentDTO;
    }

}
