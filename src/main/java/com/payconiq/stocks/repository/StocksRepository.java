package com.payconiq.stocks.repository;

import com.payconiq.stocks.model.Stock;
import java.util.List;

/** Singleton repository to handle stocks in memory. */
public interface StocksRepository {
  Stock findById(long id);

  List<Stock> findAll();

  void save(Stock stock);
}
