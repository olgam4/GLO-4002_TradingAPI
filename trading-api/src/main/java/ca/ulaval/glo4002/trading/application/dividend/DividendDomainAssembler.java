package ca.ulaval.glo4002.trading.application.dividend;

import ca.ulaval.glo4002.trading.domain.account.dividend.Dividend;
import ca.ulaval.glo4002.trading.domain.stock.Stock;

import java.time.LocalDateTime;

public class DividendDomainAssembler {

    public Dividend from(DividendDTO dividendDTO) {
        Stock stock = dividendDTO.getStock();
        LocalDateTime date = dividendDTO.getDate();
        float dividendPerShare = dividendDTO.getDividendPerShare();
        return new Dividend(stock, date, dividendPerShare);
    }

}
