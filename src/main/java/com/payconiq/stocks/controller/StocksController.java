package com.payconiq.stocks.controller;

import com.payconiq.stocks.exception.StockNotFoundException;
import com.payconiq.stocks.exception.StockWithSameNameAlreadyExistException;
import com.payconiq.stocks.model.Stock;
import com.payconiq.stocks.service.StocksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("Payconiq-Stocks Service")
@RestController
@RequestMapping("/api")
public class StocksController {
  private final StocksService stocksService;
  @Autowired
  public StocksController(StocksService stocksService) {
    this.stocksService = stocksService;
  }

  /**
   * Endpoint to get all stocks
   *
   * @return list of stocks
   */
  @ApiOperation(value = "Get all stocks")
  @GetMapping("/stocks")
  public List<Stock> getStocks() {
    return stocksService.getAllStocks();
  }

  /**
   * Endpint to get one stock
   *
   * @param stockId id of the stock.
   * @return the stock.
   * @throws StockNotFoundException if stock not found.
   */
  @ApiOperation(value = "Get one stock by id")
  @GetMapping("/stocks/{stockId}")
  public @ResponseBody Stock getOneStock(@PathVariable("stockId") long stockId)
      throws StockNotFoundException {
    return stocksService.getById(stockId);
  }

  /**
   * Endpoint to create stock.
   *
   * @param stock creatable stock.
   * @throws StockWithSameNameAlreadyExistException if stock with same name exists
   */
  @ApiOperation(value = "Create new stock")
  @PostMapping("/stocks")
  @ResponseStatus(HttpStatus.CREATED)
  public void postStock(@RequestBody Stock stock) throws StockWithSameNameAlreadyExistException {
    stocksService.createStock(stock);
  }

  /**
   * Endpoint to update a stock price.
   *
   * @param stockId id of the stock.
   * @param newPrice new price for it.
   * @return stock updated stock .
   * @throws StockNotFoundException if stock not found.
   */
  @ApiOperation(value = "Update price of a stock")
  @PutMapping("/stocks/{stockId}")
  public @ResponseBody Stock updateOneStock(
      @PathVariable("stockId") long stockId, @RequestBody Money newPrice)
      throws StockNotFoundException {
    return stocksService.updatePriceForStock(stockId, newPrice);
  }
}
