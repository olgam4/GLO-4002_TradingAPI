package ca.ulaval.glo4002.trading.rest.account;

import ca.ulaval.glo4002.trading.application.account.CreditDTO;
import ca.ulaval.glo4002.trading.rest.account.views.requests.CreditRequest;
import ca.ulaval.glo4002.trading.rest.account.views.responses.CreditResponse;

import java.util.ArrayList;
import java.util.List;

public class CreditViewAssembler {
    public CreditResponse from(CreditDTO creditDTO) {
        CreditResponse creditResponse = new CreditResponse();
        creditResponse.setAmount(creditDTO.getAmount());
        creditResponse.setCurrency(creditDTO.getCurrency());
        return creditResponse;
    }

    public CreditDTO from(CreditRequest creditRequest) {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setCurrency(creditRequest.getCurrency());
        creditDTO.setAmount(creditRequest.getAmount());
        return creditDTO;
    }

    public List<CreditResponse> from(List<CreditDTO> creditDTOS) {
        List<CreditResponse> creditResponses = new ArrayList<>();
        creditDTOS.forEach(creditDTO -> {
            creditResponses.add(from(creditDTO));
        });
        return creditResponses;
    }
}
