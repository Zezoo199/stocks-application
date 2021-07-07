package com.payconiq.stocks.service;

import com.payconiq.stocks.exception.StockNotFoundException;
import com.payconiq.stocks.exception.StockWithSameNameAlreadyExistException;
import com.payconiq.stocks.model.Stock;
import java.util.List;
import org.javamoney.moneta.Money;

public interface StocksService {
  /**
   * Method to get all stocks
   *
   * @return list of stocks
   */
  List<Stock> getAllStocks();
  /**
   * Method to create stock.
   *
   * @throws StockWithSameNameAlreadyExistException if stock with same name exists
   * @param stock creatable stock.
   */
  void createStock(Stock stock) throws StockWithSameNameAlreadyExistException;
  /**
   * Method to get one stock by id.
   *
   * @param stockId id of the stock.
   * @return the stock.
   * @throws StockNotFoundException if stock not found.
   */
  Stock getById(long stockId) throws StockNotFoundException;
  /**
   * Method to update a stock price.
   *
   * @param stockId id of the stock.
   * @param newPrice new price for it.
   * @return stock updated stock .
   * @throws StockNotFoundException if stock not found.
   */
  Stock updatePriceForStock(long stockId, Money newPrice) throws StockNotFoundException;
}
