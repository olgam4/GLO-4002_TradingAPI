package ca.ulaval.glo4002.stocks.repositories;

import ca.ulaval.glo4002.stocks.domain.stocks.Stock;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface StockRepository extends Repository<Stock, Integer> {
  @RestResource(exported = false)
  List<Stock> findAll();

  @RestResource(exported = false)
  Stock findOneByMarketAndSymbol(@Param("market") String market, @Param("symbol") String symbol);

  @RestResource(exported = false)
  Stock save(Stock stock);
}
