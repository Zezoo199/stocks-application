package com.payconiq.stocks.repository;

import com.payconiq.stocks.model.Stock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/** Singleton repository to handle stocks in memory. */
@Repository
public class StocksRepositoryImpl implements StocksRepository {
  private static Map<Long, Stock> stocksMap = new HashMap<>();

  @Override
  public Stock findById(long id) {
    if (stocksMap.containsKey(id)) return stocksMap.get(id);
    return null;
  }

  @Override
  public List<Stock> findAll() {
    return new ArrayList<>(stocksMap.values());
  }

  @Override
  public synchronized void save(Stock stock) {
    stocksMap.put(stock.getId(), stock);
  }
}
