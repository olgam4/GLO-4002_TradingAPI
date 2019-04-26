package ca.ulaval.glo4002.trading.rest.account;


import ca.ulaval.glo4002.trading.application.account.AccountDTO;
import ca.ulaval.glo4002.trading.application.account.CreditDTO;
import ca.ulaval.glo4002.trading.rest.account.views.requests.AccountRequest;
import ca.ulaval.glo4002.trading.rest.account.views.responses.AccountResponse;
import ca.ulaval.glo4002.trading.rest.account.views.responses.CreditResponse;
import ca.ulaval.glo4002.trading.rest.account.views.responses.InvestorProfileResponse;

import java.util.ArrayList;
import java.util.List;

public class AccountViewAssembler {

    private CreditViewAssembler creditViewAssembler;

    public AccountViewAssembler() {
        this.creditViewAssembler = new CreditViewAssembler();
    }

    public AccountDTO from(AccountRequest accountRequest) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setInvestorId(accountRequest.getInvestorId());
        accountDTO.setInvestorName(accountRequest.getInvestorName());
        List<CreditDTO> creditDTOList = new ArrayList<>();
        accountRequest.getCredits().forEach(creditRequest -> creditDTOList.add(creditViewAssembler.from(creditRequest)));
        accountDTO.setCredits(creditDTOList);
        return accountDTO;
    }

    public AccountResponse from(AccountDTO accountDTO) {
        InvestorProfileResponse investorProfileResponse = new InvestorProfileResponse();
        investorProfileResponse.setType(accountDTO.getInvestorType().toString());
        investorProfileResponse.setFocusAreas(accountDTO.getFocusAreas());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountNumber(accountDTO.getAccountNumber());
        accountResponse.setInvestorId(accountDTO.getInvestorId());
        accountResponse.setInvestorProfile(investorProfileResponse);
        List<CreditResponse> creditResponses = new ArrayList<>();
        accountDTO.getCredits().forEach(creditDTO -> creditResponses.add(creditViewAssembler.from(creditDTO)));
        accountResponse.setCredits(creditResponses);
        accountResponse.setTotal(accountDTO.getTotalCredits());
        return accountResponse;
    }

}
