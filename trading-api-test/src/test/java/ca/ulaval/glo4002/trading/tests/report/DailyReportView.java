package ca.ulaval.glo4002.trading.tests.report;

import ca.ulaval.glo4002.trading.tests.transaction.TransactionView;
import com.google.gson.Gson;

import java.util.List;

public class DailyReportView {
    public String date;
    public float credits;
    public float portfolioValue;
    public List<TransactionView> transactions;
    public List<DividendPaymentView> stocks;

    public class DividendPaymentView {
        public String market;
        public String symbol;
        public float marketprice;
        public float dividends;
    }

    public static DailyReportView from(String json) {
        return new Gson().fromJson(json, DailyReportView.class);
    }
}
