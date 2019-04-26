package ca.ulaval.glo4002.trading.tests.report;

import com.google.gson.Gson;

import java.util.List;

public class QuarterlyReportView {
    public String period;
    public List<StockAccountView> stocksAccountView;

    public class StockAccountView {
        public String market;
        public String symbol;
        public float rateOfReturn;
        public float totalDividends;
        public long quantity;
    }

    public static QuarterlyReportView from(String json) {
        return new Gson().fromJson(json, QuarterlyReportView.class);
    }
}
