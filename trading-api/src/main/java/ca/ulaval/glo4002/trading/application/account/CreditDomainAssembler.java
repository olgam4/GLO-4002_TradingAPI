package ca.ulaval.glo4002.trading.application.account;

import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

public class CreditDomainAssembler {
    public List<CreditDTO> from(Credits credits) {
        List<CreditDTO> creditDTOS = new ArrayList<>();
        credits.getMoneyByCurrency().forEach((currency, money) -> {
            String currencyString = currency.toString();
            CreditDTO creditDTO = new CreditDTO();
            creditDTO.setCurrency(currencyString);
            creditDTO.setAmount(money);
            creditDTOS.add(creditDTO);
        });
        return creditDTOS;
    }

    public Credits from(List<CreditDTO> creditDTOS) {
        List<Money> monies = new ArrayList<>();
        creditDTOS.forEach(creditDTO -> {
            monies.add(new Money(creditDTO.getAmount().getAmount().doubleValue(), Currency.create(creditDTO.getCurrency())));
        });
        return new Credits(monies);
    }
}
