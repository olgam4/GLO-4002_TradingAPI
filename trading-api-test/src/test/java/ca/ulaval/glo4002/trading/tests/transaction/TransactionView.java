package ca.ulaval.glo4002.trading.tests.transaction;

import com.google.gson.Gson;

public class TransactionView {
    public String transactionNumber;
    public String type;
    public String date;
    public float fees;
    public StockView stock;
    public float purchasedPrice;
    public float priceSold;
    public long quantity;

    public class StockView {
        public String market;
        public String symbol;
    }

    public static TransactionView from(String json) {
        return new Gson().fromJson(json, TransactionView.class);
    }
}
