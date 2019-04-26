package ca.ulaval.glo4002.trading.application.report;

import ca.ulaval.glo4002.trading.application.account.CreditDomainAssembler;
import ca.ulaval.glo4002.trading.application.dividend.DividendPaymentDTO;
import ca.ulaval.glo4002.trading.application.dividend.DividendPaymentDomainAssembler;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDomainAssembler;
import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.domain.account.transaction.Transaction;
import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.report.HistoryReport;
import ca.ulaval.glo4002.trading.domain.report.Quarter;
import ca.ulaval.glo4002.trading.domain.report.StockMarketReturn;
import ca.ulaval.glo4002.trading.domain.report.StockMarketReturnReport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportConverter {

    private final TransactionDomainAssembler transactionDomainAssembler;
    private final DividendPaymentDomainAssembler dividendPaymentDomainAssembler;
    private final StockMarketReturnDomainAssembler stockMarketReturnDomainAssembler;
    private final CreditDomainAssembler creditDomainAssembler;

    public ReportConverter() {
        this.transactionDomainAssembler = new TransactionDomainAssembler();
        this.dividendPaymentDomainAssembler = new DividendPaymentDomainAssembler();
        this.stockMarketReturnDomainAssembler = new StockMarketReturnDomainAssembler();
        this.creditDomainAssembler = new CreditDomainAssembler();
    }

    public DailyReportDTO convertToDailyReportDTO(HistoryReport historyReport) {
        DailyReportDTO dailyReportDTO = new DailyReportDTO();
        dailyReportDTO.setDate(historyReport.getPeriod().getEnding());
        dailyReportDTO.setBalance(creditDomainAssembler.from(historyReport.getBalance()));
        dailyReportDTO.setPortfolioValue(historyReport.getPortfolioValue());
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (Transaction transaction : historyReport.getTransactions()) {
            TransactionDTO transactionDTO = transactionDomainAssembler.from(transaction);
            transactionDTOS.add(transactionDTO);
        }
        dailyReportDTO.setTransactionDTOS(transactionDTOS);
        List<DividendPaymentDTO> dividendPaymentDTOS = new ArrayList<>();
        for (DividendPayment dividendPayment : historyReport.getStocks()) {
            DividendPaymentDTO dividendPaymentDTO = dividendPaymentDomainAssembler.from(dividendPayment);
            dividendPaymentDTOS.add(dividendPaymentDTO);
        }
        dailyReportDTO.setDividendPaymentDTOS(dividendPaymentDTOS);
        return dailyReportDTO;
    }

    public QuarterlyReportDTO convertToQuarterlyReportDTO(StockMarketReturnReport stockMarketReturnReport) {
        QuarterlyReportDTO quarterlyReportDTO = new QuarterlyReportDTO();
        Period period = stockMarketReturnReport.getPeriod();
        LocalDateTime ending = period.getEnding();
        Quarter quarter = new Quarter(ending);
        quarterlyReportDTO.setQuarter(quarter);
        List<StockMarketReturnDTO> stocksAccountDTOS = new ArrayList<>();
        for (StockMarketReturn stockMarketReturn : stockMarketReturnReport.getStockMarketReturns()) {
            StockMarketReturnDTO stockMarketReturnDTO = stockMarketReturnDomainAssembler.from(stockMarketReturn);
            stocksAccountDTOS.add(stockMarketReturnDTO);
        }
        quarterlyReportDTO.setStocksAccountDTOS(stocksAccountDTOS);
        return quarterlyReportDTO;
    }

}
