package ca.ulaval.glo4002.trading.tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.core.Application;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TradingApplicationTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new TradingApplication();
    }

    protected JsonObject toJsonObject(String json) {
        return new Gson().fromJson(json, JsonObject.class);
    }

    protected void assertBaseError(String json) {
        JsonObject jsonObject = toJsonObject(json);
        List<String> expectedKeys = new ArrayList<>();
        expectedKeys.add("error");
        expectedKeys.add("description");
        assertEquals(expectedKeys, new ArrayList<>(jsonObject.keySet()));
    }

    protected void assertTransactionError(String json) {
        JsonObject jsonObject = toJsonObject(json);
        List<String> expectedKeys = new ArrayList<>();
        expectedKeys.add("error");
        expectedKeys.add("description");
        expectedKeys.add("transactionNumber");
        assertEquals(expectedKeys, new ArrayList<>(jsonObject.keySet()));
    }
}
