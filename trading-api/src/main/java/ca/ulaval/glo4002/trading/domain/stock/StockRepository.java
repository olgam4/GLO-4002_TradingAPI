package ca.ulaval.glo4002.trading.domain.stock;

public interface StockRepository {

    Stock findByStockId(StockId stockId);

}
