package ca.ulaval.glo4002.trading.tests.account;

import com.google.gson.Gson;

import java.util.List;

public class AccountView {
    public String accountNumber;
    public long investorId;
    public InvestorProfile investorProfile;
    public float credits;

    public class InvestorProfile {
        public String type;
        public List<String> focusAreas;
    }

    public static AccountView from(String json) {
        return new Gson().fromJson(json, AccountView.class);
    }
}