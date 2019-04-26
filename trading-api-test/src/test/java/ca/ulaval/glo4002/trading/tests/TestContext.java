package ca.ulaval.glo4002.trading.tests;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.contexts.ApplicationContext;
import ca.ulaval.glo4002.trading.rest.account.AccountViewAssembler;
import ca.ulaval.glo4002.trading.rest.dividend.DividendViewAssembler;
import ca.ulaval.glo4002.trading.rest.report.assemblers.DailyReportViewAssembler;
import ca.ulaval.glo4002.trading.rest.report.assemblers.QuarterlyReportViewAssembler;
import ca.ulaval.glo4002.trading.rest.report.assemblers.StockMarketReturnViewAssembler;
import ca.ulaval.glo4002.trading.rest.transaction.TransactionViewAssembler;

public class TestContext implements ApplicationContext {
    @Override
    public void execute() {
        ServiceLocator.register(StockMarketReturnViewAssembler.class, new StockMarketReturnViewAssembler());
        ServiceLocator.register(AccountViewAssembler.class, new AccountViewAssembler());
        ServiceLocator.register(TransactionViewAssembler.class, new TransactionViewAssembler());
        ServiceLocator.register(DividendViewAssembler.class, new DividendViewAssembler());
        ServiceLocator.register(DailyReportViewAssembler.class, new DailyReportViewAssembler());
        ServiceLocator.register(QuarterlyReportViewAssembler.class, new QuarterlyReportViewAssembler());
    }
}
