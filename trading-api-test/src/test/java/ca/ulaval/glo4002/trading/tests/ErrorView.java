package ca.ulaval.glo4002.trading.tests;

import com.google.gson.Gson;

public class ErrorView {
    public String error;
    public String description;
    public String transactionNumber;

    public static ErrorView from(String json) {
        return new Gson().fromJson(json, ErrorView.class);
    }
}