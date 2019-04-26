package ca.ulaval.glo4002.trading.application.report;

import ca.ulaval.glo4002.trading.application.account.CreditDTO;
import ca.ulaval.glo4002.trading.application.dividend.DividendPaymentDTO;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.domain.money.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DailyReportDTO {

    private LocalDateTime date;
    private List<CreditDTO> balance;
    private Money portfolioValue;
    private List<TransactionDTO> transactionDTOS = new ArrayList<>();
    private List<DividendPaymentDTO> dividendPaymentDTOS = new ArrayList<>();

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<CreditDTO> getBalance() {
        return balance;
    }

    public void setBalance(List<CreditDTO> balance) {
        this.balance = balance;
    }

    public List<TransactionDTO> getTransactionDTOS() {
        return transactionDTOS;
    }

    public void setTransactionDTOS(List<TransactionDTO> transactionDTOS) {
        this.transactionDTOS = transactionDTOS;
    }

    public Money getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(Money portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public List<DividendPaymentDTO> getDividendPaymentDTOS() {
        return dividendPaymentDTOS;
    }

    public void setDividendPaymentDTOS(List<DividendPaymentDTO> dividendPaymentDTOS) {
        this.dividendPaymentDTOS = dividendPaymentDTOS;
    }

}
