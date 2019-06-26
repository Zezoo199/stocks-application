package com.payconiq.stocks.repository;

import static com.payconiq.stocks.service.StocksServiceTest.givenStock;
import static org.junit.Assert.*;

import com.payconiq.stocks.model.Stock;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class StocksRepositoryTest {
  @Before
  public void setUp() {
    repository.save(givenStock(3, "Payconiq", "EUR 15.00"));
    repository.save(givenStock(2, "Google", "EUR 14.00"));
  }

  private StocksRepository repository = new StocksRepositoryImpl();

  @Test
  public void givenRepoWithTwoStocks_whenFindById_thenCheckStockObject() {
    // given repo

    // when
    Stock stock = repository.findById(3);

    // then
    assertEquals("Payconiq", stock.getName());
  }

  @Test
  public void givenRepoWithTwoStocks_whenFindAll_thenCheckStockObject() {
    // given repo

    // when
    List<Stock> stock = repository.findAll();

    // then
    assertTrue(stock.size() > 1);
  }

  @Test
  public void givenRepoWithTwoStocks_whenSave_thenCheckSaved() {
    // given repo
    assertNull(repository.findById(5));
    // when
    repository.save(givenStock(5, "FACEBOOK", "EUR 12.00"));

    // then
    assertNotNull(repository.findById(5));
    assertEquals("FACEBOOK", repository.findById(5).getName());
  }
}
