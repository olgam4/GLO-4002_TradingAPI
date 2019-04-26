package ca.ulaval.glo4002.trading.contexts;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.application.account.AccountApplicationService;
import ca.ulaval.glo4002.trading.application.account.AccountDomainAssembler;
import ca.ulaval.glo4002.trading.application.dividend.DividendApplicationService;
import ca.ulaval.glo4002.trading.application.dividend.DividendDomainAssembler;
import ca.ulaval.glo4002.trading.application.dividend.DividendPaymentDomainAssembler;
import ca.ulaval.glo4002.trading.application.report.ReportApplicationService;
import ca.ulaval.glo4002.trading.application.report.ReportConverter;
import ca.ulaval.glo4002.trading.application.report.StockMarketReturnDomainAssembler;
import ca.ulaval.glo4002.trading.application.transaction.TransactionApplicationService;
import ca.ulaval.glo4002.trading.application.transaction.TransactionDomainAssembler;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.money.CurrencyExchanger;
import ca.ulaval.glo4002.trading.domain.money.CurrencyValueRepository;
import ca.ulaval.glo4002.trading.domain.report.Reporter;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;
import ca.ulaval.glo4002.trading.infrastructure.account.InMemoryCustomAccountRepository;
import ca.ulaval.glo4002.trading.infrastructure.currencyValue.LocalCurrencyValueRepository;
import ca.ulaval.glo4002.trading.infrastructure.market.RestApiMarketRepository;
import ca.ulaval.glo4002.trading.infrastructure.stock.RestApiStockRepository;

public class DevContext implements ApplicationContext {

    @Override
    public void execute() {
        registerAllClasses();
    }

    private void registerAllClasses() {
        registerRepositories();
        registerDomainAssemblers();
        registerServices();
    }

    private void registerRepositories() {
        ServiceLocator.register(AccountRepository.class, new InMemoryCustomAccountRepository());
        ServiceLocator.register(StockRepository.class, new RestApiStockRepository());
        ServiceLocator.register(MarketRepository.class, new RestApiMarketRepository());
        ServiceLocator.register(CurrencyValueRepository.class, new LocalCurrencyValueRepository());
    }

    private void registerDomainAssemblers() {
        ServiceLocator.register(StockMarketReturnDomainAssembler.class, new StockMarketReturnDomainAssembler());
        ServiceLocator.register(AccountDomainAssembler.class, new AccountDomainAssembler());
        ServiceLocator.register(TransactionDomainAssembler.class, new TransactionDomainAssembler());
        ServiceLocator.register(DividendDomainAssembler.class, new DividendDomainAssembler());
        ServiceLocator.register(DividendPaymentDomainAssembler.class, new DividendPaymentDomainAssembler());
        ServiceLocator.register(ReportConverter.class, new ReportConverter());
    }

    private void registerServices() {
        ServiceLocator.register(CurrencyExchanger.class, new CurrencyExchanger());
        ServiceLocator.register(Reporter.class, new Reporter());
        ServiceLocator.register(AccountApplicationService.class, new AccountApplicationService());
        ServiceLocator.register(TransactionApplicationService.class, new TransactionApplicationService());
        ServiceLocator.register(DividendApplicationService.class, new DividendApplicationService());
        ServiceLocator.register(ReportApplicationService.class, new ReportApplicationService());
    }

}
