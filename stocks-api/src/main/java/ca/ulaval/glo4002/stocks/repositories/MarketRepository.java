package ca.ulaval.glo4002.stocks.repositories;

import ca.ulaval.glo4002.stocks.domain.markets.Market;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


public interface MarketRepository extends Repository<Market, Integer> {
  @RestResource(exported = false)
  Market findOne(Integer id);

  @RestResource(exported = false)
  Market findOneBySymbol(String symbol);

  @RestResource(exported = false)
  List<Market> findAll();
}
