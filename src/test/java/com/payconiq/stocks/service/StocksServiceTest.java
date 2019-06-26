package com.payconiq.stocks.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.payconiq.stocks.exception.StockNotFoundException;
import com.payconiq.stocks.exception.StockWithSameNameAlreadyExistException;
import com.payconiq.stocks.model.Stock;
import com.payconiq.stocks.repository.StocksRepository;
import com.payconiq.stocks.repository.StocksRepositoryImpl;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class StocksServiceTest {
  private StocksRepository stocksRepository = mock(StocksRepositoryImpl.class);
  @Rule public ExpectedException expectedException = ExpectedException.none();

  private StocksService service = new DefaultStocksService(stocksRepository);

  @Before
  public void setUp() {
    when(stocksRepository.findById(1)).thenReturn(givenStock(1, "Apple", "EUR 10.00"));
    when(stocksRepository.findById(2)).thenReturn(givenStock(1, "Payconiq", "EUR 15.00"));
  }

  @Test
  public void whenGetByIdExists_thenReturnStock() throws StockNotFoundException {
    // given existing id
    long existingId = 1;

    // when
    Stock stock = service.getById(existingId);

    // then
    assertEquals("Apple", stock.getName());
  }

  @Test
  public void whenGetByIdDoesntExists_thenReturnException() throws StockNotFoundException {
    // given existing id
    long nonExistingId = 99;
    // expect
    expectedException.expect(StockNotFoundException.class);
    expectedException.expectMessage("No stock found for id=99");
    // when
    service.getById(nonExistingId);
  }

  @Test
  public void whenGetAllStocks_thenReturnValidList() {
    // given two stocks in before method
    when(stocksRepository.findAll())
        .thenReturn(
            Arrays.asList(
                givenStock(1, "Apple", "EUR 10.00"), givenStock(1, "Payconiq", "EUR 15.00")));
    // when
    List<Stock> stocks = service.getAllStocks();

    // then
    assertEquals(2, stocks.size());
    assertEquals("EUR 15", stocks.get(1).getCurrentPrice().toString());
    assertEquals("Payconiq", stocks.get(1).getName());
  }

  @Test
  public void whenCreateStock_thenRepositoryIsCalled()
      throws StockWithSameNameAlreadyExistException {
    // given
    Stock givenStock = givenStock(3, "Payconiq", "EUR 15.00");
    // when
    service.createStock(givenStock);

    // then
    verify(stocksRepository, times(1)).save(givenStock);
  }

  @Test
  public void whenCreateStockNameExists_thenRepoIsNotCalled()
      throws StockWithSameNameAlreadyExistException {
    // given
    when(stocksRepository.findAll())
        .thenReturn(
            Arrays.asList(
                givenStock(1, "Apple", "EUR 10.00"), givenStock(1, "Payconiq", "EUR 15.00")));
    Stock givenStock = givenStock(3, "Payconiq", "EUR 15.00");
    // expect
    expectedException.expect(StockWithSameNameAlreadyExistException.class);
    expectedException.expectMessage("Stock with same name exists name = Payconiq");
    // when
    service.createStock(givenStock);

    // then
    verify(stocksRepository, times(0)).save(givenStock);
  }

  @Test
  public void whenupdatePriceForStock_thenUpdated()
      throws StockNotFoundException, InterruptedException {
    // given
    long existingId = 1;
    Stock old = stocksRepository.findById(1);
    // when
    // sleep for 2 seconds so time stamps are changed
    Thread.sleep(2000);
    Stock updatedStock = service.updatePriceForStock(existingId, Money.parse("EUR 99.00"));

    // then
    assertEquals("EUR 99", updatedStock.getCurrentPrice().toString());
    verify(stocksRepository, times(1)).save(updatedStock);
  }

  @Test
  public void whenUpdatePriceByIdDoesntExists_thenReturnException() throws StockNotFoundException {
    // given existing id
    long nonExistingId = 99;
    // expect
    expectedException.expect(StockNotFoundException.class);
    expectedException.expectMessage("No stock found for id=99");
    // when
    service.updatePriceForStock(nonExistingId, Money.parse("EUR 99"));
  }

  public static Stock givenStock(long id, String name, String price) {
    Stock stock = new Stock();
    stock.setId(id);
    stock.setName(name);
    stock.setCurrentPrice(Money.parse(price));
    stock.setLastUpdate(LocalDateTime.now());
    return stock;
  }
}
