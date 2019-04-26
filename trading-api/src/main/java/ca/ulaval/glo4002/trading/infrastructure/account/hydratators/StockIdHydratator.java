package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.stock.StockId;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedStockId;

class StockIdHydratator {

    PersistedStockId dehydrate(StockId stockId) {
        PersistedStockId persistedStockId = new PersistedStockId();
        persistedStockId.setMarket(stockId.getMarket());
        persistedStockId.setSymbol(stockId.getSymbol());
        return persistedStockId;
    }

    StockId hydrate(PersistedStockId persistedStockId) {
        String market = persistedStockId.getMarket();
        String symbol = persistedStockId.getSymbol();
        return new StockId(market, symbol);
    }

}
