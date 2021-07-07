package com.payconiq.stocks.service;

import com.payconiq.stocks.exception.StockNotFoundException;
import com.payconiq.stocks.exception.StockWithSameNameAlreadyExistException;
import com.payconiq.stocks.model.Stock;
import com.payconiq.stocks.repository.StocksRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultStocksService implements StocksService {
  private static final Logger log = LoggerFactory.getLogger(DefaultStocksService.class);
  private final StocksRepository stocksRepository;

  /**
   * Constructor to inject the repo and creates two stocks in it.
   *
   * @param stocksRepository the autowired repository.
   */
  @Autowired
  public DefaultStocksService(StocksRepository stocksRepository) {
    this.stocksRepository = stocksRepository;
    Stock stock1 = givenStock(1, "Apple", "EUR 10.00");
    Stock stock2 = givenStock(2, "Facebook", "EUR 11.00");

    stocksRepository.save(stock1);
    stocksRepository.save(stock2);
  }

  /**
   * Method to get all stocks, one could also sort it by last update time stamp.
   *
   * @return list of stocks
   */
  @Override
  public List<Stock> getAllStocks() {
    log.info("Returning all stocks");
    return stocksRepository.findAll();
    /**
     * One could also sort it by last update as below or implmenting comparable with equals and
     * hashcode ;)
     */
    // .stream().
    // sorted(Comparator.comparing(Stock::getLastUpdate).reversed()).collect(Collectors.toList());
  }
  /**
   * Method to create stock.
   *
   * @param stock creatable stock
   * @throws StockWithSameNameAlreadyExistException if stock with same name exists
   */
  @Override
  public void createStock(Stock stock) throws StockWithSameNameAlreadyExistException {
    if (stocksRepository.findAll().stream()
        .anyMatch(stk -> stk.getName().equals(stock.getName()))) {
      throw new StockWithSameNameAlreadyExistException(
          "Stock with same name exists name = " + stock.getName());
    }
    log.info("Creating stock with ID {}", stock.getId());
    stocksRepository.save(stock);
  }
  /**
   * Method to get one stock by id.
   *
   * @param stockId id of the stock.
   * @return the stock.
   * @throws StockNotFoundException if stock not found.
   */
  @Override
  public Stock getById(long stockId) throws StockNotFoundException {
    log.info("Finding stock by id {}", stockId);
    Stock stock = stocksRepository.findById(stockId);
    if (stock == null) throw new StockNotFoundException("No stock found for id=" + stockId);
    return stock;
  }
  /**
   * Method to update a stock price. and last update.
   *
   * @param stockId id of the stock.
   * @param newPrice new price for it.
   * @return stock updated stock .
   * @throws StockNotFoundException if stock not found.
   */
  @Override
  public Stock updatePriceForStock(long stockId, Money newPrice) throws StockNotFoundException {
    log.info("Updating price for stock {} , new price {}", stockId, newPrice);
    Stock stock = stocksRepository.findById(stockId);
    if (stock == null) throw new StockNotFoundException("No stock found for id=" + stockId);
    stock.setCurrentPrice(newPrice);
    stock.setLastUpdate(LocalDateTime.now());
    stocksRepository.save(stock); // not really needed but could be good if Jpa is used ;)
    return stock;
  }

  private Stock givenStock(long id, String name, String price) {
    Stock stock = new Stock();
    stock.setId(id);
    stock.setName(name);
    stock.setCurrentPrice(Money.parse(price));
    stock.setLastUpdate(LocalDateTime.now());
    return stock;
  }
}
