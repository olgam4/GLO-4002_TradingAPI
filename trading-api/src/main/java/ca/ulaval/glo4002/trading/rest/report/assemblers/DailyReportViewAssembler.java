package ca.ulaval.glo4002.trading.rest.report.assemblers;

import ca.ulaval.glo4002.trading.application.dividend.DividendPaymentDTO;
import ca.ulaval.glo4002.trading.application.report.DailyReportDTO;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.rest.account.CreditViewAssembler;
import ca.ulaval.glo4002.trading.rest.dividend.DividendViewAssembler;
import ca.ulaval.glo4002.trading.rest.dividend.views.responses.DividendPaymentResponse;
import ca.ulaval.glo4002.trading.rest.report.views.responses.DailyReportResponse;
import ca.ulaval.glo4002.trading.rest.transaction.TransactionViewAssembler;
import ca.ulaval.glo4002.trading.rest.transaction.views.responses.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

public class DailyReportViewAssembler {

    private final TransactionViewAssembler transactionViewAssembler;
    private final DividendViewAssembler dividendViewAssembler;
    private final CreditViewAssembler creditViewAssembler;

    public DailyReportViewAssembler() {
        this.transactionViewAssembler = new TransactionViewAssembler();
        this.dividendViewAssembler = new DividendViewAssembler();
        this.creditViewAssembler = new CreditViewAssembler();
    }

    public DailyReportResponse from(DailyReportDTO dailyReportDTO) {
        DailyReportResponse dailyReportResponse = new DailyReportResponse();
        dailyReportResponse.setDate(dailyReportDTO.getDate());
        dailyReportResponse.setCredits(creditViewAssembler.from(dailyReportDTO.getBalance()));
        dailyReportResponse.setPortfolioValue(dailyReportDTO.getPortfolioValue());
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (TransactionDTO dto : dailyReportDTO.getTransactionDTOS()) {
            TransactionResponse response = transactionViewAssembler.from(dto);
            transactionResponses.add(response);
        }
        dailyReportResponse.setTransactions(transactionResponses);
        List<DividendPaymentResponse> dividendPaymentResponses = new ArrayList<>();
        for (DividendPaymentDTO dto : dailyReportDTO.getDividendPaymentDTOS()) {
            DividendPaymentResponse response = dividendViewAssembler.from(dto);
            dividendPaymentResponses.add(response);
        }
        dailyReportResponse.setStocks(dividendPaymentResponses);
        return dailyReportResponse;
    }

}
