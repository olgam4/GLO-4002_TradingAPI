package ca.ulaval.glo4002.trading.tests;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.stock.StockId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Constants {
    // GENERAL
    public static final float DELTA = 0.001f;

    // ACCOUNT
    public static final long INVESTOR_ID_VALUE = 0;
    public static final InvestorId INVESTOR_ID = new InvestorId(INVESTOR_ID_VALUE);
    public static final String NAME = "Uncle Bob";
    public static final String EMAIL = "bob@example.com";
    public static final float VALID_CREDITS_VALUE = 1000;
    public static final Money VALID_MONEY = new Money(VALID_CREDITS_VALUE);
    public static final String ACCOUNT_NUMBER_VALUE = "UB-0";
    public static final AccountNumber ACCOUNT_NUMBER = new AccountNumber(ACCOUNT_NUMBER_VALUE);
    public static final String INVALID_ACCOUNT_NUMBER_VALUE = "INVALID";
    public static final AccountNumber INVALID_ACCOUNT_NUMBER = new AccountNumber(INVALID_ACCOUNT_NUMBER_VALUE);
    public static final float ZERO_CREDITS = 0f;
    public static final float NEGATIVE_CREDITS = -1f;

    // TRANSACTION
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    public static final String UNSUPPORTED_TYPE = "UNSUPPORTED";
    public static final String VALID_DATE_STRING = "2018-08-21T15:23:20.142Z";
    public static final LocalDateTime VALID_DATE = LocalDateTime
            .of(2018, 8, 21, 15, 23, 20, 142000000);
    public static final String INVALID_DATE = "INVALID";
    public static final LocalDateTime DATE_WITHOUT_PRICE = LocalDateTime.MIN;
    public static final String DATE_WITHOUT_PRICE_STRING = DATE_WITHOUT_PRICE.toString();
    public static final String MARKET = "NASDAQ";
    public static final String SYMBOL = "GOOGL";
    public static final StockId STOCK_ID = new StockId(MARKET, SYMBOL);
    public static final String INVALID_MARKET = "INVALID_MARKET";
    public static final String INVALID_SYMBOL = "INVALID_SYMBOL";
    public static final StockId INVALID_STOCK_ID = new StockId(INVALID_MARKET, INVALID_SYMBOL);
    public static final long VALID_QUANTITY = 100;
    public static final TransactionNumber TRANSACTION_NUMBER_BUY = new TransactionNumber();
    public static final UUID TRANSACTION_NUMBER_BUY_VALUE = TRANSACTION_NUMBER_BUY.getValue();
    public static final String TRANSACTION_NUMBER_BUY_VALUE_STRING = TRANSACTION_NUMBER_BUY_VALUE.toString();
    public static final TransactionNumber TRANSACTION_NUMBER_SELL = new TransactionNumber();
    public static final UUID TRANSACTION_NUMBER_SELL_VALUE = TRANSACTION_NUMBER_SELL.getValue();
    public static final String TRANSACTION_NUMBER_SELL_VALUE_STRING = TRANSACTION_NUMBER_SELL_VALUE.toString();
    public static final TransactionNumber INVALID_TRANSACTION_NUMBER = new TransactionNumber();
    public static final UUID INVALID_TRANSACTION_NUMBER_VALUE = INVALID_TRANSACTION_NUMBER.getValue();
    public static final String INVALID_TRANSACTION_NUMBER_VALUE_STRING = INVALID_TRANSACTION_NUMBER_VALUE.toString();

    // REPORT
    public static final String DAILY = "DAILY";
    public static final String INVALID_REPORT_TYPE = "INVALID";
    public static final String MISSING_REPORT_TYPE = null;
    public static final LocalDate VALID_DATE_REPORT = LocalDate.of(2018, 8, 21);
    public static final String VALID_DATE_STRING_REPORT = "2018-08-21";
    public static final LocalDateTime END_OF_THE_DAY_DATE = LocalDateTime.of(VALID_DATE_REPORT, LocalTime.MAX);
    public static final String END_OF_THE_DAY_DATE_STRING = "2018-08-21T23:59:59.999Z";
    public static final String MISSING_DATE = "";
    public static final String INVALID_DATE_STRING_REPORT = "INVALID";
    public static final LocalDate TODAY_DATE = LocalDate.now();
    public static final LocalDate FUTURE_DATE = LocalDate.now().plusYears(2);
    public static final float RATE_OF_RETURN = 1.1f;
}
