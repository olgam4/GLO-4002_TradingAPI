package ca.ulaval.glo4002.trading.domain.market;

import ca.ulaval.glo4002.trading.domain.commons.Period;
import ca.ulaval.glo4002.trading.domain.money.Currency;

import java.time.*;
import java.util.List;

public class Market {

    private String marketSymbol;
    private List<Period> openHours;
    private ZoneOffset zoneOffset;
    private Currency currency;

    public Market(String marketSymbol, ZoneOffset zoneOffset, List<Period> openHours, Currency currency) {
        this.marketSymbol = marketSymbol;
        this.openHours = openHours;
        this.zoneOffset = zoneOffset;
        this.currency = currency;
    }

    public List<Period> getOpenHours() {
        return openHours;
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
    }

    public boolean isMarketOpen(LocalDateTime date) {
        OffsetDateTime transactionDate = date.atOffset(ZoneOffset.UTC);
        OffsetDateTime transactionAtMarketTime = transactionDate.withOffsetSameInstant(getZoneOffset());
        if (isWeekDay(transactionAtMarketTime)) {
            for (Period period : getOpenHours()) {
                LocalTime localTime = transactionAtMarketTime.toLocalTime();
                if (period.contains(LocalDateTime.of(LocalDate.now(), localTime))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMarketClose(LocalDateTime date) {
        return !isMarketOpen(date);
    }

    private boolean isWeekDay(OffsetDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
    }

    public String getMarketSymbol() {
        return marketSymbol;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
