package ca.ulaval.glo4002.trading.rest.databind;

import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.account.transaction.TransactionType;
import ca.ulaval.glo4002.trading.domain.money.Money;
import ca.ulaval.glo4002.trading.domain.report.Quarter;
import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.rest.databind.deserializers.*;
import ca.ulaval.glo4002.trading.rest.databind.serializers.*;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

import java.time.LocalDateTime;

public class TradingSerializationModule extends Module {


    @Override
    public String getModuleName() {
        return this.getClass().getName();
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        setSerializers(setupContext);
        setDeserializers(setupContext);
    }

    private void setDeserializers(SetupContext setupContext) {
        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(AccountNumber.class, new AccountNumberDeserializer());
        deserializers.addDeserializer(Money.class, new MoneyDeserializer());
        deserializers.addDeserializer(InvestorId.class, new InvestorIdDeserializer());
        deserializers.addDeserializer(TransactionNumber.class, new TransactionNumberDeserializer());
        deserializers.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        deserializers.addDeserializer(TransactionType.class, new TransactionTypeDeserializer());
        deserializers.addDeserializer(StockId.class, new StockIdDeserializer());
        setupContext.addDeserializers(deserializers);
    }

    private void setSerializers(SetupContext setupContext) {
        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(AccountNumber.class, new AccountNumberSerializer());
        serializers.addSerializer(Money.class, new MoneySerializer());
        serializers.addSerializer(InvestorId.class, new InvestorIdSerializer());
        serializers.addSerializer(TransactionNumber.class, new TransactionNumberSerializer());
        serializers.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        serializers.addSerializer(Quarter.class, new QuarterSerializer());
        setupContext.addSerializers(serializers);
    }

}
