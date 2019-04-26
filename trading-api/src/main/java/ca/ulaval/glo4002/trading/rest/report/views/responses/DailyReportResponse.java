package ca.ulaval.glo4002.trading.rest.report.views.responses;

import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.rest.account.views.responses.CreditResponse;
import ca.ulaval.glo4002.trading.rest.dividend.views.responses.DividendPaymentResponse;
import ca.ulaval.glo4002.trading.rest.transaction.views.responses.TransactionResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"date", "credits", "portfolioValue", "transactions", "stocks"})
public class DailyReportResponse {

    private LocalDateTime date;
    private List<CreditResponse> credits;
    private Money portfolioValue;
    private List<TransactionResponse> transactions = new ArrayList<>();
    private List<DividendPaymentResponse> stocks = new ArrayList<>();

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<CreditResponse> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditResponse> credits) {
        this.credits = credits;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }

    public Money getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(Money portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public List<DividendPaymentResponse> getStocks() {
        return stocks;
    }

    public void setStocks(List<DividendPaymentResponse> stocks) {
        this.stocks = stocks;
    }

}
