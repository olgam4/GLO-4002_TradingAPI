package ca.ulaval.glo4002.trading.application.dividend;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.dividend.Dividend;
import ca.ulaval.glo4002.trading.domain.market.Market;
import ca.ulaval.glo4002.trading.domain.market.MarketRepository;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.stock.Stock;
import ca.ulaval.glo4002.trading.domain.stock.StockRepository;

import java.util.List;

public class DividendApplicationService {

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final DividendDomainAssembler dividendDomainAssembler;
    private final MarketRepository marketRepository;

    public DividendApplicationService() {
        this(
                ServiceLocator.resolve(AccountRepository.class),
                ServiceLocator.resolve(StockRepository.class),
                ServiceLocator.resolve(DividendDomainAssembler.class),
                ServiceLocator.resolve(MarketRepository.class)
        );
    }

    DividendApplicationService(AccountRepository accountRepository,
                               StockRepository stockRepository,
                               DividendDomainAssembler dividendDomainAssembler,
                               MarketRepository marketRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.dividendDomainAssembler = dividendDomainAssembler;
        this.marketRepository = marketRepository;
    }

    public void createDividend(DividendDTO dividendDTO) {
        Stock stock = stockRepository.findByStockId(dividendDTO.getStockId());
        Market market = marketRepository.findByMarket(dividendDTO.getStockId().getMarket());
        stock.setCurrency(market.getCurrency());
        dividendDTO.setStock(stock);
        Dividend dividend = dividendDomainAssembler.from(dividendDTO);
        List<Account> accounts = accountRepository.getAll();
        for (Account account : accounts) {
            account.applyDividend(dividend);
            accountRepository.update(account);
        }
    }

}
